package moviebuddy.data;

import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.Collections;
import java.util.List;

class CachingMovieReaderTest {

    @Test
    void caching() {
        CacheManager cacheMange = new ConcurrentMapCacheManager();
        MovieReader target = Collections::emptyList;
        CachingMovieReader movieReader = new CachingMovieReader(cacheMange, target);

        Assertions.assertNull(movieReader.getCachedData());

        List<Movie> movies = movieReader.loadMovies();
        Assertions.assertNotNull(movieReader.getCachedData());
        Assertions.assertSame(movieReader.loadMovies(), movies);
    }

}