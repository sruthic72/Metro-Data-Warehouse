
public class Supplier {

	String supplier_id;
	String supplier_name;
	
	public Supplier(String supplier_id, String supplier_name) {
		
		this.supplier_id = supplier_id;
		this.supplier_name = supplier_name;
		
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	
}
