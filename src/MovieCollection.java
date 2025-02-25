import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private ArrayList<String> cast;
    private Scanner scan;

    public MovieCollection() {
        movies = new ArrayList<Movie>();
        cast = new ArrayList<>();
        scan = new Scanner(System.in);
        start();
    }

    public void readData() {
        try {
            File myFile = new File("src\\movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                String title = splitData[0];
                ArrayList<String> currentCast = new ArrayList<>();
                splitData[1] += "|";
                while (splitData[1].contains("|")) {
                    cast.add(splitData[1].substring(0, splitData[1].indexOf("|")));
                    currentCast.add(splitData[1].substring(0, splitData[1].indexOf("|")));
                    splitData[1] = splitData[1].substring(splitData[1].indexOf("|") + 1);
                }
                String director = splitData[2];
                String overview = splitData[3];
                int runtime = Integer.parseInt(splitData[4]);
                double userRating = Double.parseDouble(splitData[5]);
                Movie movie = new Movie(title, currentCast, director, overview, runtime, userRating);
                movies.add(movie);
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }

        for (int i = 1; i < movies.size(); i++) {
            int elementIndex = i;
            Movie moviePulled = movies.get(i);
            while (elementIndex > 0 && moviePulled.getTitle().compareTo(movies.get(elementIndex-1).getTitle()) < 0) {
                movies.set(elementIndex,movies.get(elementIndex-1));
                elementIndex--;
            }
            movies.set(elementIndex,moviePulled);
        }


    }

    public void mainMenu() {
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }

    }

    private void searchTitles() {
        int movieNum = -1;
        ArrayList<Movie> movieSearch = new ArrayList<>();
        System.out.print("Enter a title search term: ");
        String search = scan.nextLine();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().toLowerCase().contains(search.toLowerCase())) {
                movieNum++;
                movieSearch.add(movies.get(i));
                System.out.println((movieNum + 1) + ". " + movies.get(i).getTitle());
            }
        }
        if (movieNum < 0) {
            System.out.println("No movie titles match that search term!");
        } else {
            System.out.println("Which movie would you like to know more about?");
            System.out.print("Enter number: ");
            int infoNum = scan.nextInt();
            scan.nextLine();
            printMovieInfo(movieSearch.get(infoNum - 1));
        }
        mainMenu();
    }

    private void searchCast() {
        System.out.print("Enter cast name: ");
        String castName = scan.nextLine();
        int castNum = 0;
        ArrayList<String> searchedCast = new ArrayList<>();
        boolean alreadySearched = false;

        for (int i = 0; i < cast.size(); i++) {
            if (cast.get(i).toLowerCase().contains(castName.toLowerCase())) {
                for (int j = 0; j < searchedCast.size(); j++) {
                    if (cast.get(i).equals(searchedCast.get(j))) {
                        alreadySearched = true;
                    }
                }
                if (!alreadySearched) {
                    castNum++;
                    searchedCast.add(cast.get(i));
                }
            }
            alreadySearched = false;
        }

        orderCast(searchedCast);

        for (int i = 0; i < searchedCast.size(); i++) {
            System.out.println(i+1 + ". " + searchedCast.get(i));
        }

        if (castNum == 0) {
            System.out.println("No cast members by this name exists!");
        } else {
            System.out.println("Which would you like to see all movies for?");
            System.out.print("Enter number: ");
            int num = scan.nextInt();
            scan.nextLine();

            searchCastMovie(searchedCast.get(num - 1));


        }
        mainMenu();
    }

    private void searchCastMovie(String castName) {
        ArrayList<Movie> searchedMovies = new ArrayList<>();
        int movieNum = 0;

        for (int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < movies.get(i).getCast().size(); j++) {
                String currentMember = movies.get(i).getCast().get(j);
                if (currentMember.equals(castName)) {
                    movieNum++;
                    System.out.println(movieNum + ". " + movies.get(i).getTitle());
                    searchedMovies.add(movies.get(i));
                }
            }
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int movieNumber = scan.nextInt();
        scan.nextLine();
        printMovieInfo(searchedMovies.get(movieNumber - 1));


    }

    private void printMovieInfo(Movie movie) {
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Runtime: " + movie.getRuntime());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.print("Cast: ");
        for (int i = 0; i < movie.getCast().size(); i++) {
            System.out.print(movie.getCast().get(i));
            if (i < movie.getCast().size() - 1) {
                System.out.print("|");
            }
        }
        System.out.println("\nOverview: " + movie.getOverview());
        System.out.println("User Rating: " + movie.getUserRating());
    }

    private void orderCast(ArrayList<String> castList) {
        for (int i = 1; i < castList.size(); i++) {
            int elementIndex = i;
            String castPulled = castList.get(i);
            while (elementIndex > 0 && castPulled.compareTo(castList.get(elementIndex-1)) < 0) {
                castList.set(elementIndex,castList.get(elementIndex-1));
                elementIndex--;
            }
            castList.set(elementIndex,castPulled);
        }
    }

    private void start() {
        readData();
        mainMenu();
    }
}
