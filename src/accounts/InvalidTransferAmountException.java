package accounts;

public class InvalidTransferAmountException extends Exception{
    public InvalidTransferAmountException() {
        super("Сумма перевода не больше нуля");
    }
}
