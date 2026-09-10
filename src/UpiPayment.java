// OOP: Inheritance — UPI is a concrete subtype of Payment.
public class UpiPayment extends Payment {
    private final String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    // OOP: Runtime Polymorphism — this implementation is selected through a Payment reference.
    @Override
    public boolean pay(double amount) {
        // Demo validation: e.g. pawan@upi
        return upiId != null && upiId.matches("^[A-Za-z0-9._-]{2,}@[A-Za-z]{2,}$") && amount > 0;
    }

    @Override
    public String getPaymentMethod() {
        return "UPI";
    }
}
