
public class Product {

	String product_id;
	String product_name;
	String supplier_ID;
	String supplier_name;
	String price;
	
	public Product(String product_id, String product_name, String supplier_ID, String supplier_name, String price) {
		
		this.product_id = product_id;
		this.product_name = product_name;
		this.supplier_ID = supplier_ID;
		this.supplier_name = supplier_name;
		this.price = price;
		
	}

	public String getSupplier_ID() {
		return supplier_ID;
	}

	public void setSupplier_ID(String supplier_ID) {
		this.supplier_ID = supplier_ID;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	public int getProductHash() {
		
		return Integer.parseInt(this.product_id.substring(4));
		
	}

	
	
}
