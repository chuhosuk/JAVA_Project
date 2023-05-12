package controller;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import model.Shoes;
import view.MenuImpl;

public class AdminImpl implements Admin {

	static Scanner sc = new Scanner(System.in);

	private AdminImpl() {
	}

	private static AdminImpl instance = new AdminImpl();

	public static AdminImpl getInstance() {
		if (instance == null) {
			instance = new AdminImpl();
		}
		return instance;
	}

	Map<Integer, Shoes> list = new HashMap<Integer, Shoes>();
	Map<Integer, Shoes> income = new HashMap<Integer, Shoes>();

	public void stockList() {
		for (Map.Entry<Integer, Shoes> str : list.entrySet()) {
			System.out.println(str.getKey() + " " + str.getValue());
		}
	}

	@Override
	public void productList() { // 추가된 상품리스트를 보여주는 메서드
		MenuImpl menu = MenuImpl.getInstance();
		System.out.println("=========상품 목록=========");
		System.out.println("번호" + "    " + "브랜드" + "    " + "모델" + "    " + "  가격" + "    " + " 수량");
		for (Map.Entry<Integer, Shoes> str : list.entrySet()) {
			System.out.println(str.getKey() + " " + str.getValue());
		}
		menu.product();
	}

	@Override
	public void productAdd() { //상품추가 메서드
		Shoes shoes = new Shoes();
		try {
		System.out.println("========모델등록========");
		System.out.println("브랜드:");
		shoes.setBrand(sc.next());
		System.out.println("모델명:");
		shoes.setModel(sc.next());
		System.out.println("가격:");
		shoes.setPrice(sc.nextInt());
		System.out.println("수량:");
		shoes.setQuantity(sc.nextInt());

		int goodsNum = (int) (Math.random() * 1000) + 1000;

		list.put(goodsNum, shoes);
		System.out.println("상품이 등록되었습니다.");

		productList();
	
		
	}catch (InputMismatchException e) {
		productAdd();
		System.out.println("잘못 입력하셨습니다");
	}
	}

	@Override
	public void productUpdate() { //상품수정 메서드
		MenuImpl menu = MenuImpl.getInstance();

		Shoes shoes = new Shoes();
		System.out.println("수정하려는 상품의 코드를 입력하세요. [이전:0]");
		try {
		int update = sc.nextInt();

		if (update == 0) {

			menu.product();
		} else {
			System.out.println("브랜드:");
			shoes.setBrand(sc.next());
			System.out.println("모델명:");
			shoes.setModel(sc.next());
			;
			System.out.println("가격:");
			shoes.setPrice(sc.nextInt());
			System.out.println("수량:");
			shoes.setQuantity(sc.nextInt());

			list.put(((Integer) update), shoes);
			productList();
		}
		}catch (InputMismatchException e) {
			productUpdate();
			System.out.println("잘못 입력하셨습니다");
		}
	}

	@Override
	public void productRemove() { //상품삭제 메서드
		System.out.println("삭제하려는 상품의 코드를 입력하세요. [이전:0] :");
		try {
		int delete = sc.nextInt();

		if (list.containsKey(delete)) {
			list.remove(delete);
			productList();
		} else {
			productList();
		}
		}catch (InputMismatchException e) {
			productRemove();
			System.out.println("잘못 입력하셨습니다");
		}
	}

	@Override
	public void orderSelect() {// 고객이 주문한 상품을 보여주는 메서드
		CustomerImpl order = CustomerImpl.getInstance();
		System.out.println("=========구매 요청 목록=========");
		System.out.println("번호" + "    " + "브랜드" + "    " + "모델" + "    " + "  가격" + "    " + " 수량");
		for (Map.Entry<Integer, Shoes> str : order.order.entrySet()) {
			System.out.println(str.getKey() + " " + str.getValue());
		}
		MenuImpl menu = MenuImpl.getInstance();
		menu.orderList();
	}

	@Override // 결제승인
	public void orderConfirm() { //주문승인 메서드
		System.out.println("결제 승인할 코드를 입력하세요. [이전:0] :");
		try {
		int code = sc.nextInt();
		do {
			CustomerImpl cus = CustomerImpl.getInstance();
			if (cus.order.containsKey(code)) {
				balance += cus.order.get(code).getPrice() * cus.order.get(code).getQuantity();
				income.put(code, cus.order.get(code));
				cus.order.remove(code);
				System.out.println("=================");
				System.out.println(" 결제 승인 되었습니다.");
				System.out.println("=================");
				break;
			}
			if (list.containsKey(code)) {
				Shoes shoes = new Shoes();
				shoes.setBrand(list.get(code).getBrand());
				;
				shoes.setModel(list.get(code).getModel());
				;
				shoes.setPrice(list.get(code).getPrice());
				;
				shoes.setQuantity(list.get(code).getQuantity() - cus.order.get(code).getQuantity());
				;

			}
			if (code == 0) {
				break;
			}
		} while (true);
		MenuImpl menu = MenuImpl.getInstance();
		menu.orderList();
		}catch(InputMismatchException e) {
			orderConfirm();
			System.out.println("잘못 입력하셨습니다");
		}
	}

	@Override
	public void orderCancel() { //주문취소 메서드
		CustomerImpl cus = CustomerImpl.getInstance();
		MenuImpl menu = MenuImpl.getInstance();

		System.out.println("=========환불 요청 목록=========");
		System.out.println("번호" + "    " + "브랜드" + "    " + "모델" + "    " + "  가격" + "    " + " 수량");
		for (Map.Entry<Integer, Shoes> str : cus.refundMap.entrySet()) {
			System.out.println(str.getKey() + " " + str.getValue());
		}

		System.out.println("취소하려는 상품의 코드를 입력하세요 [이전:0]: ");
		try {
		int cancel = sc.nextInt();
		do {
			if (cus.refundMap.containsKey(cancel)) {
				cus.refundMap.remove(cancel);
				System.out.println("취소처리가 완료 되었습니다.");
				menu.orderList();
			}
			if (cancel == 0)
				;
			break;
		} while (true);
		}catch(InputMismatchException e) {
			orderCancel();
			System.out.println("잘못 입력하셨습니다");
		}

	}

	@Override
	public void saleTotal() { //결산금액 메서드
		System.out.println("Total 판매금액:" + balance);
		MenuImpl menu = MenuImpl.getInstance();
		menu.orderList();

	}

	int balance = 0;

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}

	

}
