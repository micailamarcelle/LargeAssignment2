/*
File: MyLibrary.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: Implements the main() method for the library collection, so this is the
file that will be run in order to actually run the library collection program as
a whole. More specifically, this code will handle the user-facing front-end of the
code, using the rest of the classes in the program to manage user interactions. Note
that this represents the View in the Model-View-Controller design pattern.
 */

import java.util.ArrayList;
import java.util.Scanner;

/*
When it comes to maintaining encapsulation, considering that this class posesses no
instance variables (since it only functions as the view, and is the file that is 
run to run the overall program), there aren't any opportunities for this class 
itself to produce any escaping references for its own internal data. However, this class
plays a key role in maintaining the encapsulation of the other classes, since it
uses input validation to ensure that only valid information is being fed into the
methods of the controller (and model). In this way, this class helps to prevent the underlying
objects and data structures from being corrupted by improper inputs, thus further
supporting the overall encapsulation of different elements of the program. Also, note
that all helper methods of this class are declared as private, which helps to further
abstract away and encapsulate the underlying implementation of the library collection,
since it prevents these methods from being accessed outside of the class.
 */

public class MyLibrary {
    public static void main(String[] args) {
        LibraryCollectionController controller = new LibraryCollectionController();
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
        System.out.println("STOP: finish using your library");
        System.out.print("\n");

        // First, a Scanner object representing the keyboard is constructed
        Scanner keyboard = new Scanner(System.in);

        // A MyLibrary object is also created to access the methods of this class
        MyLibrary myLibrary = new MyLibrary();

        boolean endUse = false;

        while (!endUse) {
            // Determine the command type
            String commandType = "";
            while (!(commandType.equals("search") || commandType.equals("addbook") || commandType.equals("settoread")
                    || commandType.equals("rate") || commandType.equals("getbooks") || commandType.equals("suggestread")
                    || commandType.equals("addbooks"))) {
                if (commandType.equals("stop")) {
                    endUse = true;
                    break;
                }
                // continually ask for commands
                System.out.println();
                System.out.println("Please enter one of the library commands: ");
                commandType = keyboard.nextLine().toLowerCase();
            }

            // search
            if (commandType.equals("search")) {
                myLibrary.searchHelper(keyboard, controller);
            }

            // addBook
            if (commandType.equals("addbook")) {
                myLibrary.addBookHelper(keyboard, controller);
            }

            // setToRead
            if (commandType.equals("settoread")) {
                if (controller.cIsEmpty()) {
                    System.out.println("There are no books in your library, therefore you can not use this command.\n");
                } else {
                    myLibrary.setToReadHelp(keyboard, controller);
                }
            }

            // rate
            if (commandType.equals("rate")) {
                if (controller.cIsEmpty()) {
                    System.out.println("There are no books in your library, therefore you can not use this command.\n");
                } else {
                    myLibrary.rateHelper(keyboard, controller);
                }
            }

            // getBooks
            if (commandType.equals("getbooks")) {
                myLibrary.getBooksHelper(keyboard, controller);
            }

            // suggestRead
            // Note that if there are no books in the library, or if there are no unread books in the library,
            // then an error message is printed, and no unread book is suggested. 
            if (commandType.equals("suggestread")) {
                if (controller.cIsEmpty()) {
                    System.out.println("There are no books in your library, therefore you can not use this command.\n");
                } else {
                // retrieve a random unread book from the library and print
                    Book randBook = controller.cGetRandomBook();
                    if (randBook == null) {
                        System.out.println("No unread books are present in your library to suggest.");
                    } else {
                        System.out.println(randBook);
                    }
                }
            }

            // addBooks
            // Note that if the given file name is not valid, then an error message is printed to
            // the terminal, and no books are added to the collection. It is also assumed that if the
            // given file name is valid, then it has the specified format.
            if (commandType.equals("addbooks")) {
                // ask for file name and call addBooksFromFile function
                System.out.println("Enter the book file name: ");
                String fileName = keyboard.nextLine();
                controller.cAddBooksFromFile(fileName);
            }

        }

        // Closes the Scanner object for the keyboard
        keyboard.close();
    }

