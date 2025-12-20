package step3;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private List<Category> categories;

    // 생성자
    public CommerceSystem(List<Category> categories) {
        this.categories = categories;
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
            System.out.print("원하시는 카테고리의 번호를 입력하세요: ");

            // 입력
            int categoryChoice = sc.nextInt();

            // 종료 조건
            if (categoryChoice == 0) {
                System.out.println("\n커머스 플랫폼을 종료합니다.");
                sc.close();
                break;
            }

            // 유효성 검사
            if (categoryChoice < 1 || categoryChoice > categories.size()) {
                System.out.println("잘못된 번호입니다. 다시 입력하세요.");
                continue;
            }

            // 선택된 카테고리 보여주기
            Category selectedCategory = categories.get(categoryChoice - 1);
            showCategoryProducts(selectedCategory, sc);
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
                        + p.getDescription());
            }

            System.out.println("0. 뒤로가기");
            System.out.print("재고확인을 원하시는 제품의 번호를 입력하세요: ");

            int productChoice = sc.nextInt();

            // 뒤로가기
            if (productChoice == 0) {
                System.out.println();
                break;
            }

            // 선택한 상품 정보 출력
            Product selectedProduct = products.get(productChoice - 1);
            System.out.println("\n선택한 상품: " + selectedProduct.getName() + " | "
                    + String.format("%,d", selectedProduct.getPrice()) + "원 | "
                    + selectedProduct.getDescription() + " | 재고: "
                    + selectedProduct.getStock() + "개\n");

            // 유효성 검사
            if (productChoice < 1 || productChoice > products.size() ) {
                System.out.println("잘못된 번호입니다. 다시 입력하세요.");
            }
        }
    }

}
