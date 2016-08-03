public class Queue<T> {

    private Node<T> first;
    private Node<T> last;
    private int lenght;

    public Queue(Node<T> first, Node<T> last) {
	this.lenght = 0;
	this.first = first;
	this.last = last;
    }

    public int lenght() {
	return this.lenght;
    }
    public void enqueue(T c) {
	Node<T> newC = new Node<T>(c, null);
	if (lenght == 0) {
	    this.first = newC;
	    this.last = newC;
	} else {
	    if (this.last == null) {
		this.first.addToNext(newC);
	    } else {
		this.last.addToNext(newC);
	    }
	    this.last = newC;
	}
	++this.lenght;
    }
    public T dequeue() throws EmptyQueueException {
	T temp;
	if (this.lenght >= 0) {
	    --this.lenght;
	    temp = this.first.getElemnt();
	    this.first = this.first.removeFirst();
	} else {
	    throw new EmptyQueueException();
	}
	return temp;
    }
    public T first() {
	if (this.first == null) {
	    return null;
	} else {
	    return this.first.getElemnt();   
	}
    }

    public class Node<E> {
	private E element;
	private Node<E> next;
	
	public Node(E element, Node<E> next) {
	    this.element = element;
	    this.next = next;
	}

	public E getElemnt() {
	    return this.element;
	}
	public void addToNext(Node<E> next) {
	    this.next = next;
	}
	public Node<E> removeFirst() {
	    return this.next;
	}
    }   
    public static class EmptyQueueException extends RuntimeException{}
}

