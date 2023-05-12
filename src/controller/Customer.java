package controller;

public interface Customer {
	
	//고객 - 장바구니
	
	public void cartList();		//장바구니 목록
	public void cartAdd();    	//장바구니 추가
	public void cartRemove(); 	//장바구니 삭제
	public void cartBuy();  	//장바구니 구매
	
	//고객 - 바로구매
	public void nowBuy();  		//바로구매
	public void purchaseComplete(); //구매완료
	//고객 - 환불
	public void refund(); 		//환불

}
