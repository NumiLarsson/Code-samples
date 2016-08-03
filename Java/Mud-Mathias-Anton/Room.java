/**
 * @file Room.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Class for a room in the mud
 */
import java.util.*;

public class Room{
    private String name;
    private Creature[] studentList;
    private Teacher teacher;
    private ArrayList<Item> itemList;
    private boolean[] open;
    private String[] doors;
    private HashMap<String, Boolean> subRooms;
    private Sfinxen ender;

    /**
     * Constructor for the room with a sfinx
     * @param roomInfo Room details
     */
    public Room(String roomInfo) {
	this.subRooms = new HashMap<String, Boolean>();
	extractInfo(roomInfo);
	this.studentList = null;
	this.teacher = null;
	this.itemList = new ArrayList<Item>();
	String[] wisdom = {"Ketchup does not go with popcorn", "There is no aliens on the earth?"};
	this.ender = new Sfinxen(wisdom, 180);
    }
    
    /**
     * Constructor for a room without sfinx
     * @param roomInfo Room details
     * @param studentList Students in room
     * @param teacher Teacher in room
     * @param itemList Items in room
     */
    public Room(String roomInfo, Creature[] studentList, Teacher teacher, Item[] itemList){
	this.subRooms = new HashMap<String, Boolean>();
	extractInfo(roomInfo);
	this.studentList = studentList;
	this.teacher = teacher;
	this.itemList = new ArrayList<Item>();
	for (Item i: itemList) {
	    this.itemList.add(i);
	}
	this.ender = null;
    }

    /**
     * Get room name
     * @return Name
     */
    public String getName(){
	return this.name;
    }
    private void extractInfo(String d){
	char searchValue = ';';
	int index = d.indexOf(searchValue);
	this.name = d.substring(0, index);
	int startIndex = index+2;

	open = new boolean[4];
	doors = new String[4];
	for (int i=0; i < 8; ++i){
	    index = d.indexOf(searchValue, startIndex);
	    if (i < 4){
		doors[i] = d.substring(startIndex, index);
	    } else {
		boolean openDoor = true;
		String o = d.substring(startIndex, index);
		if (o.equals("X") || o.equals("False")){
		    openDoor = false;
		}
		open[i - 4] = openDoor;
	    }
	    startIndex = index+2;
	}
    }

    /**
     * List all creatures and items
     */
    public void listCreaturesAndItems(){
	System.out.println("\n\tCreatures in room:\n");
	if (this.ender == null) {
	    System.out.println("   Teacher:");
	    if (teacher != null) {
		System.out.println("\t"+this.teacher);
	    }
	    System.out.println("   Student:");
	    for (Creature s: studentList) {
		System.out.println("\t"+s);
	    }
	} else {
	    System.out.println("   The almighty Sfinxen is here!");
	}
	if (itemList != null) {
	    System.out.println("\n\tItems in room:\n");
	    for (Item i: itemList) {
		if (i != null) {
		    System.out.println("   " + i);
		}
	    }   
	}
    }

    /**
     *
     * @param i
     */
    public void addItem(Item i) {
	itemList.add(i);
    }

    /**
     *
     * @param name
     * @return
     */
    public Item getItem(String name) {
	if (this.itemList != null) {
	    Iterator<Item> itr = this.itemList.iterator();
	    Item tempItem = null;
	
	    while ( itr.hasNext() ){
		tempItem = itr.next();
		if (tempItem.getName().toLowerCase().equals(name.toLowerCase())) {
		    itr.remove();
		    return tempItem;
		}
	    }
	}
	return null;
    }

    /**
     *
     * @param st
     * @return
     */
    public String move(String st) {
	int r = moveHelper(st);
	String room = "";
	if (!open[r]) {
	    room = null;
	} else if (!doors[r].equals("X")) {
	    room = doors[r];
	}
	return room;
    }

    /**
     *
     * @param st
     * @return
     */
    public String getAdRoom(String st) {
	int r = moveHelper(st);
	return doors[r];
    }

    /**
     *
     * @param s
     * @param t
     * @return
     */
    public Creature getCreature(String s, boolean t) {
	Creature res = null;
	if (t) {
	    if (teacher != null) {
		res = teacher;
	    }
	} else {
	    if (studentList != null) {
		for (Creature c: studentList) {
		    if (c.getName().toLowerCase().equals(s)) {
			res = c;
		    }
		}
	    }
	    if (res == null && teacher != null) {
		if (teacher.getName().toLowerCase().equals(s)) {
		    res = teacher;
		}
	    }
	}

	return res;
    }

    /**
     *
     * @param st
     * @return
     */
    public int moveHelper(String st) {
	int r = -1;
	if (st.equals("north")) {
	    r = 0;
	} else if (st.equals("east")) {
	    r = 1;
	} else if (st.equals("south")) {
	    r = 2;
	} else if (st.equals("west")) {
	    r = 3;
	} else {
	    System.out.println("Wrong direction try [north, west, south or east]");
	}
	return r;
    }

    /**
     *
     * @param i
     * @return
     */
    public boolean checkDoor(int i) {
	return open[i];
    }

    /**
     *
     * @return
     */
    public boolean isSfinx() {
	if (this.ender != null) return true;
	return false;
    }

    /**
     *
     */
    public void printSfinx() {
	if (this.ender != null) {
	    this.ender.talk();
	}
    }

    /**
     *
     * @param room
     */
    public void unlockDoorToRoom(String room) {
	String uRoom = room.toLowerCase();
	String door;
	for (int i=0; i<4; ++i) {
	    door = doors[i].toLowerCase();
	    if (door.equals(uRoom)) {
		open[i] = true;
	    }
	}
    }

    public String toString() {
	String room = "\t\t\t"+doors[0];
	room += toStringHelper(0);
	room = toStringHelperVertical(room);
	room += "\t"+doors[3];
	room += toStringHelper(3);
	room = toStringHelperHorizontal(room);
	room += "\t\t\t\t\t"+doors[1];
	room += toStringHelper(1);
	room = toStringHelperVertical(room);
	room += "\t\t\t"+doors[2];
	room += toStringHelper(2);
	return room;
    }
    private String toStringHelper(int i) {
	if (doors[i].equals("X")) {
	    return " (This is a wall)\n";
	} else if (open[i]) {
	    return " (Unlocked)\n";
	} else {
	    return " (Locked)\n";
	}
    }
    private String toStringHelperVertical(String s) {
	for (int i=0; i<4; ++i) {
	    s += "\t\t\t\t|\n";
	}
	return s;
    }
    private String toStringHelperHorizontal(String s) {
	return (s += "\t\t----------------------------------\n");
    }
}
