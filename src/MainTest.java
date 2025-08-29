import accounts.Accounts;
import authentication.Authentication;
import authentication.LoginFailedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import products.InvalidRatingException;
import products.Products;
import products.ItemNotFoundException;

import accounts.InsufficientBalanceException;
import accounts.InvalidTransferAmountException;
import accounts.UnknownAccount;


class MainTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void safeDivideTest() {
        Assertions.assertEquals(4, Main.approximateSafeDivide(12, 3));

        Assertions.assertThrows(ArithmeticException.class, () -> Main.approximateSafeDivide(12, 0));
    }

    @Test
    void checkStringArgumentTest() {
        outputStreamCaptor.reset();
        Main.checkStringArgument("Hello, World!");
        Assertions.assertEquals("Строка \"Hello, World!\" - допустимый аргумент", outputStreamCaptor.toString().trim());

        Assertions.assertThrows(IllegalArgumentException.class, () -> Main.checkStringArgument(""));

        Assertions.assertThrows(IllegalArgumentException.class, () -> Main.checkStringArgument("    "));

        Assertions.assertThrows(IllegalArgumentException.class, () -> Main.checkStringArgument(null));
    }

    @Test
    void setAgeTest() {
        outputStreamCaptor.reset();
        Main.setAge(25);
        Assertions.assertEquals("Установлен возраст: 25", outputStreamCaptor.toString().trim());

        Assertions.assertThrows(IllegalArgumentException.class, () -> Main.setAge(-10));
    }

    @Test
    void depositTest() {
        outputStreamCaptor.reset();
        try {
            Main.deposit(2000);
        } catch (NegativeDepositException ex) {
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals("Депозит на сумму 2000.0 обработан", outputStreamCaptor.toString().trim());

        Assertions.assertThrows(NegativeDepositException.class, () -> Main.deposit(-3000));
    }

    @Test
    void getItemTest() {
        Products products = new Products(new HashMap<>(Map.of( "Pineapple", "Ананас", "Orange", "Апельсин", "Banana","Банан", "Mandarin", "Мандарин", "Mango", "Манго")));

        Assertions.assertEquals("Мандарин", products.getItem("Mandarin").getName());

        Assertions.assertThrows(ItemNotFoundException.class, () -> products.getItem("Papaya"));

        Assertions.assertThrows(ItemNotFoundException.class, () -> products.getItem(null));

        Assertions.assertThrows(ItemNotFoundException.class, () -> products.getItem("   "));

        Assertions.assertThrows(ItemNotFoundException.class, () -> products.getItem("\t\n\r\f "));
    }

    @Test
    void readFile() {
        try {
            Assertions.assertEquals(new ArrayList<>(List.of("Раз", "Два", "Три", "Четыре", "Пять", "Вышел", "Зайчик", "Погулять")), Main.readFile("src/sampleText.txt"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        Assertions.assertThrows(IOException.class, () -> Main.readFile("src/sampleText2.txt"));
    }

    @Test
    void loginTest() {
        Authentication authentication = new Authentication(new HashMap<>(Map.of( "Bob", "12345", "John", "54321", "Sam","qwerty", "Bill", "ytrewq")));

        outputStreamCaptor.reset();
        try {
            authentication.login("Sam", "qwerty");
        } catch (LoginFailedException ex) {
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals("Успешная аутентификация!", outputStreamCaptor.toString().trim());

        Assertions.assertThrows(LoginFailedException.class, () -> authentication.login("Sam", "ytrewq"));

        Assertions.assertThrows(LoginFailedException.class, () -> authentication.login(null, "ytrewq"));

        Assertions.assertThrows(LoginFailedException.class, () -> authentication.login("Sam", null));
    }

    @Test
    void transferTest() {
        Accounts accounts = new Accounts(new HashMap<>(Map.of( "001", 100.0, "002", 200.0, "003",300.0, "004", 400.0)));

        outputStreamCaptor.reset();
        Assertions.assertThrows(InvalidTransferAmountException.class, () -> accounts.transfer("001", "002", -50.0));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> accounts.transfer("001", "002", 150.0));

        Assertions.assertThrows(UnknownAccount.class, () -> accounts.transfer("321", "002", 150.0));

        Assertions.assertThrows(UnknownAccount.class, () -> accounts.transfer("001", "222", 150.0));

        outputStreamCaptor.reset();
        try {
            accounts.transfer("001", "002", 50.0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals("Со счета \"001\" на счет \"002\" переведено: 50.0", outputStreamCaptor.toString().trim());
    }

    @Test
    void rateProductTest() {
        Products products = new Products(new HashMap<>(Map.of( "Pineapple", "Ананас", "Orange", "Апельсин", "Banana","Банан", "Mandarin", "Мандарин", "Mango", "Манго")));

        Assertions.assertThrows(InvalidRatingException.class, () -> products.getItem("Pineapple").rateProduct("-1"));

        Assertions.assertThrows(InvalidRatingException.class, () -> products.getItem("Pineapple").rateProduct("8"));

        outputStreamCaptor.reset();
        try {
            products.getItem("Pineapple").rateProduct("5");
        } catch (InvalidRatingException ex) {
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals("Товару \"Ананас\" установлен рейтинг \"5\"", outputStreamCaptor.toString().trim());

        outputStreamCaptor.reset();
        try {
            products.getItem("Pineapple").rateProduct("cba");
        } catch (InvalidRatingException ex) {
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals("Рейтинг содержит нечисловое значение \"cba\"", outputStreamCaptor.toString().trim());
    }
}