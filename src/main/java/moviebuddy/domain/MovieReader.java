package moviebuddy.domain;

import java.util.List;

@FunctionalInterface
public interface MovieReader {

    List<Movie> loadMovies();

}
