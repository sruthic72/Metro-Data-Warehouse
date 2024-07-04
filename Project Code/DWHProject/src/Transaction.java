
public class Transaction {

	String transaction_ID;
	String product_ID;
	String customer_ID;
	String store_ID;
	String store_Name;
	String time_ID;
	String t_date;
	String quantity;

	public Transaction(String transaction_ID, String product_ID, String customer_ID, String store_ID, String store_Name, String time_ID, String t_date, String quantity) {
		
		this.transaction_ID = transaction_ID;
		this.product_ID = product_ID;
		this.customer_ID = customer_ID;
		this.store_ID = store_ID;
		this.store_Name = store_Name;
		this.time_ID = time_ID;
		this.t_date = t_date;
		this.quantity = quantity;
		
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


	public String getStore_ID() {
		return store_ID;
	}


	public void setStore_ID(String store_ID) {
		this.store_ID = store_ID;
	}


	public String getStore_Name() {
		return store_Name;
	}


	public void setStore_Name(String store_Name) {
		this.store_Name = store_Name;
	}


	public String getTime_ID() {
		return time_ID;
	}


	public void setTime_ID(String time_ID) {
		this.time_ID = time_ID;
	}
	
	public void print() {
		
		System.out.println(this.customer_ID + " " + " " + this.product_ID + " " + this.store_ID + " " + this.store_Name + " " + this.time_ID + " " + this.transaction_ID); 
		
	}
	
	public int getProductHash() {
		
		return Integer.parseInt(this.product_ID.substring(4));
		
	}
	
}
