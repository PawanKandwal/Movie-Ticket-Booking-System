import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Show {
    private final int showId;
    private final Movie movie;       // Aggregation: existing Movie can live without a Show.
    private final Screen screen;     // Aggregation: existing Screen can live without a Show.
    private final String startTime;
    private final List<ShowSeat> showSeats;

    public Show(int showId, Movie movie, Screen screen, String startTime) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.showSeats = new ArrayList<>(); // Composition: Show owns ShowSeat state for this show.
        createShowSeats();
    }

    private void createShowSeats() {
        for (Seat seat : screen.getSeats()) {
            showSeats.add(new ShowSeat(seat));
        }
    }

    public int getShowId() {
        return showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public String getStartTime() {
        return startTime;
    }

    public List<ShowSeat> getShowSeats() {
        return Collections.unmodifiableList(showSeats);
    }

    public ShowSeat findShowSeat(String seatNumber) {
        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getSeat().getSeatNumber().equalsIgnoreCase(seatNumber)) {
                return showSeat;
            }
        }
        return null;
    }

    public void displaySeatLayout() {
        System.out.println();
        System.out.println("SCREEN-" + screen.getScreenNumber() + " | " + startTime + " | " + movie.getTitle());
        System.out.println("[ ] = AVAILABLE   [X] = BOOKED");
        System.out.println();

        int count = 0;
        for (ShowSeat showSeat : showSeats) {
            String marker = showSeat.isAvailable() ? "[ ]" : "[X]";
            System.out.printf("%-3s %-9s %-5s  ", marker,
                    showSeat.getSeat().getSeatNumber(),
                    showSeat.getSeat().getSeatType().name());
            count++;
            if (count % 3 == 0) {
                System.out.println();
            }
        }
        if (count % 3 != 0) {
            System.out.println();
        }
    }
}
