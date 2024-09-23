/*
File: MyLibrary.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: Implements the main() method for the library collection, so this is the
file that will be run in order to actually run the library collection program as
a whole. More specifically, this code will handle the user-facing front-end of the
code, using the previously-constructed classes to manage user interactions.
 */

public class MyLibrary {
    public static void main(String[] args) {
        // beginning
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
            }
        }
        
        // addBook
        if (commandType.equals("addbook")) {
        	// ask the user for appropriate information about the book
        	// that should be added
        	// add the book to the collection
        }

        // setToRead
        if (commandType.equals("settoread")) {
        	// ask user for book they want to update
        }

        // rate
        if (commandType.equals("rate")) {
        	// ask the user what book they want to rate
        	// ask for the rating
        }
        
        // getBooks
        if (commandType.equals("getbooks")) {
        	// give user options and retrieve and display lists
        }

        // suggestRead
        if (commandType.equals("suggestread")) {
        	// retrieve a random unread book from the library
        }

        // addBooks
        if (commandType.equals("addbooks")) {
        	// ask for file name
        	//
        }
    }
}
