package moviebuddy;

import moviebuddy.data.CsvMovieReader;
import moviebuddy.domain.MovieReader;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class JdkDynamicProxyTests {

    @Test
    void useDynamicProxy() throws Exception {
        CsvMovieReader movieReader = new CsvMovieReader();
        movieReader.setResourceLoader(new DefaultResourceLoader());
        movieReader.setMetadata("movie_metadata.csv");
        movieReader.afterPropertiesSet();

        ClassLoader classLoader = JdkDynamicProxyTests.class.getClassLoader();
        Class<?>[] interfaces = new Class[] { MovieReader.class };
        InvocationHandler handler = new PerformanceInvocationHandler(movieReader);

        MovieReader proxy = (MovieReader) Proxy.newProxyInstance(classLoader, interfaces, handler);

        proxy.loadMovies();
        proxy.loadMovies();
    }

    static class PerformanceInvocationHandler implements InvocationHandler {

        final Logger log = LoggerFactory.getLogger(getClass());
        final Object target;

        public PerformanceInvocationHandler(Object target) {
            this.target = Objects.requireNonNull(target);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long start = System.currentTimeMillis();
            Object result = method.invoke(target, args);
            long elapsed = System.currentTimeMillis() - start;
            log.info("Excution {} method finished in {}ms", method.getName(), elapsed);
            return result;
        }
    }
}
