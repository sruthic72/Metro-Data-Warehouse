import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class meshjoin {

	public static void load(Connection connection, HashMap<Integer, ArrayList<JoinedTuple>> hashTable) throws SQLException {
				
		String customerTableName = "customers";
		String productTableName = "products";
		String storeTableName = "store";
		String supplierTableName = "supplier";
		String timeTableName = "time";
		String factTableName = "fact";


//		Statement getter = connection.createStatement();
//		ResultSet response=getter.executeQuery("select * from " + customerTableName + " WHERE CUST_ID = '2352';");  
//		//ResultSet response=getter.executeQuery("select * from customers where cust_id = '1234';");  
//
//		if (response.next() == false) {
//			
//			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("INSERT INTO " + customerTableName + " VALUES ('" + customer_ID + "','" + customer_name + "')");
//
//		}
//		
//		return;
		
		for (Integer key : hashTable.keySet()) {

			for(JoinedTuple jt : hashTable.get(key)) {
				
				//insert to customers
				Statement stmt1 = connection.createStatement();
				
				try {
					

					stmt1.executeUpdate("INSERT INTO " + customerTableName + " VALUES ('" + jt.getCustomer_ID() + "','" + jt.getCustomer_name() + "')");  
				
				}
				catch(Exception e) {
					
				}				
				
				//insert to products
				Statement stmt2 = connection.createStatement();
				
				try {
					

					stmt2.executeUpdate("INSERT INTO " + productTableName + " VALUES ('" + jt.getProduct_ID() + "','" + jt.getProduct_name() + "')");  
				
				}
				catch(Exception e) {
					
				}				

				//insert to stores
				Statement stmt3 = connection.createStatement();
				
				try {
					
					stmt3.executeUpdate("INSERT INTO " + storeTableName + " VALUES ('" + jt.getStore_ID() + "','" + jt.getStore_name() + "')");  
				
				}
				catch(Exception e) {
					
				}	
				
				//insert to supplier
				Statement stmt4 = connection.createStatement();
				
				try {
					

					stmt4.executeUpdate("INSERT INTO " + supplierTableName + " VALUES ('" + jt.getSupplier_id() + "','" + jt.getSupplier_name() + "')");  
				
				}
				catch(Exception e) {
					
				}				

				//insert to supplier
				Statement stmt5 = connection.createStatement();
				
				try {
					
					Date date = Date.valueOf(jt.getT_date());
					int day = date.getDay();
					int month = date.getMonth();
					int year = date.getYear();
					int quarter = month / 3;
					
					stmt5.executeUpdate("INSERT INTO " + timeTableName + " VALUES ('" + jt.getT_date() + "','" + day + "','" + month + "','" + quarter+ "','" + (year+1900) + "')");  
				
				}
				catch(Exception e) {
					
				}				

				//insert to facts
				Statement stmt6 = connection.createStatement();
				
				try {

					stmt6.executeUpdate("INSERT INTO " + factTableName + " VALUES ('" + jt.getTransaction_ID() + "','" + jt.getProduct_ID() + "','" + jt.getCustomer_ID() + "','" + jt.getStore_ID() + "','" + jt.getT_date() + "','" + jt.getSupplier_id()+ "','" + jt.getQuantity() + "','" + jt.getSale() + "')");  
				
				}
				catch(Exception e) {
					
				}			
			
			}
			
			//clear the ArrayList at current bucket
			hashTable.get(key).removeAll(hashTable.get(key));
			
		}
		
		
	
	}
	
	public static void join(Connection connection, Connection connection2) throws SQLException {
		
		
		Statement stmt = connection.createStatement();
		
		ArrayList<Transaction> stream = new ArrayList<Transaction>();
		ResultSet transactions=stmt.executeQuery("select * from transactions");  
		//ResultSet streamBuffer=stmt.executeQuery("select * from transactions limit " + 120 + "," +  10 +  ";");  

		while(transactions.next()) {
			
			stream.add(new Transaction(transactions.getString(1), transactions.getString(2), transactions.getString(3), transactions.getString(4), transactions.getString(5), transactions.getString(6), transactions.getString(7), transactions.getString(8)));
			
		}
		
		//stream now has all the transactions
		
//		for(int i = 0; i < streamBuffer.size(); i++)
//			stream.get(i).print();
		

	
		int numMDPartitions = 5;
	
		int productMDTotalSize = 100;
		int productMDChunkSize = productMDTotalSize / numMDPartitions;
				
		int customerMDTotalSize = 50;
		int customerMDChunkSize = customerMDTotalSize / numMDPartitions;
				
		//now that we have a hashmap
		HashMap<Integer, ArrayList<JoinedTuple>> hashTable = new HashMap<Integer, ArrayList<JoinedTuple>>();
	
		//we need to create space for the maximum hash locations
		int hashTableSize = productMDTotalSize > customerMDTotalSize ? productMDTotalSize : customerMDTotalSize;
	
		//now, allocate the space for maximum buckets, with null tuples
		for(int i = 0; i < hashTableSize; i++) {
			
			hashTable.put(i, new ArrayList<JoinedTuple>());
			
		}
		
//		for(int i = 0; i < hashTableSize; i++) {
//			
//			if (hashTable.get(i) != null)
//				System.out.println("Size at " + (i) + " is: " + hashTable.get(i).size());
//			else
//				System.out.println("Size at " + (i) + " is 0");
//		}
//		
		
		Queue<ArrayList<Transaction>> queue = new LinkedList<ArrayList<Transaction>>();
		ArrayList<Transaction> streamBuffer;
		
		int streamBufferSize = 10; //assumption
		
		while(stream.size() > 0) {
		
			streamBuffer = new ArrayList<Transaction>();
			
			while(stream.size() > 0 && streamBuffer.size() < streamBufferSize) {
				
				//get max elements into the stream
				streamBuffer.add(stream.get(0));
				stream.remove(0);
				
			}
			
			//now we add the streamBuffer to our queue
			queue.add(new ArrayList<Transaction>(streamBuffer));
			streamBuffer.removeAll(streamBuffer);
			
			//first we join the transaction data with the products table
			//for that, we need to map the transactions on the hashtable, where the key
			//for hashing will be the key of product_id
			
			//get the head of queue
			//while queue.poll() != null
			ArrayList<Transaction> transactionChunk = queue.poll(); 
			
			for(int i = 0; i < transactionChunk.size(); i++) {
				
				JoinedTuple newTuple = new JoinedTuple();
				newTuple.setTransaction_ID(transactionChunk.get(i).getTransaction_ID());
				newTuple.setProduct_ID(transactionChunk.get(i).getProduct_ID());
				newTuple.setCustomer_ID(transactionChunk.get(i).getCustomer_ID());
				newTuple.setTime_ID(transactionChunk.get(i).getTime_ID());
				newTuple.setStore_ID(transactionChunk.get(i).getStore_ID());
				newTuple.setStore_name(transactionChunk.get(i).getStore_Name());
				newTuple.setT_date(transactionChunk.get(i).getT_date());
				newTuple.setQuantity(transactionChunk.get(i).getQuantity());
				
				//get the hash index based on product key
				hashTable.get(transactionChunk.get(i).getProductHash()).add(newTuple);
				
			}
		
	//		for(int i = 0; i < hashTableSize; i++) {
	//			
	//			if (hashTable.get(i) != null)
	//				System.out.println("Size at " + (i) + " is: " + hashTable.get(i).size());
	//			else
	//				System.out.println("Size at " + (i) + " is 0");
	//
	//		}
	
			//now join it with all the chunks from products master data
			
			for(int startIndex = 0; startIndex < productMDTotalSize; startIndex += productMDChunkSize)
			{		
				Statement stmt2 = connection.createStatement();
				ArrayList<Product> diskBuffer = new ArrayList<Product>();
				ResultSet products = stmt2.executeQuery("select * from products limit " + startIndex + "," +  productMDChunkSize +  ";");  
				
				while(products.next()) {
					
					diskBuffer.add(new Product(products.getString(1), products.getString(2), products.getString(3), products.getString(4), products.getString(5)));
					
				}
				
				//now just go to the hash indices of the products and append the values to the tuple
				for(int i = 0; i < diskBuffer.size(); i++) {
					
					ArrayList<JoinedTuple> hashBucket = hashTable.get(diskBuffer.get(i).getProductHash());
					
					for(int j = 0; j < hashBucket.size(); j++) {
						
						hashBucket.get(j).setProduct_name(diskBuffer.get(i).getProduct_name());
						hashBucket.get(j).setSupplier_id(diskBuffer.get(i).getSupplier_ID());
						hashBucket.get(j).setSupplier_name(diskBuffer.get(i).getSupplier_name());
						hashBucket.get(j).setPrice(diskBuffer.get(i).getPrice());
						hashBucket.get(j).setSale(Float.parseFloat(hashBucket.get(j).getPrice()) * Float.parseFloat(hashBucket.get(j).getQuantity()));
					
					}
					
				}
				
	
	
			}
	
			//System.out.println("Hash keys (products):-\n");
			
			//now we are going to map the values of the hash table based on customer id
			//that means we are gonna switch the keys
		
			//print to see the hashes and values
	//		for (Integer i : hashTable.keySet()) {
	//			
	//			String val = "No value";
	//			
	//			if(hashTable.get(i).size() != 0)
	//				val = hashTable.get(i).get(0).product_ID;
	//			
	//			System.out.println(i + " -> " + val);
	//			
	//			
	//		}
			
	//		int sum_of_tables = 0;
	//		
	//		for (Integer i : hashTable.keySet()) {
	//	
	//			sum_of_tables += hashTable.get(i).size();
	//			
	//			
	//		}
	//		
	//		System.out.println("Sum of tables before: " + sum_of_tables);
			
			//now, we will perform our key shuffle logic
			//get all the keys in the dictionary
			for (Integer i : hashTable.keySet()) {
				
				//get the ArrayList at the ith index
				ArrayList<JoinedTuple> currentArrayList = hashTable.get(i);
				
				//now, for all the elements in this current ArrayList, check if
				//that element is placed correctly according to customer hash
				//if not, place it on the correct index it belongs to
				for(JoinedTuple jt : currentArrayList) {
					
					if (jt.getCustomerHash() != i) {
						
						hashTable.get(jt.getCustomerHash()).add(jt);
						
					}
					
				}
	
				//now after adding the incorrectely hashed tuple to correct bucket,
				//we will remove this tuple from the incorrect bucket
				for(int x = 0; x < currentArrayList.size(); x++) {
					
					JoinedTuple jt = currentArrayList.get(x);
					
					if (jt.getCustomerHash() != i) {
						
						currentArrayList.remove(jt);
						x--;
						
					}
					
				}
			}
	
			//System.out.println("Hash keys (Customers):-\n");
			//print to see the hashes and values
			for (Integer i : hashTable.keySet()) {
				
				String val = "No value";
				
				if(hashTable.get(i).size() != 0)
					val = hashTable.get(i).get(0).customer_ID;
				
				//System.out.println(i + " -> " + val);
				
				
			}
	
	//		sum_of_tables = 0;
	//		
	//		for (Integer i : hashTable.keySet()) {
	//	
	//			sum_of_tables += hashTable.get(i).size();
	//			
	//			
	//		}
	//		
	//		System.out.println("Sum of tables after: " + sum_of_tables);
	
			
			//now join it with all the chunks from customers master data
			
			for(int startIndex = 0; startIndex < customerMDTotalSize; startIndex += customerMDChunkSize)
			{		
				Statement stmt2 = connection.createStatement();
				ArrayList<Customer> diskBuffer = new ArrayList<Customer>();
				ResultSet customers = stmt2.executeQuery("select * from customers limit " + startIndex + "," +  customerMDChunkSize +  ";");  
				
				while(customers.next()) {
					
					diskBuffer.add(new Customer(customers.getString(1), customers.getString(2)));
					
				}
				
				//now just go to the hash indices of the customers and append the values to the tuple
				for(int i = 0; i < diskBuffer.size(); i++) {
					
					ArrayList<JoinedTuple> hashBucket = hashTable.get(diskBuffer.get(i).getCustomerHash());
					
					for(int j = 0; j < hashBucket.size(); j++) {
						
						hashBucket.get(j).setCustomer_name(diskBuffer.get(i).getCustomer_name());
					
					}
					
				}
				
			}
	
			//by now, all the data in our hashtable should be joined with both MD tables
			//let's try it out by printing it
			for(int i = 0; i < hashTable.size(); i++) {
	
				if(hashTable.get(i).size() > 0) {
		
					ArrayList<JoinedTuple> al = hashTable.get(i);
	
					for(int j = 0; j < al.size(); j++) {
						
						//al.get(j).print();
						
					}
					
				}
				
			}
			
			load(connection2, hashTable);
			
		}
		
		int sum_of_tables = 0;
		
		for (Integer i : hashTable.keySet()) {
	
			sum_of_tables += hashTable.get(i).size();
			
		}
		
		//send all the data from the hashtable to the DWH
		//clear the hashtable for new transactions
		
		//System.out.println("Sum of tables finally: " + sum_of_tables);

		
		connection.close();  
	}
	
	
    public static void main(String[] args) {

        System.out.println("Connecting to database..");

        Scanner myObj = new Scanner(System.in); // Create a Scanner object

        // Default values for command line arguments
        String username = args.length > 0 ? args[0] : "";
        String password = args.length > 1 ? args[1] : "";
        String port = args.length > 2 ? args[2] : "";
        String dbname = args.length > 3 ? args[3] : "";
        String dwhname = "metrowarehouse"; // Assuming this is constant

        if(username.isEmpty()){
            System.out.println("Enter database username: ");
            username = myObj.next(); // Read user input
        }

        if(password.isEmpty()){
            System.out.println("Enter database password: ");
            password = myObj.next(); // Read user input
        }

        if(port.isEmpty()){
            System.out.println("Enter database server port: ");
            port = myObj.next(); // Read user input
        }

        if(dbname.isEmpty()){
            System.out.println("Enter database schema name: ");
            dbname = myObj.next(); // Read user input
        }

        String url = "jdbc:mysql://localhost:" + port + "/" + dbname;
        System.out.println("URL to database is: " + url); // Output user input

        String url2 = "jdbc:mysql://localhost:" + port + "/" + dwhname;
        System.out.println("URL to warehouse is: " + url2); // Output user input

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            Connection connection2 = DriverManager.getConnection(url2, username, password);

            System.out.println("Database connected!");

            System.out.println("ETL Starting..");
            System.out.println("Running Meshjoin..");
            join(connection, connection2);

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        System.out.println("*** ETL Done Successfully ***");
        System.out.println("*** R-Meshjoin Applied ***");
        System.out.println("--------------------------");
        System.out.println("DataWarehouse Successfully Populated!");
    }
	
}
