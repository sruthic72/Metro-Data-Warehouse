The project consists of four parts:

	1. Generation of Master Data
	2. Creation of Data Warehouse Schema (tables creation)
	3. Java code for MeshJoin and data loading
	4. MySQL OLAP queries 

The first part is the set of MySQL queries create the Master Data.
	
	The queries are saved in a .sql file named as 'Transactional _ MasterData Generator.sql'.
	The queries from this file are copy/pasted into MySQL Workbench and executed all at once.
	A new database schema named as 'db' should appear with three tables named 'transactions', 'products', and 'customers'.

The second part creates the star schema for data warehouse, it has the sql queries to create dimension tables with appropriate attributes.
	
	The queries are saved in a file named 'createDW.sql'.
	The queries from this file are copy/pasted into MySQL Workbench and executed all at once.
	A new database schema named as 'metrowarehouse' should appear with several dimension tables and one fact table.
	
The third part consists of Java code that takes transactional data from stream, loads customers and products data from master data, and applied mesh join, and writes the output tuples to the datawarehouse tables:
	
	For this, execute the given Java project on Eclipse ID. 
	Provide the database credentials.
	Provide the name of the database for master data, namely 'db' in this case.
	Run the java code and wait for a couple of minutes.
	The code will display a 'Bye' message on the console, meaning that the code has executed successfully.
	After successful execution, the fact table shall be populated with 10,000 entries, the products table with 100 entries, the customers table with 50 entries, the suppliers table with 20 entries, and the stores table with 10 entries.

The fourth part contains the OLAP queries.

	The queries are contained in a file named 'queriesDW.sql'.
	The queries from this file are copy/pasted into MySQL Workbench and executed all at once.
	The results of each query will appear on the output window of MySQL Workbench.
