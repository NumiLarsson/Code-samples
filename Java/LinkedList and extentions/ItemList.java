public class ItemList extends LinkedList<Item>{
    private int maxSize;
    private int currentSize;

    public ItemList(int maxSize) {
		super.length = 0;
		super.first = null;
		super.last = null;
		this.maxSize = maxSize;
		this.currentSize = 0;
    }

	//Get item based on item name.
    public Item getItem (String name){
		Node<Item> tempItem = super.first;
		while (tempItem != null){
			if (tempItem.getNext() == super.last) {
			if (tempItem.getElement().getName() == name){
				return tempItem.getElement();
			}
			else tempItem = tempItem.getNext();
			}
		}
		return null;
    }

	//Delete item based on item name.
    public boolean deleteItem (String name){
		Node<Item> tempItem = super.first;
		while (tempItem != null){
			if (tempItem.getElement().getName() == name){
			tempItem = tempItem.getNext();
			return true;
			}
			
		}
		return false;
    }

    public void print(){
		Node<Item> tempItem = super.first;
		int depth = 0;
		while (tempItem != null){
			System.out.println("Item " + depth + ": " +
					tempItem.getElement().getName() +
					" weight: "+tempItem.getElement().getSize());
			++depth;
			tempItem = tempItem.getNext();
		}
    }

    public boolean addItem(Item item){
		if ( (item.getSize() + currentSize) <= maxSize ){
			currentSize += item.getSize();
			return super.addNode(item);
		}
		return false;
    }
}
