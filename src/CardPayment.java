// OOP: Inheritance — Card is a concrete subtype of Payment.
public class CardPayment extends Payment {
    private final String cardNumber;
    private final String cvv;

    public CardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    // OOP: Runtime Polymorphism — this implementation is selected through a Payment reference.
    @Override
    public boolean pay(double amount) {
        String normalizedCard = cardNumber == null ? "" : cardNumber.replaceAll("\\s+", "");
        return normalizedCard.matches("\\d{16}") && cvv != null && cvv.matches("\\d{3}") && amount > 0;
    }

    @Override
    public String getPaymentMethod() {
        return "Card";
    }
}
