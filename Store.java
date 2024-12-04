package application.storelib;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Store implements Serializable{
	String name;
	ArrayList<Product> products = new ArrayList<>();
	Cart cart = new Cart();

	// Constructor
	public Store(String name) {
		this.name = name;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public Cart getCart() {
		return cart;
	}

	// ************** Cart related methods *******************
	public void addProductToCart(String id, int amt) throws CloneNotSupportedException {
		// Call findProduct. If the product exist and the store has enough quantity,
		// Call the addProduct method of Cart class. Otherwise show "Out of Stock" message
		if(cart.findProduct(id)==-1) {
			int index = findProduct(id);
			if (index >= 0) {
				Product p = (Product) products.get(index).clone();
				if (p.getQuantity() >= amt) {
					p.setQuantity(amt);
					cart.addProduct(p);
					return;
				}
			}
		}
		cart.updateProduct(id,amt);
	}

	public void removeProductFromCart(String id) {
		// call removeProduct of Cart class
		cart.removeProduct(id);
	}

	public void updateProductInCart(String id, int count) {
		// Call findProduct (String, ArrayList<>) and Call the updateProduct method of Cart class
		int index = findProduct(id);
		if(index>=0) {
			cart.updateProduct(id, count);
			return;
		}
		System.out.println("Out of Stock");//unnecessary
	}

	public void clearCart() {
		// Call the clearCart method of Cart class
		cart.clearCart();
	}

	//total bill
	public double totalBill(){
		double bill=0;
		for(Product i:cart.items){
			if(i.isOnSale()) {
				bill+=i.salePrice(i.getQuantity());
			}
			else {
				bill+=i.totalPrice(i.getQuantity());
			}
		}
		return bill;
	}

	// Pay Bill
	public double payBill() {
		// Iterate through each of the products in the cart and do the following
		// a) reduce those products quantity, 
		// b) If the item is on sale add the salePrice to the bill.
		// c) if not onSale add the totalPrice to bill. 
		// d) Call the clearCart method
		double bill=0;
		for(Product i:cart.items){
			int index = findProduct(i.getId());
			int quantity=products.get(index).getQuantity()-i.getQuantity();
			products.get(index).setQuantity(quantity);
			if(i.isOnSale()) {
				bill+=i.salePrice(i.getQuantity());
			}
			else {
				bill+=i.totalPrice(i.getQuantity());
			}
		}
		this.clearCart();
		return bill;
	}

	// ****************View related methods ******************
	
	// Admin will see all the information about each product. 
	// Non-admin will be able to view just the name, id and price of the product
	// Display the items in groups (Food, Electronics, Clothing) 
	public ArrayList<Product> viewProducts(boolean isAdmin) {
		// if isAdmin is true call viewProductsAsAdmin for different categories
		// else call viewProducts for different categories
		ArrayList<Product> view = new ArrayList<>();
		if(isAdmin) {
			for(Category category:Category.values())
				view = viewProductsAsAdmin(category);
		}
		else {
			for(Category category:Category.values())
				view = viewProducts(category);
		}
		return view;
	}

	// View the list of items in a specific category. Show only name, id and price
	public ArrayList<Product> viewProducts(Category category) {
		ArrayList<Product> customer = new ArrayList<>();
		for(Product p:products) {
			if(p.getCategory().equals(category)) {
				customer.add(p);
			}
		}
		return customer;
	}

	// View the list of items in a specific category. Show all info about each product.
	// This functionality is for admin
	public ArrayList<Product> viewProductsAsAdmin(Category category) {
		ArrayList<Product> admin = new ArrayList<>();
		for(Product p:products) {
			if(p.getCategory().equals(category)) {
				admin.add(p);
			}
		}
		return admin;
	}
	
	// *************** Admin methods to put a item(s) on sale*************
	
	// Put all food items on sale that will expire within next expireWithin days
	public void putOnSaleFood(Integer expireWithin, int percentage) {
		// Search for the items that will expire within next expireWithin days and call putOnSale for that item
		for(Product p:products) {
			if(p.getCategory().equals(Category.FOOD)) {
				p.putOnSale(expireWithin, percentage);
			}
		}
	}
	
	// Put a specific cloth item on sale
	//changed putOnSaleClothe to putOnSaleAll to do the same work for all Category
	public void putOnSaleAll(String  id, int percentage) {
		// Search for the items with the specific id and call putOnSale for that item
		int index = findProduct(id);
		if(index>=0) {
			products.get(index).putOnSale(id, percentage);
			return;
		}
		System.out.println("Out of Stock.");//unnecessary
	}
	
	// Put a specific Electronic item on sale
	public void putOnSaleElectronic(String  id, int percentage) {
		// Search for the items with the specific id and call putOnSale for that item
		int index = findProduct(id);
		if(index>=0) {
			products.get(index).putOnSale(id, percentage);
			return;
		}
		System.out.println("Out of Stock.");//unnecessary
	}
	
	

	//****************************Admin methods to add items in the store*********

	// This method is for adding clothing item to the store
	public void addProduct(String name, String id, int quantity, String brand, SubCategory subCategory, Size size, double price) {
		// Call the addProduct(Product p) method with Clothing object as parameter
		this.addProduct(new Clothing(name, id, quantity, brand, subCategory, size, price));
	}

	// This method is for adding Electronics item to the store
	public void addProduct(String name, String id, int quantity, String manufacturer, ElectCategory subCategory, double price) {
		// Call the addProduct(Product p) method with Electronics object as parameter
		this.addProduct(new Electronics(name, id, quantity, manufacturer, subCategory, price));
	}

	// This method is for adding Food item to the store
	// I am adding this code as it is little tricky
	public void addProduct(String name, String id, int quantity, LocalDate mfg, LocalDate exp,double price) {
		int index = findProduct(id);
		if(index >=0) {
			Product pr= products.get(index);
			FoodItem item = (FoodItem)pr;
			if (item.getMfgDate().equals(mfg) && 
					item.getExpirationDate().equals(exp)) {
				item.updateQuantity(quantity);
				return;					
			}
		}
		products.add(new FoodItem(name, id, quantity, mfg, exp, price));
	}

	// ******************** private methods**************
	
	public int findProduct(String id) {
		// search the product in the products list using id. 
		// If the product exists return the product, otherwise return -1
		for(Product p:products) {
			if(p.getId().equals(id)) {
				return products.indexOf(p);
			}
		}
		return -1;
	}

	// the following method is a private method for only Clothing and Electronics/
	private void addProduct(Product p) {
		// Check if the item is available in the store.
		// If available increment the quantity. Else add the product in the list
		int index = findProduct(p.getId());
		if(index >=0) {
			Product pr= products.get(index);
			pr.updateQuantity(p.getQuantity());
			return;
		}
		products.add(p);
	}
}