import java.util.ArrayList;

public class Movie {
    private String title;
    private ArrayList<String> cast;
    private String director;
    private String overview;
    private int runtime;
    private double userRating;

    public Movie(String title, ArrayList<String> cast, String director, String overview, int runtime, double userRating) {
        this.title = title;
        this.cast = cast;
        this.director = director;
        this.overview = overview;
        this.runtime = runtime;
        this.userRating = userRating;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public String getOverview() {
        return overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public double getUserRating() {
        return userRating;
    }
}
