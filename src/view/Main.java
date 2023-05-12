package view;

import java.util.HashMap;
import java.util.Map;

import controller.AdminImpl;
import controller.CustomerImpl;

public class Main {
	public static void main(String[] args) {

		MenuImpl menu = MenuImpl.getInstance();
		menu.menu();
	}

}
