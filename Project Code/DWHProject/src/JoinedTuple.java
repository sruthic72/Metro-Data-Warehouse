
public class JoinedTuple {

	String transaction_ID;
	String product_ID;
	String customer_ID;
	String time_ID;
	String store_ID;
	String store_name;
	String t_date;
	String quantity;
	String product_name;
	String supplier_id;
	String supplier_name;
	String price;
	float sale;
	String customer_name;
	
	JoinedTuple(){
				
		
	}

	public String getTransaction_ID() {
		return transaction_ID;
	}

	public void setTransaction_ID(String transaction_ID) {
		this.transaction_ID = transaction_ID;
	}

	public String getProduct_ID() {
		return product_ID;
	}

	public void setProduct_ID(String product_ID) {
		this.product_ID = product_ID;
	}

	public String getCustomer_ID() {
		return customer_ID;
	}

	public void setCustomer_ID(String customer_ID) {
		this.customer_ID = customer_ID;
	}

	public String getTime_ID() {
		return time_ID;
	}

	public void setTime_ID(String time_ID) {
		this.time_ID = time_ID;
	}

	public String getStore_ID() {
		return store_ID;
	}

	public void setStore_ID(String store_ID) {
		this.store_ID = store_ID;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getT_date() {
		return t_date;
	}

	public void setT_date(String t_date) {
		this.t_date = t_date;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public float getSale() {
		return sale;
	}

	public void setSale(float sale) {
		this.sale = sale;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public int getCustomerHash() {
		
		return Integer.parseInt(this.customer_ID.substring(2));
		
	}

	public void print() {
		
		String data = 
				
			transaction_ID  + " " +
			product_ID + " " +
			customer_ID  + " " +
			time_ID  + " " +
			store_ID  + " " +
			store_name  + " " +
			t_date  + " " +
			quantity  + " " +
			product_name + " " +
			supplier_id + " " +
			supplier_name + " " +
			price + " " +
			Float.toString(sale) + " " +
			customer_name;
		
		System.out.println(data);
		
	}
	
}