    /*
    Private helper method, which implements the "getBooks" functionality of the library collection.
    This allows the user to choose a method for sorting (title, author, read, unread), and then
    gets either all books in the library sorted by title/author, or all read/unread books, which will 
    be sorted by title. This method does not return anything, and takes in a Scanner and 
    LibraryCollectionController as inputs in order to perform the desired functionality. The books
    that are obtained are printed to the terminal.

    @pre keyboard != null && controller != null
     */
    private void getBooksHelper(Scanner keyboard, LibraryCollectionController controller) {
        String searchType = "";
        System.out.println("Retrieve a list of all books sorted by title or author, ");
        System.out.println("or a list of all read or unread books.");
        while (!(searchType.equals("title") || searchType.equals("author") || searchType.equals("rating") || searchType.equals("read") || searchType.equals("unread"))) {
            System.out.print("Enter one of 'title', 'author', 'read', or 'unread': ");
            searchType = keyboard.nextLine().toLowerCase();
        }
        // Converts to an enumerated type for greater code clarity
        TypeSort enumSearchType;
        ArrayList<Book> ourList;
        if (searchType.equals("title")) {
            enumSearchType = TypeSort.TITLE;
            ourList = controller.cGetSortedCollection(enumSearchType);
        } else if (searchType.equals("author")) {
            enumSearchType = TypeSort.AUTHOR;
            ourList = controller.cGetSortedCollection(enumSearchType);
        } else {
            if (searchType.equals("read")) {
                ourList = controller.cAllReadBooks();
            } else {
                ourList = controller.cAllUnreadBooks();
            }
        }

        // retrieves list of books based on search type and prints books that match 
        // with the search
        if (ourList.size() == 0) {
            System.out.println("No books available to get for this task :(");
        } else {
            for (int i = 0; i < ourList.size(); i++) {
                System.out.println(ourList.get(i));
            }
        }
    }

    /*
    Private helper method which implements the "addBook" functionality of the library 
    collection. This method asks the user for the title and author of the book that they
    want to add to the collection, then adds the corresponding book to the collection, assuming
    that it does not already exist in the collection. This method does not return anything, and
    takes in a Scanner and LibraryCollectionController as inputs in order to perform the desired
    functionality. Note that this method supports the idea that all books in the library collection
    should be unique!

    @pre keyboard != null && controller != null
     */
    private void addBookHelper(Scanner keyboard, LibraryCollectionController controller) {
        // ask the user for appropriate information about the book
        // that should be added
        System.out.println("Enter your book title: ");
        String newTitle = keyboard.nextLine();

        System.out.println("Enter your book author: ");
        String newAuthor = keyboard.nextLine();

        // add the book to the collection
        // check that it is not in collection first
        if (!(controller.cAlreadyInCollection(newTitle, newAuthor))) {
            controller.cAddBook(newTitle, newAuthor);
        } else {
            System.out.println("This book is already in your library!");
        }
    }

    /*
    Private helper method, which implements the "setToRead" functionality of the library collection.
    This allows the user to search for a book in their library, and set that books status to "read". 
    This method does not return anything, and takes in a Scanner and LibraryCollectionController as 
    inputs in order to perform the desired functionality. Note that if the book has already been 
    read, then its read status is unchanged, and that if the book is not already in the collection,
    an error message is printed. 

    @pre keyboard != null && controller != null
     */
    private void setToReadHelp(Scanner keyboard, LibraryCollectionController controller) {
        // ask user for book they want to update
        System.out.println("Enter your book title: ");
        String newTitle = keyboard.nextLine();
    
        System.out.println("Enter your book author: ");
        String newAuthor = keyboard.nextLine();

        if (!(controller.cAlreadyInCollection(newTitle, newAuthor))) {
            System.out.println("Sorry, that book is not in your collection.");
            return;
        }
        controller.cSetToRead(newTitle, newAuthor);
    }

