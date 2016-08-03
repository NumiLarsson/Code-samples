public class LinkedListTest {
    public static void main (String args[]){
		LinkedList<Integer> testList = new LinkedList<Integer>();

		testList.addElement(5);
		System.out.println("Number is: " + testList.getNode(0));
		
		testList.deleteElement(5);

		for (int i = 0; i < 10; i++){
			testList.addElement(i);
		}

		System.out.println("Added 10 numbers in order");
		for (int i = 0; i < testList.getLength(); i++) {
			System.out.print("Position " + i + ": ");
			System.out.println(testList.getElement(i));
		}

		System.out.println();
		testList.deleteElement(7);
		System.out.println("Deleted 7, it should be missing");
		for (int i = 0; i < testList.getLength(); i++) {
			System.out.println("with getNode() position " + i + ": " + testList.getNode(i));
		}
		for (int i = 0; i < 10; i++) {
			System.out.println("with getElement() requesting " + i + ": " + testList.getElement(i));
		}

		LinkedList<String> testStringList = new LinkedList<String>();
		testStringList.addElement("Hello");
		testStringList.addElement("World");
		System.out.print("String is: " + testStringList.getElement("Hello"));
		System.out.println(" " + testStringList.getNode(1));
    }
}
