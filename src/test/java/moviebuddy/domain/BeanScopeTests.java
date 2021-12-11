package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import moviebuddy.MovieBuddyProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles(MovieBuddyProfile.CSV_MODE)
@SpringJUnitConfig(MovieBuddyFactory.class)
public class BeanScopeTests {

    private final ApplicationContext applicationContext;

    public BeanScopeTests(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Test
    void Equals_MovieFinderBean() {
        MovieFinder movieFinder = applicationContext.getBean(MovieFinder.class);
        Assertions.assertEquals(movieFinder, applicationContext.getBean(MovieFinder.class));
    }

}