    /*
    Private helper method, which implements the "rate" functionality of the library collection.
    This allows the user to input a book based on title and author, asks them for a number 1-5,
    that they would like to use as a rating got the book. This method confirms that the books is already
    in the collection before rating and ensures that the rating is in the range 1-5. This method does not return 
    anything, and takes in a Scanner and LibraryCollectionController as inputs in order to perform the desired
    functionality. Note that if the given book is not already in the library, then an error message
    is printed, and nothing else is done. If the rating is not in the valid range, then the method
    continues asking until it gets a rating that is in the valid range. 

    @pre keyboard != null && controller != null
     */
    private void rateHelper(Scanner keyboard, LibraryCollectionController controller) {
        // ask the user what book they want to rate
        // ask for the rating
        System.out.println("Enter your book title: ");
        String newTitle = keyboard.nextLine();
    
        System.out.println("Enter your book author: ");
        String newAuthor = keyboard.nextLine();

        if (!(controller.cAlreadyInCollection(newTitle, newAuthor))) {
            System.out.println("Sorry, that book is not in your collection.");
            return;
        }

        // validate rating
        System.out.println("Enter your book rating (1-5): ");
        int rating;
        try {
            rating = keyboard.nextInt();
        } catch (Exception e) {
            keyboard.nextLine();
            rating = -1;
        }
        while (!(rating >= 1 && rating <= 5)) {
            System.out.println("Sorry, that's not between 1 and 5. Enter your book rating (1-5): ");
            try {
                rating = keyboard.nextInt();
            } catch (Exception e) {
                keyboard.nextLine();
                rating = -1;
            }
        }
    
        keyboard.nextLine();
        controller.cUpdateBookRating(newTitle, newAuthor, rating);
    }

    /*
    Private helper method, which implements the "search" functionality of the library collection.
    This allows the user to choose a method for searching (title, author, or rating), asks them for
    the appropriate information according to the chosen method of sorting, and prints all of the
    books that match this criteria to the terminal. This method does not return anything, and
    takes in a Scanner and LibraryCollectionController as inputs in order to perform the desired
    functionality. Input validation is used to ensure that the user selects one of these options,
    and that they pass in a valid integer rating. 

    @pre keyboard != null && controller != null
     */
    private void searchHelper(Scanner keyboard, LibraryCollectionController controller) {
        // give user options and retrieve and display lists
        System.out.println("AUTHOR: Find books by a particular author");
        System.out.println("TITLE: Find books with a particular title");
        System.out.println("RATING: Find books with a particular rating");
        
        // get search type
        String getType = "";
        while (!(getType.equals("title") || getType.equals("author") || getType.equals("rating"))) {
            System.out.print("Please enter one of the above options: ");
            getType = keyboard.nextLine().toLowerCase();
        }

        // get book list based on search type
        ArrayList<Book> ourBooks;
        if (getType.equals("author")) {
            System.out.print("Please enter the author: ");
            String author = keyboard.nextLine();
            ourBooks = controller.cGetBooksWithAuthor(author);
        } else if (getType.equals("title")) {
            System.out.print("Please enter the title: ");
            String title = keyboard.nextLine();
            ourBooks = controller.cGetBooksWithTitle(title);
        } else if (getType.equals("rating")) { 

            System.out.print("Please enter the rating (1-5): ");
            int rating;
            try {
                rating = keyboard.nextInt();
            } catch (Exception e) {
                keyboard.nextLine();
                rating = -1;
            }
            while(! (rating >= 1 && rating <= 5)) {
                System.out.print("Please enter the rating (1-5): ");
                try {
                    rating = keyboard.nextInt();
                } catch (Exception e) {
                    keyboard.nextLine();
                    rating = -1;
                }
            }
            keyboard.nextLine();
            ourBooks = controller.cGetBooksWithRating(rating);
        } else {
            System.out.println("Error: Instruction is not one of the valid options");
            return;
        }

        // print books retrieved
        if (ourBooks.size() == 0) {
            System.out.println("No items match your search.");
            return;
        } else {
            for (int i = 0; i < ourBooks.size(); i++) {
                System.out.println(ourBooks.get(i));
            }
        }
    }

}
