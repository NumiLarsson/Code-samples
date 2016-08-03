public class QueueTest
{
    public static void main(String args[])
    {
	// Test String
	Queue<String> testString = new Queue<String>(null, null);
	
	testString.enqueue("String");

	System.out.println(testString.first());

	testString.enqueue("String2");

	testString.dequeue();
	
	System.out.println(testString.first());

	// Interger
	Queue<Integer> testInt = new Queue<Integer>(null, null);
	
	testInt.enqueue(12);

	System.out.println(testInt.first());
    }
}

