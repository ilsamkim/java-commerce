package lv1;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Category> categories;
    private Cart cart;      // 장바구니 추가

    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
        this.cart = new Cart();
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

            System.out.println((categories.size() + 1) + ". 장바구니 보기");
            System.out.println((categories.size() + 2) + ". 주문하기");
            System.out.println("0. 종료 | 프로그램 종료");
            System.out.print("\n원하시는 카테고리의 번호를 입력하세요: ");

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
                cart.showCart();
                continue;
            }

            // 주문하기
            if (categoryChoice == categories.size() + 2) {
                processOrder(sc);
                continue;
            }

            // 카테고리 선택 & 유효성 검사
            if (categoryChoice >= 1 && categoryChoice <= categories.size()) {
                Category selectedCategory = categories.get(categoryChoice - 1);
                showCategoryProducts(selectedCategory, sc);
            } else {
                System.out.println("잘못된 번호입니다. 다시 입력하세요.\n");
            }
        }
    }

    // showCategoryProducts 메서드
    private void showCategoryProducts(Category category, Scanner sc) {
        while (true) {
            System.out.println("\n[ " + category.getCategoryName() + " 카테고리 ]");

            List<Product> products = category.getProducts();

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.println((i + 1) + ". " + p.getName() + " | "
                        + String.format("%,d", p.getPrice()) + "원 | "
                        + p.getDescription() + " | 재고: " + p.getStock() + "개");
            }

            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int productChoice = sc.nextInt();

            // 뒤로가기
            if (productChoice == 0) {
                System.out.println();
                break;
            }

            // 유효성 검사
            if (productChoice < 1 || productChoice > products.size()) {
                System.out.println("잘못된 번호입니다. 다시 입력하세요.");
                continue;
            }

            // 상품 선택 후 장바구니 담기 여부 확인
            Product selectedProduct = products.get(productChoice - 1);
            showProductDetail(selectedProduct, sc);
        }
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
            System.out.print("주문하시겠습니까? (1: 예, 0: 아니오): ");
            confirm = sc.nextInt();

            if (confirm == 1 || confirm == 0) {
                break;
            }
            System.out.println("잘못된 입력입니다. 1 또는 0을 입력하세요.\n");
        }

        if (confirm != 1) {
            System.out.println("주문이 취소되었습니다.\n");
            return;
        }

        System.out.println("\n주문이 완료되었습니다.");
        System.out.println("총 결제 금액: " + String.format("%,d", cart.getTotalPrice()) + "원");

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

        int addToCart;
        while (true) {
            System.out.print("장바구니에 담으시겠습니까? (1: 예, 0: 아니오): ");
            addToCart = sc.nextInt();

            if (addToCart == 1 || addToCart == 0) {
                break;
            }
            System.out.println("잘못된 입력입니다. 1 또는 0을 입력하세요.");
        }

        // 장바구니에 담기
        if (addToCart == 1) {
            System.out.print("수량을 입력하세요: ");
            int quantity = sc.nextInt();

            // 유효성 검사
            if (quantity <= 0) {
                System.out.println("수량은 1개 이상이어야 합니다.\n");
                return;
            }

            cart.addItem(product, quantity);
        } else {
            System.out.println("취소되었습니다.");
        }
    }


}
