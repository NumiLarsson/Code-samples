public class Customer {

    private int bornTime;
    private int groceries;

    public Customer(int bornTime, int groceries) {
	this.bornTime = bornTime;
	this.groceries = groceries;
    }

    public int getBornTime() {
	return this.bornTime;
    }
    public int getNumGroceries() {
	return this.groceries;
    }
    public void serve() {
	--this.groceries;
    }
    public boolean isDone() {
	return this.groceries == 0;
    }
}
