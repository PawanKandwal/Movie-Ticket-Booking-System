import java.util.List;

public class PriceCalculator {
    public double calculateTotal(List<ShowSeat> seats) {
        double total = 0;
        for (ShowSeat showSeat : seats) {
            total += showSeat.getSeat().getSeatType().getPrice();
        }
        return total;
    }
}
