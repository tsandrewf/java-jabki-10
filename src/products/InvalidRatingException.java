package products;

public class InvalidRatingException extends Exception {
    public InvalidRatingException(int rating) {
        super("Рейтинг " + rating + " находится вне диапазона - от 1 до 5");
    }
}
