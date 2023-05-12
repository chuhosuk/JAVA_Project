package view;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import controller.AdminImpl;
import controller.CustomerImpl;
import model.Code;

public class MenuImpl implements Menu, Code {
	static Scanner sc = new Scanner(System.in);

	private MenuImpl() {
	}

	private static MenuImpl instance = new MenuImpl();

	public static MenuImpl getInstance() {
		if (instance == null) {
			instance = new MenuImpl();
		}
		return instance;
	}

	@Override
	public void menu() { // 메인메뉴 메서드
		System.out.println("------------메뉴-------------");
		System.out.println("1.고객  2.관리자  3.회원가입  4.종료");
		System.out.println("----------------------------");
		System.out.println("메뉴번호를 입력하세요:");
		try {
			int log = sc.nextInt();
			switch (log) {
			case 1:
				customer();
				break;
			case 2:
				login();
				break;
			case 3:
				register();
				break;
			case 4:
				System.exit(4);
				break;
			default:
				System.out.println("잘못된 번호입니다.");
				menu();
			}
		} catch (InputMismatchException e) {
			sc.next();
			System.out.println("숫자를 입력하세요.");
			menu();
		}

	}

	@Override
	public void login() { // 관리자 로그인 메서드
		Map<String, String> admin = new HashMap<String, String>();
		admin.put("host", "host");

		do {
			System.out.print("관리자ID:");
			String id = sc.next();
			if (admin.containsKey(id)) {
				System.out.print("관리자PW:");
				String pw = sc.next();
				if (pw.equals(admin.get(id))) {
					System.out.println("로그인 성공");
					break;
				} else {
					System.out.println("비빌번호 불일치");
				}
			} else {
				System.out.println("ID 불일치");
				login();
			}

		} while (true);
		admin();

	}

	public void admin() { // 관리자메뉴 메서드
		System.out.println("-----------관리자------------");
		System.out.println("1.재고관리 2.주문관리 3.로그아웃 ");
		System.out.println("----------------------------");
		System.out.println("메뉴 번호를 입력하세요:");

		try {
			String admin1 = sc.next();

			switch (admin1) {
			case "1":
				product();
				break;
			case "2":
				orderList();
				break;
			case "3":
				menu();
				break;
			default:
				System.out.println("잘못된 번호입니다.");
				menu();
			}
		} catch (InputMismatchException e) {
			sc.next();
			System.out.println("숫자를 입력하세요.");
			admin();

		}

	}

	@Override
	public void product() { // 상품추가 메서드
		AdminImpl admin = AdminImpl.getInstance();
		System.out.println("---------------관리자---------------");
		System.out.println("1.목록 2.추가 3.수정 4.삭제 5.이전");
		System.out.println("-----------------------------------");
		System.out.println("메뉴 번호를 입력하세요:");
		try {
			String product = sc.next();
			switch (product) {
			case "1":
				admin.productList();
				break;
			case "2":
				admin.productAdd();
				break;
			case "3":
				admin.productUpdate();
				break;
			case "4":
				admin.productRemove();
				break;
			case "5":
				admin();
				break;
			default:
				System.out.println("잘못된 번호입니다.");
				menu();
			}
		} catch (InputMismatchException e) {
			sc.next();
			System.out.println("숫자를 입력하세요.");
			product();
		}
	}

	Map<String, String> login = new HashMap<String, String>();

	@Override
	public void register() { // 회원가입 메서드
		System.out.println("회원가입");
		System.out.print("id:");
		id = sc.next();
		System.out.print("pw:");
		pw = sc.next();
		login.put(id, pw);
		System.out.println("회원가입 완료");
		menu();
	}

	@Override
	public void orderList() { // 주문목록 메서드
		AdminImpl impl = AdminImpl.getInstance();
		System.out.println("---------------관리자---------------");
		System.out.println("1.주문목록" + "  " + "2.결제승인" + "  " + "3.결제취소" + " " + " 4.결산" + " " + " 5.이전");
		System.out.println("-------------------------------------");
		System.out.println("     메뉴 번호를 입력하세요. :");
		try {
			int list = sc.nextInt();
			switch (list) {
			case 1:
				impl.orderSelect();
				break;
			case 2:
				impl.orderConfirm();
				break;
			case 3:
				impl.orderCancel();
				break;
			case 4:
				impl.saleTotal();
				break;
			case 5:
				admin();
				break;
			default:
				System.out.println("잘못된 번호입니다.");
				orderList();

			}
		} catch (InputMismatchException e) {
			sc.next();
			System.out.println("숫자를 입력하세요.");
			orderList();
		}

	}

	@Override
	public void customer() { // 고객 로그인 메서드
		Map<String, String> login = new HashMap<String, String>();
		login.put(id, pw);
		do {
			System.out.print("회원ID:");
			String id = sc.next();
			if (login.containsKey(id)) {
				System.out.print("회원PW:");
				String pw = sc.next();
				if (pw.equals(login.get(id))) {
					System.out.println("로그인 성공");
					customerMenu();
				} else {
					System.out.println("비빌번호 불일치");
				}
			} else {
				System.out.println("ID 불일치");
				break;
			}

		} while (true);
		menu();

	}

	@Override
	public void customerMenu() { // 고객메뉴 메서드
		CustomerImpl cus = CustomerImpl.getInstance();
		System.out.println("------------고객-------------");
		System.out.println("1.장바구니 2.바로구매 3.환불 4.로그아웃");
		System.out.println("--------------------------");
		System.out.println("    메뉴번호를 입력하세요:");
		try {
			int cm = sc.nextInt();
			switch (cm) {
			case 1:
				cart();
				break;
			case 2:
				cus.nowBuy();
				break;
			case 3:
				cus.refund();
				break;
			case 4:
				menu();
				break;
			default:
				System.out.println("잘못된 번호입니다.");
				menu();
			}
		} catch (InputMismatchException e) {
			sc.next();
			System.out.println("숫자를 입력하세요.");
			customerMenu();

		}
	}

	@Override
	public void cart() { // 장바구니 메뉴 메서드
		CustomerImpl cus = CustomerImpl.getInstance();
		System.out.println("-----------장바구니-----------");
		System.out.println("1.추가 2.삭제 3.구매 4.이전");
		System.out.println("--------------------------");
		System.out.println("메뉴 번호를 입력하세요:");
		try {
			int cart = sc.nextInt();
			switch (cart) {
			case 1:
				cus.cartAdd();
				break;
			case 2:
				cus.cartRemove();
				break;
			case 3:
				cus.cartBuy();
				break;
			case 4:
				customerMenu();
				break;
			default:
				System.out.println("잘못된 번호입니다.");
				menu();
			}
		} catch (InputMismatchException e) {
			sc.next();
			System.out.println("숫자를 입력하세요.");
			cart();
		}
	}

	String id = "0";
	String pw = "0";

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getPw() {
		return pw;
	}

}
