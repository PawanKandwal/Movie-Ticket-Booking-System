public class ShowSeat {
    private final Seat seat;
    private SeatStatus status;

    public ShowSeat(Seat seat) {
        this.seat = seat;
        this.status = SeatStatus.AVAILABLE;
    }

    public Seat getSeat() {
        return seat;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }

    // Encapsulation: status is changed only through controlled methods.
    public boolean bookSeat() {
        if (!isAvailable()) {
            return false;
        }
        status = SeatStatus.BOOKED;
        return true;
    }

    public void cancelSeat() {
        status = SeatStatus.AVAILABLE;
    }
}
