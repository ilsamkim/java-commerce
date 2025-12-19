package step2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Product 리스트 생성
        ArrayList<Product> products = new ArrayList<>();

        // 상품 추가
        products.add(new Product("Galaxy S24", 1200000, "최신 안드로이드 스마트폰", 1));
        products.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 1));
        products.add(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 1));
        products.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 1));

        // CommerceSystem 객체 생성
        CommerceSystem system = new CommerceSystem(products);

        // 프로그램 시작
        system.start();

    }
}
