package lv2;

import java.util.List;
import java.util.Scanner;

public class AdminMode {
    // 관리자 모드 비밀번호 설정
    private static final String admin_password = "1234";
    private List<Category> categories;
    private Cart cart;

    // 생성자
    public AdminMode(List<Category> categories, Cart cart) {
        this.categories = categories;
        this.cart = cart;
    }

    // 관리자 모드 실행
    public void start(Scanner sc) {
        if (!authenticateAdmin(sc)) {
            return;
        }

        while (true) {
            System.out.println("[ 관리자 모드 ]");
            System.out.println("1. 상품 추가");
            System.out.println("2. 상품 수정");
            System.out.println("3. 상품 삭제");
            System.out.println("0. 메인으로 돌아가기");
            System.out.print("\n선택: ");

            int choice = sc.nextInt();

            if (choice == 0) {
                System.out.println("메인으로 돌아갑니다.\n");
                break;
            }

            switch (choice) {
                case 1:
                    addProduct(sc);
                    break;
                case 2:
                    editProduct(sc);
                    break;
                case 3:
                    deleteProduct(sc);
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.\n");
            }
        }
    }

    // 관리자 비밀번호 인증
    private boolean authenticateAdmin(Scanner sc) {
        System.out.println("\n[ 관리자 인증 ]");
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("비밀번호를 입력하세요: ");
            String input = sc.next();

            if (input.equals(admin_password)) {
                System.out.println("인증 성공\n");
                return true;
            }

            attempts++;
            if (attempts < 3) {
                System.out.println("비밀번호가 틀렸습니다. (" + attempts + "/3)\n");
            }
        }

        System.out.println("비밀번호 3회 실패. 메인 메뉴로 돌아갑니다.\n");
        return false;
    }

    // 상품 추가
    private void addProduct(Scanner sc) {
        System.out.println("\n[상품 추가]");

        // 카테고리 선택
        System.out.println("카테고리를 선택하세요:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.print("선택: ");
        int categoryChoice = sc.nextInt();

        if (categoryChoice < 1 || categoryChoice > categories.size()) {
            System.out.println("잘못된 카테고리 번호입니다.\n");
            return;
        }

        Category selectedCategory = categories.get(categoryChoice - 1);

        // 상품 정보 입력
        sc.nextLine(); // 버퍼 비우기
        System.out.print("상품명: ");
        String name = sc.nextLine();

        // 중복 검사
        for (Product p : selectedCategory.getProducts()) {
            if (p.getName().equals(name)) {
                System.out.println("이미 존재하는 상품명입니다.\n");
                return;
            }
        }

        System.out.print("가격: ");
        int price = sc.nextInt();

        sc.nextLine(); // 버퍼 비우기
        System.out.print("설명: ");
        String description = sc.nextLine();

        System.out.print("재고수량: ");
        int stock = sc.nextInt();

        // 상품 추가
        Product newProduct = new Product(name, price, description, stock);
        selectedCategory.addProduct(newProduct);

        System.out.println("\n상품이 추가되었습니다!");
        System.out.println(name + " | " + String.format("%,d", price) + "원 | "
                + description + " | 재고: " + stock + "개\n");
    }

    // 상품 수정
    private void editProduct(Scanner sc) {
        System.out.println("\n[ 상품 수정 ]");

        sc.nextLine(); // 버퍼 비우기
        System.out.print("수정할 상품명을 입력하세요: ");
        String searchName = sc.nextLine();

        // 상품 찾기
        Product targetProduct = findProductByName(searchName);

        if (targetProduct == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.\n");
            return;
        }

        // 현재 정보 출력
        System.out.println("\n[ 현재 상품 정보 ]");
        System.out.println("상품명: " + targetProduct.getName());
        System.out.println("가격: " + String.format("%,d", targetProduct.getPrice()));
        System.out.println("설명: " + targetProduct.getDescription());
        System.out.println("재고: " + targetProduct.getStock() + "개");

        // 수정할 항목 선택
        System.out.println("\n수정할 항목을 선택하세요: ");
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 재고수량");
        System.out.println("0. 취소");
        System.out.print("선택: ");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("새로운 가격: ");
                int newPrice = sc.nextInt();
                targetProduct.setPrice(newPrice);
                System.out.println("가격이 수정되었습니다.\n");
                break;
            case 2:
                sc.nextLine(); // 버퍼 비우기
                System.out.print("새로운 설명: ");
                String newDescription = sc.nextLine();
                targetProduct.setDescription(newDescription);
                System.out.println("설명이 수정되었습니다.\n");
                break;
            case 3:
                System.out.print("새로운 재고수량: ");
                int newStock = sc.nextInt();
                targetProduct.setStock(newStock);
                System.out.println("재고수량이 수정되었습니다.\n");
                break;
            case 0:
                System.out.println("취소되었습니다.\n");
                break;
            default:
                System.out.println("잘못된 입력입니다.\n");
                break;
        }
    }

    // 상품 삭제
    private void deleteProduct(Scanner sc) {
        System.out.println("\n[ 상품 삭제 ]");

        sc.nextLine(); // 버퍼 비우기
        System.out.print("삭제할 상품명을 입력하세요: ");
        String searchName = sc.nextLine();

        //상품 찾기
        Product targetProduct = null;
        Category targetCategory = null;

        for (Category category : categories) {
            for (Product p : category.getProducts()) {
                if (p.getName().equals(searchName)) {
                    targetProduct = p;
                    targetCategory = category;
                    break;
                }
            }
            if (targetProduct != null) break;
        }

        if (targetProduct == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.\n");
            return;
        }

        // 상품 정보 출력
        System.out.println("\n[ 삭제할 상품 정보 ]");
        System.out.println("상품명: " + targetProduct.getName());
        System.out.println("가격: " + String.format("%,d", targetProduct.getPrice()));
        System.out.println("설명: " + targetProduct.getDescription());
        System.out.println("재고수량: " + targetProduct.getStock());

        // 삭제 확인
        System.out.println("\n정말 삭제하시겠습니까? (1: 예, 0: 아니오): ");
        int confirm = sc.nextInt();

        if (confirm == 0) {
            System.out.println("취소되었습니다.\n");
            return;
        } else if  (confirm == 1) {
            // 장바구니에서 제거
            cart.removeProductByName(targetProduct.getName());

            // 카테고리에서 제거
            targetCategory.removeProduct(targetProduct);

            System.out.println(targetProduct.getName() + " 상품이 삭제되었습니다.\n");
        } else {
            System.out.println("잘못된 입력입니다. 다시 입력하세요.\n");
            return;
        }
    }

    // 상품 이름으로 찾기
    private Product findProductByName(String name) {
        for (Category category : categories) {
            for (Product p : category.getProducts()) {
                if (p.getName().equals(name)) {
                    return p;
                }
            }
        }
        return null;
    }
}
