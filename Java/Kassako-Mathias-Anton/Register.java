import java.util.*;

public class Register {
    private boolean open;
    private Queue<Customer> queue;

    public Register(boolean open) {
	this.open = open;
	this.queue = new Queue<Customer>(null, null);
    }

    public void open() {
	this.open = true;
    }
    public void close() {
	this.open = false;
    }
    public boolean isOpen() {
	return this.open;
    }
    
    public void step() {
	if (this.queue.first() != null) {
	    this.queue.first().serve();	   
	}
    }

    public boolean hasCustomers() {
	return this.queue.lenght() != 0;
    }

    public boolean currentCustomerIsDone() {
	if (this.queue.first() != null) {
	    return this.queue.first().getNumGroceries() == 0;
	}
	return true;
    }

    public void addToQueue(Customer c) {
	this.queue.enqueue(c);
    }

    public Customer removeCurrentCustomer() {
	return this.queue.dequeue();
    }

    public int getQueueLength() {
	return this.queue.lenght();
    }
    
    public String toString() {
	int length = getQueueLength();
	int numGroceriers = 0;
	if (this.queue.first() != null) {
	    numGroceriers = this.queue.first().getNumGroceries();
	}
	String queueString = numGroceriers + "]";
	for (int i = 0; i < length; ++i) {
	    queueString += "@";
	}
	return queueString;
    }
}
