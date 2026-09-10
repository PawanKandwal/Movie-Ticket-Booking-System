import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Association: Main wires together domain and service objects for the console application.
public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final Cinema cinema;
    private final PriceCalculator priceCalculator;
    private final BookingService bookingService;
    private final Customer customer;

    public Main() {
        this.cinema = createSampleCinema();
        this.priceCalculator = new PriceCalculator();
        this.bookingService = new BookingService(priceCalculator, new TicketPrinter());
        this.customer = readCustomer();
    }

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> listMovies();
                case 2 -> bookTicketFlow();
                case 3 -> cancelBookingFlow();
                case 4 -> showSeatLayoutFlow();
                case 0 -> running = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the Movie Ticket Booking System.");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== MOVIE TICKET BOOKING =====");
        System.out.println("1. Movies");
        System.out.println("2. Book");
        System.out.println("3. Cancel");
        System.out.println("4. Seat layout");
        System.out.println("0. Exit");
    }

    private void listMovies() {
        System.out.println();
        System.out.println("CURRENTLY PLAYING");
        for (Movie movie : cinema.getMovies()) {
            System.out.println("[" + movie.getMovieId() + "] " + movie);
        }
    }

    private void bookTicketFlow() {
        listMovies();
        int movieId = readInt("Movie ID: ");
        Movie movie = findMovieById(movieId);
        if (movie == null) {
            System.out.println("Invalid movie ID.");
            return;
        }

        List<Show> shows = cinema.getShowsForMovie(movie);
        System.out.println("Shows for " + movie.getTitle() + ":");
        for (Show show : shows) {
            System.out.println("[" + show.getShowId() + "] Screen-" +
                    show.getScreen().getScreenNumber() + " | " + show.getStartTime());
        }

        int showId = readInt("Show ID: ");
        Show show = findShowById(shows, showId);
        if (show == null) {
            System.out.println("Invalid show ID.");
            return;
        }

        show.displaySeatLayout();
        String input = readLine("Seats (e.g. A1,A2): ");
        List<String> seatNumbers = parseSeatNumbers(input);
        if (seatNumbers.isEmpty()) {
            System.out.println("No valid seat numbers entered.");
            return;
        }

        double estimatedTotal = estimateTotal(show, seatNumbers);
        if (estimatedTotal < 0) {
            return;
        }
        System.out.printf("Total: Rs. %.2f%n", estimatedTotal);

        Payment payment = createPayment(estimatedTotal);
        if (payment == null) {
            System.out.println("Invalid payment selection. Booking cancelled before reservation.");
            return;
        }

        bookingService.bookTickets(customer, show, seatNumbers, payment);
    }

    private Payment createPayment(double amount) {
        System.out.println("Pay by: 1.UPI  2.Card  3.Cash");
        int choice = readInt("Payment method: ");
        switch (choice) {
            case 1 -> {
                String upiId = readLine("UPI ID (e.g. pawan@upi): ");
                return new UpiPayment(upiId);
            }
            case 2 -> {
                String cardNumber = readLine("Card number (16 digits): ");
                String cvv = readLine("CVV (3 digits): ");
                return new CardPayment(cardNumber, cvv);
            }
            case 3 -> {
                double cash = readDouble("Cash received: ");
                if (cash < 0) {
                    System.out.println("Cash cannot be negative.");
                    return null;
                }
                return new CashPayment(cash);
            }
            default -> {
                return null;
            }
        }
    }

    private void cancelBookingFlow() {
        String bookingId = readLine("Booking ID: ");
        bookingService.cancelBooking(bookingId);
    }

    private void showSeatLayoutFlow() {
        listMovies();
        int movieId = readInt("Movie ID: ");
        Movie movie = findMovieById(movieId);
        if (movie == null) {
            System.out.println("Invalid movie ID.");
            return;
        }
        List<Show> shows = cinema.getShowsForMovie(movie);
        for (Show show : shows) {
            System.out.println("[" + show.getShowId() + "] Screen-" +
                    show.getScreen().getScreenNumber() + " | " + show.getStartTime());
        }
        int showId = readInt("Show ID: ");
        Show show = findShowById(shows, showId);
        if (show == null) {
            System.out.println("Invalid show ID.");
            return;
        }
        show.displaySeatLayout();
    }

    private double estimateTotal(Show show, List<String> seatNumbers) {
        List<ShowSeat> selectedSeats = new ArrayList<>();
        for (String seatNumber : seatNumbers) {
            ShowSeat showSeat = show.findShowSeat(seatNumber);
            if (showSeat == null) {
                System.out.println("Invalid seat number: " + seatNumber);
                return -1;
            }
            if (!showSeat.isAvailable()) {
                System.out.println("Seat " + seatNumber + " is already BOOKED.");
                return -1;
            }
            if (selectedSeats.contains(showSeat)) {
                System.out.println("Seat " + seatNumber + " was selected more than once.");
                return -1;
            }
            selectedSeats.add(showSeat);
        }
        return priceCalculator.calculateTotal(selectedSeats);
    }

    private List<String> parseSeatNumbers(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = input.split(",");
        List<String> seats = new ArrayList<>();
        for (String part : parts) {
            String seat = part.trim();
            if (!seat.isEmpty()) {
                seats.add(seat);
            }
        }
        return seats;
    }

    private Movie findMovieById(int movieId) {
        for (Movie movie : cinema.getMovies()) {
            if (movie.getMovieId() == movieId) {
                return movie;
            }
        }
        return null;
    }

    private Show findShowById(List<Show> shows, int showId) {
        for (Show show : shows) {
            if (show.getShowId() == showId) {
                return show;
            }
        }
        return null;
    }

    private Customer readCustomer() {
        System.out.println("Welcome to " + cinema.getName());
        String name = readLine("Customer name: ");
        String phone = readLine("Phone: ");
        return new Customer(name, phone);
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt));
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private Cinema createSampleCinema() {
        Cinema sampleCinema = new Cinema("PVR Cinema - Dehradun");

        Screen screen1 = new Screen(1);
        Screen screen2 = new Screen(2);
        addStandardSeats(screen1);
        addStandardSeats(screen2);
        sampleCinema.addScreen(screen1);
        sampleCinema.addScreen(screen2);

        Movie movie1 = new Movie(1, "3 Idiots", "Hindi", 170);
        Movie movie2 = new Movie(2, "Interstellar", "English", 169);
        sampleCinema.addMovie(movie1);
        sampleCinema.addMovie(movie2);

        sampleCinema.addShow(new Show(1, movie1, screen1, "06:00 PM"));
        sampleCinema.addShow(new Show(2, movie1, screen2, "09:00 PM"));
        sampleCinema.addShow(new Show(3, movie2, screen1, "03:00 PM"));
        sampleCinema.addShow(new Show(4, movie2, screen2, "07:00 PM"));

        return sampleCinema;
    }

    private void addStandardSeats(Screen screen) {
        String[] silver = {"A1", "A2", "A3", "A4", "A5"};
        String[] gold = {"B1", "B2", "B3", "B4", "B5"};
        String[] platinum = {"C1", "C2", "C3", "C4", "C5"};

        Arrays.stream(silver).forEach(number -> screen.addSeat(new Seat(number, SeatType.SILVER)));
        Arrays.stream(gold).forEach(number -> screen.addSeat(new Seat(number, SeatType.GOLD)));
        Arrays.stream(platinum).forEach(number -> screen.addSeat(new Seat(number, SeatType.PLATINUM)));
    }
}
