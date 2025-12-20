package lv1;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String categoryName;        // 카테고리 이름
    private List<Product> products;     // 카테고리에 속한 상품들

    // 생성자
    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.products = new ArrayList<>();
    }

    // 카테고리 이름 반환
    public String getCategoryName() {
        return categoryName;
    }

    // 상품 리스트 반환
    public List<Product> getProducts() {
        return products;
    }

    // 상품 관리 메서드
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
