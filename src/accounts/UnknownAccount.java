package accounts;

public class UnknownAccount extends Exception {
    public UnknownAccount(String account) {
        super("Счет \"" + account + "\" не найден");
    }
}
