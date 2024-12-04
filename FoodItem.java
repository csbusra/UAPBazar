package application.storelib;

import java.time.LocalDate;

public class FoodItem extends Product{	 // make this a subclass of Product
	private LocalDate mfgDate, expirationDate;
	
	// constructor
	public FoodItem(String name, String id, int quantity, LocalDate mfg, LocalDate exp,double price) {
		super(name,id,Category.FOOD,quantity,price);
		this.mfgDate=mfg;
		this.expirationDate=exp;
	}

	// This method will put an item on sale if the item expires 
	// within next few days which will be decided by the admin.
	@Override
	public void putOnSale(Object criterion, int p) {
		if(criterion instanceof Integer){
			LocalDate exp = LocalDate.now().plusDays((int)criterion);
			if(exp.compareTo(this.expirationDate)>=0) {
				this.setOnSale(true);
				this.setSalePercent(p);
			}
		}
		//to putOnSale by ID
		else{
			this.setOnSale(true);
			this.setSalePercent(p);
		}
	}
	
	public LocalDate getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(LocalDate mfgDate) {
		this.mfgDate = mfgDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	// This details method is for admin user. Admin should be able to see everything.
	public String details() {
		return super.details()+" FoodItem [mfgDate=" + mfgDate + ", expirationDate=" + expirationDate + "]";
	}
	
	
	
}