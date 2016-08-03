//Standard linked list impletemented in Java with generic types.
public class LinkedList<T> {
    protected Node<T> first;
    protected Node<T> last;
    protected int length = 0;

	public LinkedList () {
		this.length = 0;
		this.first = null;
		this.last = null;
    }

	//easy check for the length of the list.
    public int getLength(){
		return this.length;
    }

	//Add a new node to the back of the list.
    public boolean addElement(T element){
		Node<T> tempNode = new Node<T>(element);
		if (this.first == null){
			this.first = tempNode;
			++this.length;
			return true;
		}
		else if (this.last == null){
			this.first.setNext(tempNode);
			this.last = tempNode;
			++this.length;
			return true;
		}
		else {
			this.last.setNext(tempNode);
			this.last = tempNode;
			++this.length;
			return true;
		}
    }

	//Get node based on position in the list, returns null if the number is out of bounds.
	//Needs to make the position absolute, as it doesn't currently check for negative numbers and it'd easily break it.
	public T getNode(int nodePos) {
		if (this.first == null) {
			return null;
		}
		
		Node<T> tempNode = this.first;

		for (int i = 0; i < nodePos; i++){
			if (tempNode != null){
				tempNode = tempNode.getNext();
			}
			else {
				return null;
			}
		}
		return tempNode.getElement();
	}

	//Retreive a known object from the list, returns null if the list doesn't contain such an element.
    public T getElement(T element){
	if (this.first != null) {
	    Node<T> tempNode = this.first;
	    while(tempNode != null){
			if (tempNode.getElement() == element){
		    	return tempNode.getElement();
			}
			//System.out.println(tempNode.getElement());
			//Used for debugging.
			tempNode = tempNode.getNext();
	    }
	}
	return null;
    }

	//Delete based on a known object, returns true if succesfull, false if element couldn't be found.
    public boolean deleteElement(T element){
	if (this.first != null){
	    Node<T> tempNode = this.first;
		Node<T> lastNode = null;

		if (tempNode.getElement() == element) {
			if (tempNode.getNext() != null) { 
				//This is not the only element in the list
				this.first = tempNode.getNext();
				--this.length;
				tempNode = null;
				return true;
			}
			//This is the only element in the list
			this.first = null;
			tempNode = null;
			--this.length;
			return true;
		}
		if (tempNode.getNext() != null) {
			lastNode = tempNode;
			tempNode = lastNode.getNext();
			while ( tempNode != null ){
				if ( tempNode.getElement() == element ){
					if (tempNode.getNext() == null) {
						lastNode.setNext(null);
						this.last = lastNode;
					}
		    		lastNode.setNext(tempNode.getNext()); 
		    		--this.length;
		    		return true;
				}
				lastNode = tempNode;
				tempNode = tempNode.getNext();
	    	}
		}
	    
	}
	return false;
    }

	//Delete based on position in the list.
	public boolean deleteNode(int node) {
		return false;
	}
    
	//Internal Node structure, a node is simply a part of the list, containing the actual elements.
    protected class Node<T> {
	private T element;
	private Node<T> next;
	
		public Node(T nodeElement){
	    	this.element = nodeElement;
	    	this.next = null;
		}
	
		public Node<T> getNext(){
	    	return this.next;
		}

		public void setNext(Node<T> nextNode){
			if (nextNode != null) {
				this.next = nextNode;
				return;
			}
			this.next = null;
		}

		public T getElement(){
			return this.element;
		}
    }
}
