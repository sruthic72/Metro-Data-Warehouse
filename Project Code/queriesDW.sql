-- Some OLAP queries on our data warehouse schema

-- Q1. Determine the top 3 store names who generated highest sales in September, 2017.
 
select store.store_id,  store.date, SUM(store.sales) from fact store, time t where t.date = store.date and t.month=8 GROUP BY store.store_id order by SUM(store.sales) desc limit 3;

-- Q2. Find Top 10 suppliers that generated most revenue over the weekends. 

SELECT supplier.supplier_name, fact.supplier_id, SUM(fact.sales)
FROM fact
INNER JOIN supplier ON fact.supplier_id=supplier.supplier_id
AND fact.date in (
	
    select time.date from time where time.day = 5 or time.day = 6

)
group by fact.supplier_id
order by SUM(fact.sales) desc limit 10;

select * from time;
select * from supplier;

-- Q3. Present total sales of all products supplied by each supplier with respect to quarter and month.

SELECT supplier.SUPPLIER_Name, time.QUARTER, time.MONTH, products.P_NAME, SUM(SALES)
from fact
join products on products.P_ID = fact.P_ID
join supplier on fact.SUPPLIER_ID = supplier.SUPPLIER_ID
join time on fact.DATE = time.DATE
GROUP BY supplier_name,time.quarter,time.month,p_name;

-- Q4. Present total sales of each product sold by each store. The output should be organised store wise and then product wise under each store.

SELECT fact.store_id, fact.p_id, SUM(fact.sales) FROM fact GROUP BY fact.store_id, fact.p_id order by fact.store_id, fact.p_id;

-- Q5. Present the quarterly sales analysis for all stores using drill down query concepts.

drop view if exists QUARTERLY_SALES_ANALYSIS;
create view QUARTERLY_SALES_ANALYSIS AS
select store.store_name, time.quarter, sum(fact.sales) as total_sales from fact join store on fact.store_id = store.store_id
	join time on fact.date = time.date
	group by store.store_name, time.quarter;

SELECT
    store_name, 
    max(case when quarter = '0'  then total_sales end) AS quarter_1,
	max(case when quarter = '1'  then total_sales end) AS quarter_2,
	max(case when quarter = '2'  then total_sales end) AS quarter_3,
	max(case when quarter = '3'  then total_sales end) AS quarter_4
FROM
    QUARTERLY_SALES_ANALYSIS
GROUP BY
    store_name;

-- Q6. Find the 5 most popular products sold over the weekends.

SELECT products.p_name, fact.p_id, COUNT(fact.sales)
FROM fact
INNER JOIN products ON fact.p_id=products.p_id
AND fact.date in (
	
    select time.date from time where time.day = 5 or time.day = 6

)
group by fact.p_id
order by COUNT(fact.sales) desc limit 5;

-- Q7. Preform ROLLUP operation to store, supplier, and product. 

SELECT   
    store_id, supplier_id, p_id, sum(sales)
FROM   
    fact
GROUP BY  
    store_id, supplier_id, p_id WITH ROLLUP;

-- Q8. Extract total sales of each product for the first and second half of year 2017 along with its total yearly sales.

select products.p_id, products.p_name,
sum(case when time.quarter= '0' or time.QUARTER = '1' then fact.sales end) first_half,
    sum(case when time.quarter = '2' or time.QUARTER = '3' then fact.sales end) second_half,
    sum(fact.sales) as total_yearly_sales
from fact
join products on fact.p_id = products.p_id
join time on fact.date = time.date
where time.year = '2017'
group by products.p_id, time.year
order by products.p_name asc, time.quarter asc;	
	
-- Q9. Find an anomaly in the data warehouse dataset. 

select p_name, count(p_name) from products group by p_name having count(p_name) > 1;
	
-- Q10. Create a materialised view with name “STORE_PRODUCT_ANALYSIS” that presents store and product wise sales. The results should be ordered by store name and then product name.

drop view if exists STORE_PRODUCT_ANALYSIS;
CREATE VIEW STORE_PRODUCT_ANALYSIS AS
select products.p_name, store.store_name, sum(fact.sales) from fact join store on fact.store_id = store.store_id join products on fact.p_id = products.p_id group by fact.p_id, fact.store_id order by store.store_name, products.p_name;
