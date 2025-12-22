package lv2;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }


    // 장바구니에 상품 추가
    public void addItem(Product product, int quantity) {
        // 이미 장바구니에 있는 상품인지 확인
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                // 기존 수량에 추가
                int newQuantity = item.getQuantity() + quantity;

                // 재고 확인
                if (!checkStock(product, newQuantity)) {
                    return;
                }

                item.addQuantity(quantity);
                System.out.println(product.getName() + "의 수량이 " + quantity + "개 추가되었습니다.");
                return;
            }
        }

        // 재고 확인
        if (!checkStock(product, quantity)) {
            return;
        }

        items.add(new CartItem(product, quantity));
        System.out.println(product.getName() + "이(가) " + quantity + "개 장바구니에 추가되었습니다.");
    }


    // 재고 확인 메서드
    private boolean checkStock(Product product, int quantity){
        if (product.getStock() < quantity) {
            System.out.println("재고가 부족합니다. (현재 재고: " + product.getStock() + "개)");
            return false;
        }
        return true;
    }


    // 장바구니 출력
    public void showCart() {
        if (items.isEmpty()) {
            System.out.println("장바구니가 비었습니다.\n");
            return;
        }

        System.out.println("\n[ 장바구니 목록 ]");
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            Product p = item.getProduct();
            System.out.println((i + 1) + ". " + p.getName() + " | "
                    + String.format("%,d", p.getPrice()) + "원 | "
                    + "수량: " + item.getQuantity() + "개 | "
                    + "소계: " + String.format("%,d", item.getTotalPrice()) + "원");
        }
        System.out.println("총 금액: " + String.format("%,d", getTotalPrice()) + "원\n");
    }


    // 총 금액 계산
    public int getTotalPrice(){
        int total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }


    // 장바구니 비었는지 확인
    public boolean isEmpty() {
        return items.isEmpty();
    }


    // 장바구니 항목 가져오기
    public List<CartItem> getItems() {
        return items;
    }


    // 장바구니 비우기
    public void clear() {
        items.clear();
    }


    // 상품 이름으로 장바구니에서 제거
    public void removeProductByName(String name) {
        items.removeIf(item -> item.getProduct().getName().equals(name));
    }
}
