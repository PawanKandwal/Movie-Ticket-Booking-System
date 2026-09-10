public class Movie {
    private final int movieId;
    private final String title;
    private final String language;
    private final int durationMinutes;

    // Compile-time polymorphism: overloaded constructors.
    public Movie(int movieId, String title, String language, int durationMinutes) {
        this.movieId = movieId;
        this.title = title;
        this.language = language;
        this.durationMinutes = durationMinutes;
    }

    public Movie(String title, String language, int durationMinutes) {
        this(0, title, language, durationMinutes);
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public String toString() {
        return title + " | " + language + " | " + durationMinutes + " min";
    }
}
