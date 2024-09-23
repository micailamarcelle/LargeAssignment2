/*
File: MyLibrary.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: Implements the main() method for the library collection, so this is the
file that will be run in order to actually run the library collection program as
a whole. More specifically, this code will handle the user-facing front-end of the
code, using the previously-constructed classes to manage user interactions.
 */
import java.util.Scanner;
public class MyLibrary {
    public static void main(String[] args) {
        // Intro message for the Library UI
	System.out.println("---------------------------");
	System.out.println("- WELCOME TO YOUR LIBRARY -");
	System.out.println("---------------------------");
	System.out.print("\n");
	System.out.println("What would you like to do?");
	System.out.println("search: find a book");
	System.out.println("addBook: add a book");
	System.out.println("setToRead: update a book to read");
	System.out.println("rate: rate a book");
	System.out.println("getBooks: retrieve and display a list of books");
	System.out.println("suggestRead: choose a random book from library");
	System.out.println("addBooks: add files to your library");
	System.out.print("\n");
		
	// Determine the command type 	
	String commandType = "";
        while (!(commandType.equals("search") || commandType.equals("addbook") || commandType.equals("settoread") 
        		|| commandType.equals("rate") || commandType.equals("getbooks") || commandType.equals("suggestread")
        		|| commandType.equals("addbooks"))) {
        	System.out.println("Please enter one of the above: ");
            	commandType = new Scanner(System.in).nextLine().toLowerCase();
        }
		
        // search
        if (commandType.equals("search")) {
        	String searchType = "";
            	while (!(searchType.equals("title") || searchType.equals("author") || searchType.equals("book"))) {
                	System.out.print("Enter search type (title, author, or book): ");
                	searchType = new Scanner(System.in).nextLine().toLowerCase();
			getSortedCollection(searchType);
            }
        }
        
        // addBook
        if (commandType.equals("addbook")) {
        	// ask the user for appropriate information about the book
        	// that should be added
		System.out.println("Enter your book title: ");
		String newTitle = new Scanner(System.in).nextLine();

		System.out.println("Enter your book author: ");
		String newAuthor = new Scanner(System.in).nextLine();
        	
		// add the book to the collection
		// check that it is not in collection first
		if (!(alreadyInCollection(newTitle, newAuthor)) {	
			addBook(newTitle, newAuthor);
		}
        }

        // setToRead
        if (commandType.equals("settoread")) {
        	// ask user for book they want to update
		System.out.println("Enter your book title: ");
		String newTitle = new Scanner(System.in).nextLine();

		System.out.println("Enter your book author: ");
		String newAuthor = new Scanner(System.in).nextLine();

		setToRead(newTitle, newAuthor);
        }

        // rate
        if (commandType.equals("rate")) {
        	// ask the user what book they want to rate
        	// ask for the rating
		System.out.println("Enter your book title: ");
		String newTitle = new Scanner(System.in).nextLine();

		System.out.println("Enter your book author: ");
		String newAuthor = new Scanner(System.in).nextLine();

		System.out.println("Enter your book rating (1-5): ");
		String rating = new Scanner(System.in).nextInt();

		updateBookRating(newTitle, newAuthor, rating);
        }
        
        // getBooks
        if (commandType.equals("getbooks")) {
        	// give user options and retrieve and display lists
        }

        // suggestRead
        if (commandType.equals("suggestread")) {
        	// retrieve a random unread book from the library
		getRandomBook();
        }

        // addBooks
        if (commandType.equals("addbooks")) {
        	// ask for file name
        	System.out.println("Enter the book file name: ");
		String fileName = new Scanner(System.in).nextLine();
		addBooksFromFile(fileName);
        }
    }
}
