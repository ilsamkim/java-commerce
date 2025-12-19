package step2;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    // Product 관리 리스트
    private final List<Product> products;

    // 생성자
    public CommerceSystem(List<Product> products) {
        this.products = products;
    }

    // start 메서드
    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            // 상품 목록 출력
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.println((i + 1) + ". " + p.getName() + " | "
                        + String.format("%,d", p.getPrice()) + "원 | "
                        + p.getDescription());
            }

            System.out.println("0. 종료 | 프로그램 종료");
            System.out.print("재고량 확인을 원하시는 상품의 번호를 입력하세요: ");

            // 입력
            int choice = sc.nextInt();

            // 종료 조건
            if (choice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                break;
            }

            // 재고 조회 & 유효성 검사
            if (choice >= 1 && choice <= products.size()) {
                Product q = products.get(choice - 1);
                System.out.println(q.getName() + " " + q.getStock() + "개");
            } else {
                System.out.println("잘못된 번호입니다. 다시 입력하세요.");
            }
        }
    }
}
