package lv3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private AdminMode adminMode;
    private List<Category> categories;
    private Cart cart;      // 장바구니 추가

    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
        this.cart = new Cart();
        this.adminMode = new AdminMode(categories, cart);
    }

    public List<Category> getCategories() {
        return categories;
    }

    // start 메서드
    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");

            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
            }

            System.out.println("0. 종료 | 프로그램 종료");

            if (!cart.isEmpty()) {
                System.out.println("\n[ 주문 관리 ]");
                System.out.println((categories.size() + 1) + ". 장바구니 확인");
                System.out.println((categories.size() + 2) + ". 주문 취소");
            }

            System.out.println((categories.size() + 3) + ". 관리자 모드");

            System.out.print("\n선택: ");

            // 입력
            int categoryChoice = sc.nextInt();

            // 종료
            if (categoryChoice == 0) {
                System.out.println("\n커머스 플랫폼을 종료합니다.");
                sc.close();
                break;
            }

            // 장바구니 보기
            if (categoryChoice == categories.size() + 1) {
                if (cart.isEmpty()) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.\n");
                } else {
                    processOrder(sc);
                }
                continue;
            }

            // 주문하기
            if (categoryChoice == categories.size() + 2) {
                if (cart.isEmpty()) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.\n");
                } else {
                    cancelOrder();
                }
                continue;
            }

            if (categoryChoice == categories.size() + 3) {
                adminMode.start(sc);
                continue;
            }

            // 카테고리 선택 & 유효성 검사
            if (categoryChoice >= 1 && categoryChoice <= categories.size()) {
                Category selectedCategory = categories.get(categoryChoice - 1);
                showCategoryProducts(selectedCategory, sc);
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력하세요.\n");
            }
        }
    }

    // cancelOrder 메서드
    private void cancelOrder() {
        cart.clear();
        System.out.println("장바구니가 비워졌습니다. 주문이 취소되었습니다.\n");
    }

    // showCategoryProducts 메서드
    private void showCategoryProducts(Category category, Scanner sc) {
        while (true) {
            System.out.println("\n[ " + category.getCategoryName() + " 카테고리 ]");
            System.out.println("1. 전체 상품 보기");
            System.out.println("2. 가격대별 필터링 (100만원 이하)");
            System.out.println("3. 가격대별 필터링 (100만원 초과)");
            System.out.println("0. 뒤로가기");
            System.out.print("\n선택: ");

            int choice = sc.nextInt();

            if (choice == 0) {
                System.out.println();
                break;
            }

            switch (choice) {
                case 1:
                    showProducts(category, null, sc);
                    break;
                case 2:
                    showProducts(category, "under", sc);
                    break;
                case 3:
                    showProducts(category, "over", sc);
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.\n");
            }
        }
    }

    // 상품 출력
    public void showProducts(Category category, String filter, Scanner sc) {
        List<Product> products = category.getProducts();
        List<Product> filteredProducts;

        // 필터링
        if ("under".equals(filter)) {
            System.out.println("\n[ 100만원 이하 상품 목록 ]");
            filteredProducts = products.stream()
                    .filter(p -> p.getPrice() <= 1000000)
                    .toList();
        } else if ("over".equals(filter)) {
            System.out.println("\n[ 100만원 초과 상품 목록 ]");
            filteredProducts = products.stream()
                    .filter(p -> p.getPrice() > 1000000)
                    .toList();
        } else {
            System.out.println("\n[ 전체 상품 목록 ]");
            filteredProducts = products;
        }

        if (filteredProducts.isEmpty()) {
            System.out.println("해당 조건의 상품이 없습니다.");
            System.out.println("0. 뒤로가기");

            while (true) {
                System.out.print("\n선택: ");
                int choice = sc.nextInt();

                if (choice == 0) {
                    break;
                }
                System.out.println("잘못된 입력입니다.");
            }
            return;
        }

        // 상품 출력 (스트림 사용)
        for (int i = 0; i < filteredProducts.size(); i++) {
            Product p = filteredProducts.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " | "
                    + String.format("%,d", p.getPrice()) + "원 | "
                    + p.getDescription() + " | 재고: " + p.getStock() + "개");
        }

        System.out.println("0. 뒤로가기");
        System.out.print("\n선택: ");

        int productChoice = sc.nextInt();

        if (productChoice == 0) {
            return;
        }

        if (productChoice < 1 || productChoice > filteredProducts.size()) {
            System.out.println("잘못된 입력입니다. 다시 입력하세요.\n");
            return;
        }

        Product selectedProduct = filteredProducts.get(productChoice - 1);
        showProductDetail(selectedProduct, sc);
    }

    // processOrder 메서드
    public void processOrder(Scanner sc) {
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비었습니다.\n");
            return;
        }

        // 장바구니 출력
        cart.showCart();

        int confirm;
        while (true) {
            System.out.println("1. 주문 확정");
            System.out.println("2. 메인으로 돌아가기");
            System.out.print("선택: ");
            confirm = sc.nextInt();

            if (confirm == 1 || confirm == 2) {
                break;
            }
            System.out.println("잘못된 입력입니다. 1 또는 2를 입력하세요.\n");
        }

        if (confirm != 1) {
            System.out.println("메인으로 돌아갑니다.\n");
            return;
        }

        // 고객 등급 선택
        System.out.println("\n고객 등급을 입력해주세요.");
        System.out.println("1. BRONZE : 0% 할인");
        System.out.println("2. SILVER : 5% 할인");
        System.out.println("3. GOLD : 10% 할인");
        System.out.println("4. PLATINUM : 15% 할인");

        int gradeChoice;
        while (true) {
            System.out.print("\n선택: ");
            gradeChoice = sc.nextInt();

            if (gradeChoice >= 1 && gradeChoice <= 4) {
                break;
            }
            System.out.println("잘못된 입력입니다. 1~4를 입력하세요.");
        }

        CustomerGrade grade = CustomerGrade.values()[gradeChoice-1];

        int totalPrice = cart.getTotalPrice();
        int discountAmount = grade.calculateDiscount(totalPrice);
        int finalPrice = grade.calculateFinalPrice(totalPrice);

        System.out.println("\n주문이 완료되었습니다!");
        System.out.println("할인 전 금액: " + String.format("%,d", totalPrice) + "원");
        System.out.println(grade.getName() + " 등급 할인(" + grade.getDiscountRate() + "%): -"
                + String.format("%,d", discountAmount) + "원");
        System.out.println("최종 결제 금액: " + String.format("%,d", finalPrice) + "원");

        // 재고 차감 및 변화 출력
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int previousStock = product.getStock();  // 이전 재고
            int quantity = item.getQuantity();       // 주문 수량

            product.reduceStock(quantity);           // 재고 차감
            int newStock = product.getStock();       // 새로운 재고

            System.out.println(product.getName() + " 재고가 "
                    + previousStock + "개 → " + newStock + "개로 업데이트되었습니다.");
        }

        System.out.println();

        // 장바구니 초기화
        cart.clear();
    }

    // showProductDetail 메서드
    private void showProductDetail(Product product, Scanner sc) {
        System.out.println("\n선택한 상품: " + product.getName() + " | "
                + String.format("%,d", product.getPrice()) + "원 | "
                + product.getDescription() + " | 재고: "
                + product.getStock() + "개");

        System.out.println("\n위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인");
        System.out.println("2. 취소");

        int addToCart;
        while (true) {
            System.out.print("선택: ");
            addToCart = sc.nextInt();

            if (addToCart == 1 || addToCart == 2) {
                break;
            }
            System.out.println("잘못된 입력입니다. 1 또는 2을 입력하세요.");
        }

        if (addToCart == 1) {
            cart.addItem(product, 1);
            System.out.println();
        } else {
            System.out.println("취소되었습니다.\n");
        }
    }


}
