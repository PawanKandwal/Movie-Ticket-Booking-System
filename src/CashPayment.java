// OOP: Inheritance — Cash is a concrete subtype of Payment.
public class CashPayment extends Payment {
    private final double cashReceived;

    public CashPayment(double cashReceived) {
        this.cashReceived = cashReceived;
    }

    // OOP: Runtime Polymorphism — this implementation is selected through a Payment reference.
    @Override
    public boolean pay(double amount) {
        return amount > 0 && cashReceived >= amount;
    }

    public double getChange(double amount) {
        return Math.max(0, cashReceived - amount);
    }

    @Override
    public String getPaymentMethod() {
        return "Cash";
    }
}
