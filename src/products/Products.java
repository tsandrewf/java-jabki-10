package products;

import java.util.HashMap;
import java.util.Map;

public class Products {
    private Map<String, Product> productMap;

    public Products(Map<String, String> productNameMap) {
        this.productMap = new HashMap<String, Product>();
        productNameMap.forEach((key,value) -> this.productMap.put(key, new Product(value)));
    }

    public Product getItem(String code) {
        if (code == null || code.isBlank()) {
            throw new ItemNotFoundException("Не задан код товара");
        }

        if (!this.productMap.containsKey(code)) {
            throw new ItemNotFoundException("Товар по коду \"" + code + "\" не найден в карте товаров");
        }

        return this.productMap.get(code);
    }
}
