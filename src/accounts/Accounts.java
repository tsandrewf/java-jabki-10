package accounts;

import java.util.Map;

public class Accounts {
    private Map<String, Double> accountMap;

    public Accounts(Map<String, Double> accountMap) {
        this.accountMap = accountMap;
    }

    public void transfer(String fromAccount, String toAccount, Double amount) throws InvalidTransferAmountException, InsufficientBalanceException, UnknownAccount {

        if (!this.accountMap.containsKey(fromAccount)) {
            throw new UnknownAccount(fromAccount);
        }

        if (!this.accountMap.containsKey(toAccount)) {
            throw new UnknownAccount(toAccount);
        }
        if (amount <= 0) {
            throw new InvalidTransferAmountException();
        }

        if (amount > this.accountMap.get(fromAccount)) {
            throw new InsufficientBalanceException();
        }

        this.accountMap.put(fromAccount, this.accountMap.get(fromAccount) - amount);
        this.accountMap.put(toAccount, this.accountMap.get(toAccount) + amount);

        System.out.printf("Со счета \"%s\" на счет \"%s\" переведено: %s\n", fromAccount, toAccount, amount);
    }
}
