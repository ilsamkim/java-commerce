package lv3;

public enum CustomerGrade {
    BRONZE("BRONZE", 0),
    SILVER("SILVER", 5),
    GOLD("GOLD", 10),
    PLATINUM("PLATINUM", 15);

    private final String name;
    private final int discountRate;

    CustomerGrade(String name, int discountRate) {
        this.name = name;
        this.discountRate = discountRate;
    }

    public String getName() {
        return name;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    // 할인 금액 계산
    public int calculateDiscount(int totalPrice) {
        return totalPrice * discountRate / 100;
    }

    // 최종 금액 계산
    public int calculateFinalPrice(int totalPrice) {
        return totalPrice - calculateDiscount(totalPrice);
    }
}
