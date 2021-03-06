/**
 * @file world.java
 * @authors: Anton Larsson & Mathias Palm
 * @date: 15 december 2015
 * @brief Creates the game board and whole world! 
 * Contains game variables to change gameplay
 */
import java.io.*;
import java.util.*;

public class World {
    private static final int NUM_OF_STUDENTS = 14;
    private static final int NUM_OF_TEACHERS = 7;
    private static final int NUM_OF_KEYS = 20;

    private static final int START_ROOM = 1;
    private static final int ROOM_FOR_BOSS = 8;

    private ArrayList<Course> courses;
    private ArrayList<Book> books;
    private Teacher[] teachers;
    private Student[] students;
    private ArrayList<Key> keys;
    private ArrayList<Room> rooms;
    private Room currentRoom;

    
    /**
     * Constructor to creat a game world
     */    
    public World(){
	this.courses = new ArrayList<Course>();
	this.books = new ArrayList<Book>();
	this.rooms = new ArrayList<Room>();
	this.teachers = new Teacher[NUM_OF_TEACHERS];
	this.students = new Student[NUM_OF_STUDENTS];
	this.keys = new ArrayList<Key>();

	
	
	ArrayList<String> map = loadFile("world.txt");
	ArrayList<String> courseInfo = loadFile("course.txt");
	ArrayList<String> bookInfo = loadFile("book.txt");

	if (map != null && courseInfo != null && bookInfo != null) {
	    for (String s : bookInfo){
		Book b = new Book(s);
		this.books.add(b);
	    }
	    
	    for (int k = 0; k < NUM_OF_KEYS; ++k) {
		Key key = new Key("Key "+k);
		this.keys.add(key);
	    }
	    
	    Random rand = new Random();
	    int p = 0;
	    for (String s : courseInfo){
		int answer = rand.nextInt(2);
		String[] an = {"Black", "White", "RGB"};
		String q = "Avrage color of two zebras?";
		Course c = new Course(s,books.get(p), q, an, answer+1);
		++p;
		courses.add(c);
	    }
	    loadTeachers();
	    loadStudents();
	    loadRooms(map);
	    this.currentRoom = rooms.get(START_ROOM);
	}
    }
    /**
     * Private method to load teachers to world
     */    
    private void loadTeachers() {
	Random rand = new Random();
	int numOfCourse = courses.size();
	Course temp;
	for (int i=0; i < NUM_OF_TEACHERS; ++i) {
	    if (i < numOfCourse) {
		temp = this.courses.get(i);
	    } else {
		int r = rand.nextInt(numOfCourse);
		temp = this.courses.get(r);
	    }
	    Teacher t = new Teacher("Teacher "+i, temp);
	    this.teachers[i] = t;
	}
    }
    /**
     * Private method to load students to world
     */      
    private void loadStudents() {
	Random rand = new Random();
	int numOfCourse = courses.size();
	int bookInfoSize = books.size();
	for (int y=0; y < NUM_OF_STUDENTS; ++y) {
	    int ranBook = rand.nextInt(bookInfoSize);
	    int ranCourse = rand.nextInt(numOfCourse);
	    int ranComp = rand.nextInt(numOfCourse);
	    Student s = new Student("Student "+y, courses.get(ranCourse), courses.get(ranComp));
	    this.students[y] = s;
	}
    }
    /**
     * Private method to load rooms to world
     */      
    private void loadRooms(ArrayList<String> map) {
	Random rand = new Random();
	int sfix = 0;
	Room r;
	for (String s: map) {
	    if (sfix == ROOM_FOR_BOSS) {
		r = new Room(s);
		++sfix;
	    } else {
		int ranTeachers = rand.nextInt(teachers.length);
		int ranStudents = rand.nextInt(5);
		Creature[] creatureList = new Creature[ranStudents];
		for (int x=0; x < ranStudents; ++x) {
		    int stud = rand.nextInt(students.length);
		    creatureList[x] = students[stud];
		}
		Teacher teche = null;
		int randomGenerate = rand.nextInt(4);
		if (randomGenerate < 2) {
		    teche = teachers[ranTeachers];
		}
		int ranKey, ranBook, rn;
		int numOfItems = rand.nextInt(4);
		Item[] itemList = new Item[numOfItems];
		for(int i=0; i < numOfItems; ++i) {
		    rn = rand.nextInt(2);
		    if (keys.size()-1 > 0) {
			ranKey = rand.nextInt(keys.size());
			if (rn == 0) {
			    itemList[i] = keys.get(ranKey);
			    keys.remove(ranKey);
			} 
		    }
		    if (books.size()-1 > 0) {
			ranBook = rand.nextInt(books.size());
			if (rn != 0) {
			    itemList[i] = books.get(ranBook);
			    books.remove(ranBook);
			}
		    }
		   
		}
	    r = new Room(s, creatureList, teche, itemList);
	    ++sfix;
	}
	    this.rooms.add(r);
	}
    }
    /**
     * Load a mud file from root folder directory
     * @param fileName File name
     * @return Arraylist with each row of file to an elemnt of the array
     */      
    public ArrayList<String> loadFile(String fileName) {
    	System.out.println("Loading File: " + fileName);
	String str;
	ArrayList<String> outputString = new ArrayList<String>();
	try{
	    Thread.sleep(500);
	    BufferedReader in = new BufferedReader(new FileReader(fileName));
	    while( (str = in.readLine()) != null ){
		outputString.add(str);
	    }
	    in.close();
	    System.out.println("loaded succsefully!");
		    
	} catch (Exception e){
	    System.out.println("Error: " + e.getMessage());
	    return null;
	} 
	return outputString;
    }
    /**
     * Return the first 6 courses
     * @return list of courses
     */  
    public Course[] returnFirst6 (){
	Iterator<Course> itr = this.courses.iterator();
	Course[] first6 = new Course[6];
	
	for (int i = 0; i < 6; i++){
	    if ( itr.hasNext() ){
		first6[i] = itr.next();
	    }
	}
	return first6;
    }
    /**
     * List all creatures in current room user is in
     */      
    public void listRoomCreaturesAndItems() {
	this.currentRoom.listCreaturesAndItems();
    }
    /**
     * Returns the current room user is in
     * @return Current room
     */      
    public Room getCurrentRoom() {
	return this.currentRoom;
    }
    /**
     * Returns item of the given name null if dont exists
     * @param itemName Name of item
     * @return Item if exists else null
     */      
    public Item getItemFromRoom(String itemName) {
	return this.currentRoom.getItem(itemName);
    }
    /**
     * Add item to the current room
     * @param i item to add
     */      
    public void addItemToRoom(Item i) {
	this.currentRoom.addItem(i);
    }
    /**
     * Get creature from room with boolean flag if want teacher
     * @param s name of creature
     * @param teacher true if want teacher else false
     * @return creature if exists else null
     */      
    public Creature getCreatureInRoom(String s, boolean teacher) {
	return this.currentRoom.getCreature(s, teacher);
    }
    /**
     * Check if Sfinx is in room
     * @return true if exists else false
     */      
    public boolean inSfinxRoom() {
	return this.currentRoom.isSfinx();
    }
    /**
     * Method to move into give direction
     * @param st direction to move
     */      
    public void moveToRoom(String st) {
	String newRoom = this.currentRoom.move(st);
	if (newRoom == null) {
	    System.out.println("Room is locked! Use key to enter.");
	} else {
	    moveIntoRoom(newRoom);
	}
    }
    /**
     * Print sfinx wise words
     */        
    public void printSfinxWord() {
	this.currentRoom.printSfinx();
    }
    /**
     * List all availble rooms in moving direction
     */        
    public void listAdjacentRooms() {
	System.out.println(this.currentRoom);
    }
    /**
     * Check if a door to give direction is locked
     * @param st Direction to check
     * @return true if locked else false
     */        
    public boolean isDoorLocked(String st) {
	int door = this.currentRoom.moveHelper(st);
	if (door != -1) {
	    return this.currentRoom.checkDoor(door);
	}
	return false;
    }
    /**
     * Use key to open a locked door and moves into room, 
     * does nothing if a wall
     * @param st Direction to open
     * @return bolean if succseded
     */        
    public boolean useKeyToRoom(String st) {
	String newRoom = this.currentRoom.getAdRoom(st);
	if (!newRoom.equals("X")) {
	    Room r = getRoomForString(newRoom);
	    if (r != null) {
		System.out.println("Room is locked! Using key...Room Unlocked");
		this.currentRoom.unlockDoorToRoom(newRoom);
		r.unlockDoorToRoom(this.currentRoom.getName());
		moveIntoRoom(newRoom);
		return true;
	    }
	} else if (newRoom.equals("X")) {
	    System.out.println("Your trying to open a wall! You dont got the tools for that!");
	    return false;
	}
	return false;
    }
    /**
     * private method to move into given room
     * @param newRoom room to move into
     */        
    private void moveIntoRoom(Room newRoom) {
	System.out.println("Moving into " + newRoom);
	this.currentRoom = newRoom;
    }
    /**
     * overloader to move into room given room name
     * @param newRoom name of room to move into
     */        
    private void moveIntoRoom(String newRoom) {
	System.out.println("Moving into " + newRoom);
	Room r = getRoomForString(newRoom);
	if (r != null) {
	    this.currentRoom = r;
	}
    }
    /**
     * Get room from name
     * @param room name of room
     * @return room if exists else null
     */        
    private Room getRoomForString(String room) {
	Room ro = null;
	String check, moveToRoom;
	for (Room r: rooms) {
	    check = r.getName().toLowerCase();
	    moveToRoom = room.toLowerCase();
	    if (check.equals(moveToRoom)) {
		ro = r; 
	    }
	}
	return ro;
    }
    
}
