package main;
import java.util.ArrayList;

class Book {

	private String title;
	private String author;
	private boolean availability;
	
	public Book(String tytul, String autor, boolean dostepnosc){
		title = tytul;
		author = autor;
		availability = dostepnosc;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public boolean getAvailability() {
		return availability;
	}
	public void setAvailability(boolean available) {
		availability = available;
	}
};

class Library {
	private ArrayList<Book> books;
	private ArrayList<Member> members;
	public Library() {
		books = new ArrayList<Book>();
		members = new ArrayList<Member>();
	}
	public void addBook(String tytul, String autor, boolean dostepnosc) {
		books.add(new Book(tytul, autor, dostepnosc));
		System.out.println(String.format("'%s' by %s has been added to library.", tytul,autor));
	}
	public void addMember(Member member) {
		members.add(member);
		System.out.println(String.format("%s %s %s has become a library member.", member.getFirstName(), member.getLastName(), member.getUserID()));
	}
	public void listMembers() {
		System.out.println("Library members:");
		for(Member member : members) {
			System.out.println(String.format("- %s %s %s", member.getFirstName(), member.getLastName(), member.getUserID()));
		}
	}
	public void addBook(Book book) {
		books.add(book);
		System.out.println(String.format("'%s' by %s has been added to library.", book.getTitle(),book.getAuthor()));
	}
	public void borrowBook(Book book, Member member) {
		if(books.contains(book)) {
			if(book.getAvailability()==true) {
			book.setAvailability(false);
			System.out.println(String.format("%s borrowed the book: '%s' by %s", member.getFirstName(), book.getTitle(), book.getAuthor()));
			member.addBorrowedBook(book);
		}
			else System.out.println(String.format("Sorry %s, the book '%s' by %s is not available at the moment.", member.getFirstName(), book.getTitle(), book.getAuthor()));
	}
		else System.out.println(String.format("Sorry %s, we do not have the book '%s' by %s in our library.", member.getFirstName(), book.getTitle(), book.getAuthor()));
}
	public void returnBook(Book book, Member member) {
		if(books.contains(book)) {
			if(book.getAvailability()==false) {
			book.setAvailability(true);
			System.out.println(String.format("%s returned the book: '%s' by %s", member.getFirstName(), book.getTitle(), book.getAuthor()));
			member.removeBorrowedBook(book);
		}
			else System.out.println(String.format("Sorry %s, the book '%s' by %s cannot be returned as it was returned earlier.", member.getFirstName(), book.getTitle(), book.getAuthor()));
	}
		else System.out.println(String.format("Sorry %s, the book '%s' by %s has not been borrowed from our library.", member.getFirstName(), book.getTitle(), book.getAuthor()));
	}
	public void listAvailableBooks() {
		System.out.println("List of books in our library:");
		for(Book book : books) {
			if(book.getAvailability()==true) System.out.println(String.format("- '%s' by %s.",book.getTitle(), book.getAuthor()));
		}
	}
}
class Member{
	private String userID;
	private String firstName;
	private String lastName;
	private ArrayList<Book> ownedBooks;
	
	public Member(String id, String imie, String nazwisko) {
		userID = id;
		firstName = imie;
		lastName = nazwisko;
		ownedBooks = new ArrayList<Book>();
	}
	
	public String getUserID() {
		return userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public ArrayList<Book> getBooksList() {
		return ownedBooks;
	}
	public void addBorrowedBook(Book book) {
		ownedBooks.add(book);
	}
	public void removeBorrowedBook(Book book) {
		ownedBooks.remove(book);
	}
	public void listBorrowedBooks() {
		for(Book book : ownedBooks) {
			System.out.println(String.format("'%s' by %s.",book.getTitle(), book.getAuthor()));
		}
	}
}

public class Main {

	public static void main(String[] args) {
			Book book1 = new Book("Java Programming", "Maria Lempke", true);
			Book book2 = new Book("Data Structures", "Rose Knapp", true);
			Book book3 = new Book("Algorithms", "Michael Odolan", true);
			Book book4 = new Book("PHP", "Rob Joal", true);
			
			Member member1 = new Member("AF1", "Alice", "First");
			Member member2 = new Member("BS2", "Bob", "Second");
			
			Library library = new Library();
			library.addBook(book1);
			library.addBook(book2);
			library.addBook(book3);
			System.out.println();
			
			library.addMember(member1);
			library.addMember(member2);
			System.out.println();
			
			library.listAvailableBooks();
			System.out.println();
			
			library.listMembers();
			System.out.println();
	
			library.borrowBook(book1, member1);
			library.borrowBook(book1, member2);
			library.borrowBook(book4, member2);
			library.borrowBook(book3, member1);
			System.out.println();
			
			library.listAvailableBooks();
			System.out.println();
			
			library.returnBook(book1, member1);
			library.borrowBook(book1, member2);
			library.returnBook(book1, member2);
			library.returnBook(book1, member1);
			library.returnBook(book4, member2);
			System.out.println();
			
			library.listAvailableBooks();
	}

}
