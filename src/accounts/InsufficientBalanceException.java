package accounts;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Баланс отправителя меньше суммы перевода");
    }
}
