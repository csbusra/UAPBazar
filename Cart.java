package application.storelib;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
	ArrayList<Product> items= new ArrayList<>();

	public ArrayList<Product> getItems() {
		return items;
	}

	public void addProduct(Product p) {
		// Add p to products;
		items.add(p);
	}
	
		
	// Search for the item in the items list and remove the item;
	public void removeProduct(String id) {
		int index = findProduct(id);
		if(index >=0) {
			items.remove(index);
			return;
		}
		System.out.println("Not in Cart.");
	}
	
	// Search for the item in the items list and update the quantity of the item;
	public void updateProduct(String id, int count) {
		int index = findProduct(id);
		if(index>=0) {
			items.get(index).setQuantity(count);
			return;
		}
		System.out.println("Not in Cart.");
	}
	
	// View the items in the cart and their details which include the name, quantity and price
	// If any item is on sale, the discounted price will be shown
	public void viewCart() {
		for(Product p:items) {
			System.out.println(p);
		}
	}
	
	// clear the items in the cart
	public void clearCart() {		
		items.clear();
	}
	
	// Search for a specific product and return the index in the items arrayList.
	// If the product is not available return -1
	public int findProduct(String id) {
		// search the product in the items list using id. 
		// If the product exists return the product, otherwise return null
		for(Product p:items) {
			if(p.getId().equals(id)) {
				return items.indexOf(p);
			}
		}
		return -1;
		
	}

}