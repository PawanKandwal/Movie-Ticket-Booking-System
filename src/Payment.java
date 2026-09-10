/**
 * Abstraction for all supported payment methods.
 * OOP: Abstraction + Runtime Polymorphism.
 */
// OOP: Abstraction — concrete payment details are hidden behind this contract.
public abstract class Payment {
    public abstract boolean pay(double amount);
    public abstract String getPaymentMethod();
}
