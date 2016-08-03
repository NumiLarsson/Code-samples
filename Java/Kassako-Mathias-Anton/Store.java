import java.util.*;

public class Store {
    private Register[] registers;
    
    public Store(int numOfRegisters) {
	int i = 0;
	registers = new Register[numOfRegisters];
	do {
	    registers[i] = new Register(false);
	    ++i;
	} while (i < numOfRegisters);
	registers[0].open();
    }


    public int getAvrageQueueLength() {
	if (registers.length == 0) return 0;
	int length = 0;
	int i = 0;

	for (Register r : registers) {
	    if (r.isOpen()) {
		length += r.getQueueLength();
		++i;
	    }
	}
	
	return length / i;
    }

    public void newCustomer(Customer c) {
	if (registers.length == 1) {
	    registers[0].addToQueue(c);
	} else {
	    int i = 0;
	    Register shortest = registers[0];	
	    do {
		for (Register r : registers) {	 
		    if (r.getQueueLength() < shortest.getQueueLength() && r.isOpen()) {
			shortest = r;
		    }
		}
		++i;
	    } while (i < registers.length);
	    shortest.addToQueue(c);   
	}	
    }
    public void step() {
	for (Register r : registers) {
	    if (r.isOpen()) {
		r.step();	
	    }
	}
    }
    public void openNewRegister() {
	for (Register r : registers) {
	    if (!r.isOpen()) {
		r.open();
		return;
	    }
	}	
    }
    public Customer[] getDoneCustomers() {
	Customer[] doneCustomers = new Customer[registers.length];
	int i = 0;
	for (Register r : registers) {
	    if (r.hasCustomers() && r.currentCustomerIsDone()) {
		doneCustomers[i] = r.removeCurrentCustomer();	
	    } else {
		doneCustomers[i] = null;
	    }
	    ++i;
	}
	return doneCustomers;
    }
    public String toString() {
	String registerString = "";
	for (Register r : registers) {
	    if (r.isOpen()) {
		registerString += "   [" + r.toString() + "\n\n";
	    } else {
		registerString += " X [ ] \n\n";
	    }
	}
	return registerString;
    }
}
