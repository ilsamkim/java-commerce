package step3;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Category> categories = new ArrayList<>();

        // 1. 전자제품 카테고리
        Category electronics = new Category("전자제품");
        electronics.addProduct(new Product("Galaxy S24", 1200000, "최신 안드로이드 스마트폰", 25));
        electronics.addProduct(new Product("iPhone 15", 1350000, "Apple의 최신 스마트폰", 30));
        electronics.addProduct(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 15));
        electronics.addProduct(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 50));
        categories.add(electronics);

        // 2. 의류 카테고리
        Category clothing = new Category("의류");
        clothing.addProduct(new Product("나이키 후드티", 89000, "겨울용 기모 후드티", 100));
        clothing.addProduct(new Product("리바이스 청바지", 120000, "클래식 핏 데님", 80));
        clothing.addProduct(new Product("아디다스 운동화", 150000, "러닝화 최신 모델", 60));
        categories.add(clothing);

        // 3. 식품 카테고리
        Category food = new Category("식품");
        food.addProduct(new Product("유기농 사과", 15000, "1kg 국내산", 200));
        food.addProduct(new Product("프리미엄 쌀", 50000, "10kg 당일 도정", 150));
        food.addProduct(new Product("제주 감귤", 20000, "2kg 노지 감귤", 180));
        categories.add(food);

        // CommerceSystem 객체 생성
        CommerceSystem system = new CommerceSystem(categories);

        // 프로그램 시작
        system.start();

    }
}
