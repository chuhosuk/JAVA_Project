package model;

//DTO
public class Shoes {

	// 멤버변수
	private String brand;
	private String model;
	private int price;
	private int quantity;

	// 생성자
	public void Shoes(String brand, String model, int price, int quantity) {
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.quantity = quantity;
	}

	// 멤버메서드(Setter,Getter,출력정보)
	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrand() {
		return brand;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public String toString() {
		return brand + " " + model + " " + price + " " + quantity;

	}
}
