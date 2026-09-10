public class TicketPrinter {
    public void printTicket(Booking booking) {
        System.out.println();
        System.out.println("================= TICKET =================");
        System.out.println("Booking ID : " + booking.getBookingId());
        System.out.println("Customer   : " + booking.getCustomer().getName());
        System.out.println("Movie      : " + booking.getShow().getMovie().getTitle());
        System.out.println("Screen     : Screen-" + booking.getShow().getScreen().getScreenNumber());
        System.out.println("Time       : " + booking.getShow().getStartTime());
        System.out.print("Seats      : ");

        for (int i = 0; i < booking.getBookedSeats().size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            ShowSeat showSeat = booking.getBookedSeats().get(i);
            System.out.print(showSeat.getSeat().getSeatNumber());
        }

        System.out.printf("%nAmount     : Rs. %.2f%n", booking.getTotalAmount());
        System.out.println("Payment    : " + booking.getPayment().getPaymentMethod());
        System.out.println("Status     : " + booking.getStatus());
        System.out.println("===========================================");
        System.out.println();
    }
}
