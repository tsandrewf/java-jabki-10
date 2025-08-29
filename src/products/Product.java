package products;

public class Product {
    private String name;
    private Integer rating;

    public Product(String name) {
        this.name = name;
        this.rating = null;
    }

    public String getName() {
        return this.name;
    }

    public void rateProduct(String rating) throws InvalidRatingException {
        int intRating;

        try {
            intRating = Integer.parseInt(rating);
            if (intRating < 1 || intRating > 5) {
                throw new InvalidRatingException(intRating);
            }
            this.rating = intRating;
            System.out.printf("Товару \"%s\" установлен рейтинг \"%s\"\n", this.getName(), this.rating);
        } catch (NumberFormatException ex) {
            System.out.printf("Рейтинг содержит нечисловое значение \"%s\"\n", rating);
        }
    }
}
