import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cinema {
    private final String name;
    private final List<Screen> screens;
    private final List<Movie> movies;
    private final List<Show> shows;

    public Cinema(String name) {
        this.name = name;
        this.screens = new ArrayList<>(); // Composition: Cinema owns its screens.
        this.movies = new ArrayList<>();
        this.shows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public List<Movie> getMovies() {
        return Collections.unmodifiableList(movies);
    }

    public List<Screen> getScreens() {
        return Collections.unmodifiableList(screens);
    }

    public List<Show> getShowsForMovie(Movie movie) {
        List<Show> result = new ArrayList<>();
        for (Show show : shows) {
            if (show.getMovie() == movie) {
                result.add(show);
            }
        }
        return result;
    }
}
