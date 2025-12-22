package lv3;

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
            System.out.println("4. 전체 상품 현황");
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
                case 4:
                    showAllProduct();
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.\n");
            }
        }
    }

    // 관리자 비밀번호 인증
    private boolean authenticateAdmin(Scanner sc) {
        System.out.print("관리자 비밀번호를 입력해주세요: ");
        int attempts = 0;

        while (attempts < 3) {
            String input = sc.next();

            if (input.equals(admin_password)) {
                System.out.println("인증 성공\n");
                return true;
            }

            attempts++;
            if (attempts < 3) {
                System.out.println("비밀번호가 틀렸습니다. (" + attempts + "/3)\n");
                System.out.print("관리자 비밀번호를 입력해주세요: ");
            }
        }

        System.out.println("비밀번호 3회 실패. 메인 메뉴로 돌아갑니다.\n");
        return false;
    }

    // 전체 상품 현황
    private void showAllProduct() {
        System.out.println("\n[ 전체 상품 현황 ]\n");

        for (Category category : categories) {
            System.out.println("[ " + category.getCategoryName() + " ]");

            List<Product> products = category.getProducts();
            if (products.isEmpty()) {
                System.out.println(" 등록된 상품이 없습니다.");
            } else {
                for (int i = 0; i < products.size(); i++) {
                    Product p =  products.get(i);
                    System.out.println("  " + (i + 1) + ". " + p.getName() + " | "
                            + String.format("%,d", p.getPrice()) + "원 | "
                            + p.getDescription() + " | 재고: " + p.getStock() + "개");
                }
            }
            System.out.println();
        }
    }

    // 상품 추가
    private void addProduct(Scanner sc) {
        System.out.println("\n어느 카테고리에 상품을 추가하시겠습니까?");

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }

        int categoryChoice = sc.nextInt();

        if (categoryChoice < 1 || categoryChoice > categories.size()) {
            System.out.println("잘못된 카테고리 번호입니다.\n");
            return;
        }

        Category selectedCategory = categories.get(categoryChoice - 1);

        // 상품 정보 입력
        sc.nextLine(); // 버퍼 비우기
        System.out.print("상품명을 입력해주세요: ");
        String name = sc.nextLine();

        // 중복 검사
        for (Product p : selectedCategory.getProducts()) {
            if (p.getName().equals(name)) {
                System.out.println("이미 존재하는 상품명입니다.\n");
                return;
            }
        }

        System.out.print("가격을 입력해주세요: ");
        int price = sc.nextInt();

        sc.nextLine(); // 버퍼 비우기
        System.out.print("상품 설명을 입력해주세요: ");
        String description = sc.nextLine();

        System.out.print("재고수량을 입력해주세요: ");
        int stock = sc.nextInt();

        System.out.println("\n" + name + " | " + String.format("%,d", price) + "원 | "
                + description + " | 재고: " +  stock + "개");
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인");
        System.out.println("2. 취소");

        int confirm;
        while (true) {
            int choice = sc.nextInt();

            if (choice == 1 ||  choice == 2) {
                confirm = choice;
                break;
            }
            System.out.println("잘못된 입력입니다. 1 또는 2를 입력하세요.");
        }

        if (confirm != 1) {
            System.out.println("상품 추가가 취소되었습니다.\n");
            return;
        }

        // 상품 추가
        Product newProduct = new Product(name, price, description, stock);
        selectedCategory.addProduct(newProduct);

        System.out.println("상품이 성공적으로 추가되었습니다!\n");
    }

    // 상품 수정
    private void editProduct(Scanner sc) {
        sc.nextLine(); // 버퍼 비우기
        System.out.print("\n수정할 상품명을 입력하세요: ");
        String searchName = sc.nextLine();

        // 상품 찾기
        Product targetProduct = findProductByName(searchName);

        if (targetProduct == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.\n");
            return;
        }

        // 현재 정보 출력
        System.out.println("\n[ 현재 상품 정보 ]");
        System.out.println(targetProduct.getName() + " | "
                + String.format("%,d", targetProduct.getPrice()) + "원 | "
                + targetProduct.getDescription() + " | 재고: "
                + targetProduct.getStock() + "개");

        // 수정할 항목 선택
        System.out.println("\n수정할 항목을 선택하세요: ");
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 재고수량");
        System.out.print("선택: ");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.println("\n현재 가격: " + String.format("%,d", targetProduct.getPrice()) + "원");
                System.out.print("새로운 가격을 입력해주세요: ");
                int newPrice = sc.nextInt();
                int oldPrice = targetProduct.getPrice();
                targetProduct.setPrice(newPrice);
                System.out.println(targetProduct.getName() + "의 가격이 "
                        + String.format("%,d", oldPrice) + "원 → "
                        + String.format("%,d", newPrice) + "원으로 수정되었습니다.\n");
                break;
            case 2:
                System.out.println("\n현재 설명: " + targetProduct.getDescription());
                sc.nextLine(); // 버퍼 비우기
                System.out.print("새로운 설명을 입력해주세요: ");
                String newDescription = sc.nextLine();
                targetProduct.setDescription(newDescription);
                System.out.println(targetProduct.getName() + "의 설명이 수정되었습니다.\n");
                break;
            case 3:
                System.out.println("\n현재 재고수량: " + targetProduct.getStock() + "개");
                System.out.print("새로운 재고수량을 입력해주세요: ");
                int newStock = sc.nextInt();
                int oldStock = targetProduct.getStock();
                targetProduct.setStock(newStock);
                System.out.println(targetProduct.getName() + "의 재고수량이 "
                        + oldStock + "개 → " + newStock + "개로 수정되었습니다.\n");
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
        System.out.print("\n정말 삭제하시겠습니까? (1: 예, 0: 아니오): ");
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
