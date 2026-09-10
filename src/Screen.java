import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Screen {
    private final int screenNumber;
    private final List<Seat> seats;

    public Screen(int screenNumber) {
        this.screenNumber = screenNumber;
        this.seats = new ArrayList<>(); // Composition: Screen owns its Seat objects.
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }

    public Seat findSeat(String seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equalsIgnoreCase(seatNumber)) {
                return seat;
            }
        }
        return null;
    }

}
