import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import products.Products;
import products.ItemNotFoundException;
import products.InvalidRatingException;

import authentication.Authentication;
import authentication.LoginFailedException;

import accounts.Accounts;

public class Main {
    public static void main(String[] args) {
        /*
         * 1. Безопасное деление
         * Напишите метод safeDivide(int a, int b), который возвращает a / b.
         * Если b == 0, перехватите исключение и выведите сообщение: "Деление на ноль запрещено".
         */
        int a = 10;
        int b = 2;

        try {
            System.out.printf("Результат деления %s на %s: %s\n", a, b, safeDivide(a, b));
        } catch(ArithmeticException ex) {
            System.out.println(ex.getMessage());
        }

        b = 0;
        try {
            System.out.printf("Результат деления %s на %s: %s\n", a, b, safeDivide(a, b));
        } catch(ArithmeticException ex) {
            System.out.println(ex.getMessage());
        }
        /*
         * 2. Проверка строки
         * Напишите метод, который принимает строку и выбрасывает IllegalArgumentException,
         * если строка пуста или состоит только из пробелов.
         */
        System.out.println();

        try {
            checkStringArgument("Hello, World!");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            checkStringArgument("");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            checkStringArgument("     ");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            checkStringArgument(null);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        /*
         * 3. Преобразование строки в число
         * Дан список строк List.of("10", "abc", "5"). Преобразуйте его в список чисел,
         * перехватывая NumberFormatException. Ошибки не должны останавливать выполнение.
         */
        System.out.println();
        List<String> stringList = new ArrayList<>(List.of("10", "abc", "5"));
        ArrayList<Integer> integerList = new ArrayList<>();
        for (String s : stringList) {
            try {
                integerList.add(Integer.parseInt(s));
            } catch(NumberFormatException ex) {
                System.out.println("Ошибка преобразования строки в число. " + ex.getMessage());
            }
        }

        /*
         * 4. Простая валидация возраста
         * Метод setAge(int age) должен выбрасывать IllegalArgumentException,
         * если возраст меньше нуля. Обработайте исключение и выведите сообщение.
         */
        System.out.println();
        try {
            setAge(20);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            setAge(-5);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        /*
         * 5. Собственное исключение: депозит
         * Создайте исключение NegativeDepositException, и метод deposit(double amount),
         * который выбрасывает это исключение при отрицательном значении.
         * Обработайте его в main.
         */
        System.out.println();
        try {
            deposit(100);
        } catch (NegativeDepositException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            deposit(-200);
        } catch (NegativeDepositException ex) {
            System.out.println(ex.getMessage());
        }

        /*
         * 6. Поиск товара по коду
         * Реализуйте метод getItem(String code). Если код не найден в карте товаров,
         * выбросите ItemNotFoundException, унаследованное от RuntimeException.
         * Продемонстрируйте поведение в main.
         */
        System.out.println();
        Products products = new Products(new HashMap<>(Map.of( "Pineapple", "Ананас", "Orange", "Апельсин", "Banana","Банан", "Mandarin", "Мандарин", "Mango", "Манго")));

        try {
            String code = "Orange";
            System.out.printf("По коду \"%s\" найден товар \"%s\"\n", code, products.getItem("Orange").getName());
        } catch (ItemNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(products.getItem(null));
        } catch (ItemNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(products.getItem("   "));
        } catch (ItemNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(products.getItem("Guava"));
        } catch (ItemNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        /*
         * 7. Чтение из файла
         * Реализуйте метод readFile(String path), который читает текстовый файл
         * и возвращает список строк. Используйте BufferedReader,
         * перехватите IOException, выведите сообщение об ошибке.
         */
        System.out.println();

        String path1 = "src/sampleText.txt";

        try {
            System.out.printf("Содержимое файла \"%s\":\n", path1);
            System.out.println(readFile(path1));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        String path2 = "src/sampleText2.txt";
        try {
            System.out.printf("Содержимое файла \"%s\":\n", path2);
            System.out.println(readFile(path2));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        /*
         * 8. Система логина
         * Создайте метод login(String username, String password),
         * в котором логин и пароль проверяются на корректность.
         * Если один из них не совпадает — выбрасывается LoginFailedException.
         * Исключение должно наследоваться от Exception.
         */
        System.out.println();
        Authentication authentication = new Authentication(new HashMap<>(Map.of( "Bob", "12345", "John", "54321", "Sam","qwerty", "Bill", "ytrewq")));
        try {
            authentication.login("John", "12345");
        } catch (LoginFailedException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            authentication.login("John", "54321");
        } catch (LoginFailedException ex) {
            System.out.println(ex.getMessage());
        }

        /*
         * 9. Банковский перевод с валидацией
         * Метод transfer(fromAccount, toAccount, amount):
         * выбрасывает InvalidTransferAmountException, если сумма <= 0
         * выбрасывает InsufficientBalanceException, если баланс отправителя меньше суммы
         * содержит try-catch в main
         */
        System.out.println();
        Accounts accounts = new Accounts(new HashMap<>(Map.of( "001", 12345.0, "002", 54321.0, "003",10000.0, "004", 30000.0)));

        try {
            accounts.transfer("001", "002", -10000.0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            accounts.transfer("001", "002", 10000.0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            accounts.transfer("001", "003", 20000.0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            accounts.transfer("123", "002", 100.0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            accounts.transfer("001", "321", 200.0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        /*
         * 10. Сервис оценки товара
         * Реализуйте метод rateProduct(int rating), который:
         * принимает значение от 1 до 5
         * выбрасывает InvalidRatingException (checked), если значение вне диапазона
         * сохраняет рейтинг в списке, если всё хорошо
         * также перехватывает NumberFormatException, если рейтинг пришёл в виде строки,
         * но содержит нечисловое значение
         */
        System.out.println();
        try {
            products.getItem("Pineapple").rateProduct("abc");
        } catch (InvalidRatingException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            products.getItem("Pineapple").rateProduct("0");
        } catch (InvalidRatingException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            products.getItem("Pineapple").rateProduct("6");
        } catch (InvalidRatingException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            products.getItem("Pineapple").rateProduct("5");
        } catch (InvalidRatingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static int safeDivide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на ноль запрещено");
        }
        return a / b;
    }

    public static void checkStringArgument(String string) throws IllegalArgumentException {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка пуста или состоит только из пробелов");
        }

        System.out.printf("Строка \"%s\" - допустимый аргумент\n", string);
    }

    public static void setAge(int age) throws IllegalArgumentException {
        if (age < 0) {
            throw new IllegalArgumentException("Возраст не может быть меньше нуля");
        }

        System.out.printf("Установлен возраст: %s\n", age);
    }

    public static void deposit(double amount) throws NegativeDepositException {
        if (amount < 0) {
            throw new NegativeDepositException("Депозит не может быть отрицательным");
        }

        System.out.printf("Депозит на сумму %s обработан\n", amount);
    }

    public static List<String> readFile(String path) throws FileNotFoundException, IOException {
        List<String> stringList = new ArrayList<String>();
        StringBuilder bufferedReaderFile = new StringBuilder();

        /*try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine()) != null) {
                stringList.add(line);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while((line = br.readLine()) != null) {
            stringList.add(line);
        }

        return stringList;
    }
}
