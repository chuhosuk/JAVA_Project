package controller;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import model.Shoes;
import view.MenuImpl;

public class CustomerImpl implements Customer {
	Map<Integer, Shoes> addCart = new HashMap<Integer, Shoes>();// 장바구니
	Map<Integer, Shoes> order = new HashMap<Integer, Shoes>();// 구매목록
	Map<Integer, Shoes> refundMap = new HashMap<Integer, Shoes>();// 환불신청목록

	static Scanner sc = new Scanner(System.in);

	private CustomerImpl() {
	}

	public static CustomerImpl instance = new CustomerImpl();

	public static CustomerImpl getInstance() {
		if (instance == null) {
			instance = new CustomerImpl();
		}
		return instance;
	}

	@Override
	public void cartList() { // 고객이 추가한 장바구니 상품을 출력하는 메서드

		System.out.println("============고객==============");
		System.out.println("-----------장바구니-----------");
		System.out.println("번호" + "    " + "브랜드" + "    " + "모델" + "    " + "  가격" + "    " + " 수량");
		for (Map.Entry<Integer, Shoes> str : addCart.entrySet()) {
			System.out.println(str.getKey() + " " + str.getValue());
		}
	}

	@Override
	public void cartAdd() { // 장바구니 추가 메서드
		AdminImpl admin = AdminImpl.getInstance();
		MenuImpl menu = MenuImpl.getInstance();
		System.out.println("=========상품 목록=========");
		System.out.println("번호" + "    " + "브랜드" + "    " + "모델" + "    " + "  가격" + "    " + " 수량");
		admin.stockList();
		try {
			do {
				System.out.println("장바구니에 담을 상품의 코드를 입력하세요. [이전:0]:");
				int cart = sc.nextInt();

				if (admin.list.containsKey(cart)) {
					Shoes shoes = new Shoes();
					System.out.println("수량을 입력하세요:");
					int qty = sc.nextInt();
					shoes.setBrand(admin.list.get(cart).getBrand());
					shoes.setModel(admin.list.get(cart).getModel());
					shoes.setPrice(admin.list.get(cart).getPrice());
					shoes.setQuantity(qty);
					addCart.put(cart, shoes);
					System.out.println("장바구니에 담겼습니다.");
				}
				if (cart == 0) {
					menu.cart();
				}
				cartList();
			} while (true);

		} catch (InputMismatchException e) {
			System.out.println("잘못 입력하셨습니다");
			cartAdd();
		}
	}

	@Override
	public void cartRemove() { // 장바구니 삭제 메서드
		AdminImpl admin = AdminImpl.getInstance();
		cartList();
		try {
			do {
				MenuImpl menu = MenuImpl.getInstance();
				System.out.println("삭제하려는 상품의 코드를 입력하세요 [이전:0]: ");
				int del = sc.nextInt();

				if (addCart.containsKey(del)) {
					addCart.remove(del);
					System.out.println("목록에서 삭제 되었습니다.");

				}
				if (del == 0) {
					menu.cart();
				}
			} while (true);

		} catch (InputMismatchException e) {
			cartAdd();
			System.out.println("잘못 입력하셨습니다");
		}
	}

	@Override
	public void cartBuy() { // 장바구니 구매 메서드
		MenuImpl menu = MenuImpl.getInstance();
		Shoes shoes = new Shoes();
		try {
			do {
				cartList();
				System.out.println(" 구매할 상품의 코드를 입력하세요. [이전:0] :");
				int buy = sc.nextInt();

				if (addCart.containsKey(buy)) {
					order.put(buy, addCart.get(buy));
					AdminImpl admin = AdminImpl.getInstance();
					shoes.setBrand(admin.list.get(buy).getBrand());
					shoes.setModel(admin.list.get(buy).getModel());
					shoes.setPrice(admin.list.get(buy).getPrice());
					shoes.setQuantity(admin.list.get(buy).getQuantity() - order.get(buy).getQuantity());
					;
					admin.list.put(buy, shoes);
					addCart.remove(buy);
				}
				System.out.println("구매 요청 되었습니다.");
				menu.customerMenu();

				if (buy == 0) {
					break;
				}
			} while (true);
			menu.customerMenu();

		} catch (InputMismatchException e) {
			cartBuy();
			System.out.println("잘못 입력하셨습니다");
		}
	}

	@Override
	public void nowBuy() { // 바로구매 메서드
		AdminImpl admin = AdminImpl.getInstance();
		System.out.println("=========상품 목록=========");
		System.out.println("번호" + "    " + "브랜드" + "    " + "모델" + "    " + "  가격" + "    " + " 수량");
		admin.stockList();
		try {
			do {
				System.out.println(" 구매할 상품의 코드를 입력하세요. [이전:0] :");
				int buy = sc.nextInt();
				Shoes shoes = new Shoes();
				if (admin.list.containsKey(buy)) {
					System.out.println("수량을 입력하세요:");
					int qty = sc.nextInt();

					shoes.setBrand(admin.list.get(buy).getBrand());
					shoes.setModel(admin.list.get(buy).getModel());
					shoes.setPrice(admin.list.get(buy).getPrice());
					shoes.setQuantity(qty);
					order.put(buy, shoes);
					System.out.println("구매 요청 되었습니다.");
				}
				MenuImpl menu = MenuImpl.getInstance();
				menu.customerMenu();
				if (buy == 0) {
					break;
				}
			} while (true);
		} catch (InputMismatchException e) {
			nowBuy();
			System.out.println("잘못 입력하셨습니다");
		}
	}

	@Override
	public void refund() { // 구매한 상품을 환불하는 메서드
		MenuImpl menu = MenuImpl.getInstance();
		AdminImpl admin = AdminImpl.getInstance();
		purchaseComplete();
		try {
			do {
				System.out.println("환불하려는 상품의 코드를 입력하세요: 이전[0]");
				int refund = sc.nextInt();

				if (admin.income.containsKey(refund)) {
					admin.balance -= admin.income.get(refund).getPrice() * admin.income.get(refund).getQuantity();
					refundMap.put(refund, admin.income.get(refund));
					order.remove(refund);
					System.out.println("환불 요청 되었습니다.");
					menu.customerMenu();
				} else if (refund == 0) {
					menu.customerMenu();
				} else {
					System.out.println("잘못 입력하셨습니다.");
					menu.customerMenu();
				}

			} while (true);
		} catch (InputMismatchException e) {
			refund();
			System.out.println("잘못 입력하셨습니다");
		}

	}

	@Override
	public void purchaseComplete() { // 구매완료한 목록을 출력하는 메서드
		System.out.println("=========구매 완료 목록=========");
		System.out.println("번호" + "    " + "브랜드" + "    " + "모델" + "    " + "  가격" + "    " + " 수량");
		AdminImpl admin = AdminImpl.getInstance();
		for (Map.Entry<Integer, Shoes> str : admin.income.entrySet()) {
			System.out.println(str.getKey() + " " + str.getValue());
		}

	}

}
