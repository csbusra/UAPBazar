package application.storelib;

public class Electronics extends Product{ // make this a subclass of Product
	private String manufacturer;
	private ElectCategory electCategory; // Can be String or other data type as well

	// Constructor
	public Electronics(String name, String id, int quantity, String manufacturer, ElectCategory electCategory, double price) {
		super(name, id,Category.ELECTRONIC, quantity, price);
		this.manufacturer = manufacturer;
		this.electCategory = electCategory;
	}

	// getter/setter
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ElectCategory getElectCategory() {
		return electCategory;
	}

	public void setElectCategory(ElectCategory electCategory) {
		this.electCategory = electCategory;
	}

	// Override the putOnSale method
	// This method will put an item on sale and also set the sale percentage
	// The item id and sale percentage will be decided by the admin.
	@Override
	public void putOnSale(Object id, int percentage) {
		this.setOnSale(true);
		this.setSalePercent(percentage);
		//id?
	}

	// This details method is for admin user. Admin should be able to see everything.
	public String details(){
		return super.details()+" Electronics [manufacturer=" + manufacturer + ", subCategory=" + electCategory + "]";
	}
}

