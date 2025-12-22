package lv3;

public class Product {
    private String name;
    private int price;
    private String description;
    private int stock;

    public Product(String name, int price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    // 재고 감소 메서드
    public void reduceStock(int amount) {
        if (amount > 0 && this.stock >= amount) {
            this.stock -= amount;
        } else {
            System.out.println("재고가 부족합니다.");
        }
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
