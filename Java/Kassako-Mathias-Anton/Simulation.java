import java.util.*;

public class Simulation {
    private Store store;
    private int time;
    private int intensity;
    private int maxGroceries;
    private float thresholdForNewRegister;

    private int customersServed;
    private int maxTime;
    private float avrageTime;

    private String ifDoneCustomers;

    public Simulation(int intensity, int maxGroceries, float thresholdForNewRegister) {
	this.store = new Store(5);
	this.time = 0;
	this.intensity = intensity;
	this.maxGroceries = maxGroceries;
	this.thresholdForNewRegister = thresholdForNewRegister;
	this.customersServed = 0;
    }
    
    public void step() {
        this.store.step();
	++time;
	Random random = new Random();
	int i = random.nextInt(100 - 0 + 1) + 0;
	if (i < this.intensity) {
	    int x = random.nextInt(maxGroceries - 1 + 1) + 1;
	    Customer newC = new Customer(time, x);
	    store.newCustomer(newC);
	}

	int avrageQueueLenght = store.getAvrageQueueLength();
	if (avrageQueueLenght > thresholdForNewRegister) {
	    store.openNewRegister();
	}
	this.ifDoneCustomers = store.toString();
	Customer doneCustomers[] = store.getDoneCustomers();
	extractStatistic(doneCustomers);
    }

    private void extractStatistic(Customer customers[]) {
	if (customers != null) {
	    int maxTimeArray[] = new int[customers.length];
	    float avrageTimeCombined = 0;
	    int x = 0;
	    int i = 0;
	    for (Customer c : customers) {
		maxTimeArray[i] = 1;
		if (customers[i] != null) {
		    ++customersServed;
		    ++x;
		    int bornTime = c.getBornTime();		    
		    maxTimeArray[i] = time - bornTime;
		    avrageTimeCombined += time - bornTime;
		}
		++i;
	    }
	    if (x > 0) {
		Arrays.sort(maxTimeArray);
		int newMaxTime =  maxTimeArray[customers.length - 1];
		if (this.maxTime < newMaxTime) {
		    this.maxTime = newMaxTime;
		}
		float avragetimeForStep = avrageTimeCombined / x;
		if (avragetimeForStep >= 0) {
		    this.avrageTime = avragetimeForStep;	
		}
	    }
	    
	}
    }

    
    public String toString() {	
        return this.ifDoneCustomers + "Number of customers serverd: " + this.customersServed + "\n"
	    + "Max wait-time: " + this.maxTime + "\nAvrage wait-time: " + this.avrageTime;
    }
}
