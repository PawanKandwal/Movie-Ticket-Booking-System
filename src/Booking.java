import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Booking {
    // OOP: Encapsulation — booking state is private and changed through controlled methods.
    // OOP: Static Members — nextBookingId is shared by all Booking objects.
    private static int nextBookingId = 1001; // Static member: shared unique ID generator.

    private final String bookingId;
    private final Show show;
    private final Customer customer;
    private final List<ShowSeat> bookedSeats;
    private final double totalAmount;
    private BookingStatus status;
    private Payment payment;

    public Booking(Show show, Customer customer, List<ShowSeat> bookedSeats, double totalAmount) {
        // OOP: this keyword — refers to the current Booking instance.
        this.bookingId = "BK" + nextBookingId++;
        this.show = show;
        this.customer = customer;
        this.bookedSeats = new ArrayList<>(bookedSeats);
        this.totalAmount = totalAmount;
        this.status = BookingStatus.PENDING;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Show getShow() {
        return show;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<ShowSeat> getBookedSeats() {
        return Collections.unmodifiableList(bookedSeats);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void confirm() {
        status = BookingStatus.CONFIRMED;
    }

    public void fail() {
        status = BookingStatus.FAILED;
    }

    public void cancel() {
        status = BookingStatus.CANCELLED;
    }
}
