import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private final PriceCalculator priceCalculator;
    private final TicketPrinter ticketPrinter;
    private final List<Booking> bookings;

    public BookingService(PriceCalculator priceCalculator, TicketPrinter ticketPrinter) {
        this.priceCalculator = priceCalculator;
        this.ticketPrinter = ticketPrinter;
        this.bookings = new ArrayList<>();
    }

    public Booking bookTickets(Customer customer, Show show, List<String> seatNumbers, Payment payment) {
        if (seatNumbers == null || seatNumbers.isEmpty()) {
            System.out.println("No seats selected. Booking rejected.");
            return null;
        }

        List<ShowSeat> selectedSeats = findAndValidateSeats(show, seatNumbers);
        if (selectedSeats == null) {
            return null;
        }

        double total = priceCalculator.calculateTotal(selectedSeats);
        Booking booking = new Booking(show, customer, selectedSeats, total);
        booking.setPayment(payment);
        bookings.add(booking);

        // Reserve only after the full selection has been validated.
        reserveSeats(selectedSeats);

        boolean paymentSuccessful = payment != null && payment.pay(total);
        if (!paymentSuccessful) {
            releaseSeats(selectedSeats);
            booking.fail();
            System.out.println("Payment failed. Booking NOT confirmed. Seats released.");
            return booking;
        }

        booking.confirm();
        System.out.println("Payment successful. Booking confirmed.");
        ticketPrinter.printTicket(booking);
        return booking;
    }

    private List<ShowSeat> findAndValidateSeats(Show show, List<String> seatNumbers) {
        List<ShowSeat> selectedSeats = new ArrayList<>();

        for (String seatNumber : seatNumbers) {
            ShowSeat showSeat = show.findShowSeat(seatNumber.trim());
            if (showSeat == null) {
                System.out.println("Invalid seat number: " + seatNumber);
                return null;
            }
            if (!showSeat.isAvailable()) {
                System.out.println("Seat " + seatNumber + " is already BOOKED. Whole booking rejected.");
                return null;
            }
            if (containsSeat(selectedSeats, showSeat)) {
                System.out.println("Seat " + seatNumber + " was selected more than once.");
                return null;
            }
            selectedSeats.add(showSeat);
        }
        return selectedSeats;
    }

    private boolean containsSeat(List<ShowSeat> seats, ShowSeat target) {
        for (ShowSeat seat : seats) {
            if (seat == target) {
                return true;
            }
        }
        return false;
    }

    private void reserveSeats(List<ShowSeat> seats) {
        for (ShowSeat seat : seats) {
            seat.bookSeat();
        }
    }

    private void releaseSeats(List<ShowSeat> seats) {
        for (ShowSeat seat : seats) {
            seat.cancelSeat();
        }
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = findBooking(bookingId);
        if (booking == null) {
            System.out.println("Booking not found: " + bookingId);
            return false;
        }
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            System.out.println("Only CONFIRMED bookings can be cancelled.");
            return false;
        }

        releaseSeats(booking.getBookedSeats());
        booking.cancel();
        System.out.println("Booking " + bookingId + " cancelled. Seats are AVAILABLE again.");
        return true;
    }

    public Booking findBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId.trim())) {
                return booking;
            }
        }
        return null;
    }


}
