/*
File: MyLibrary.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: Implements the main() method for the library collection, so this is the
file that will be run in order to actually run the library collection program as
a whole. More specifically, this code will handle the user-facing front-end of the
code, using the previously-constructed classes to manage user interactions. Note
that this represents the View in the Model-View-Controller design pattern.
 */

// NEED TO DO: put everything in main() in a while loop to allow repeated interaction,
//              and put all of the code for each task into private helper methods for improved modularity
// NEED TO DO: replace the model currently being used with a controller
// NEED TO DO: documentation for helper methods, once written

import java.util.Scanner;

/*
When it comes to maintaining encapsulation, considering that this class posesses no
instance variables (since it only functions as the view, and is the file that is 
run to run the overall program), there aren't any opportunities for this class 
itself to produce any escaping references for internal data. However, this class
plays a key role in maintaining the encapsulation of the other classes, since it
uses input validation to ensure that only valid information is being fed into the
methods of the controller. In this way, this class helps to prevent the underlying
objects and data structures from being corrupted by improper inputs, thus further
supporting the overall encapsulation of different elements of the program.
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
        System.out.print("\n");

        // First, a Scanner object representing the keyboard is constructed
        Scanner keyboard = new Scanner(System.in);

        // Determine the command type
        String commandType = "";
        while (!(commandType.equals("search") || commandType.equals("addbook") || commandType.equals("settoread")
                || commandType.equals("rate") || commandType.equals("getbooks") || commandType.equals("suggestread")
                || commandType.equals("addbooks"))) {
            System.out.println("Please enter one of the above: ");
            commandType = keyboard.nextLine().toLowerCase();
        }

        // search
        if (commandType.equals("search")) {
            String searchType = "";
            while (!(searchType.equals("title") || searchType.equals("author") || searchType.equals("book"))) {
                System.out.print("Enter search type (title, author, or book): ");
                searchType = keyboard.nextLine().toLowerCase();
            }
            // Converts to an enumerated type for greater code clarity
            TypeSort enumSearchType;
            if (searchType.equals("title")) {
                enumSearchType = TypeSort.TITLE;
            } else if (searchType.equals("author")) {
                enumSearchType = TypeSort.AUTHOR;
            } else {
                enumSearchType = TypeSort.RATING;
            }

            controller.cGetSortedCollection(enumSearchType);
        }

        // addBook
        if (commandType.equals("addbook")) {
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
            }
        }

        // setToRead
        if (commandType.equals("settoread")) {
            // ask user for book they want to update
            System.out.println("Enter your book title: ");
            String newTitle = keyboard.nextLine();

            System.out.println("Enter your book author: ");
            String newAuthor = keyboard.nextLine();

            controller.cSetToRead(newTitle, newAuthor);
        }

        // rate
        if (commandType.equals("rate")) {
            // ask the user what book they want to rate
            // ask for the rating
            System.out.println("Enter your book title: ");
            String newTitle = keyboard.nextLine();

            System.out.println("Enter your book author: ");
            String newAuthor = keyboard.nextLine();

            System.out.println("Enter your book rating (1-5): ");
            int rating = keyboard.nextInt();

            controller.cUpdateBookRating(newTitle, newAuthor, rating);
        }

        // getBooks
        if (commandType.equals("getbooks")) {
            // give user options and retrieve and display lists
            System.out.println("AUTHOR: All books by sorted by author");
            System.out.println("TITLE: All books by sorted by title");
            System.out.println("READ: All books that you have read");
            System.out.println("UNREAD: All books that you have not read");
            String getType = "";
            while (!(getType.equals("title") || getType.equals("author") || getType.equals("read") ||
                    getType.equals("unread"))) {
                System.out.print("Please enter one of the above options: ");
                getType = keyboard.nextLine().toLowerCase();
            }
            if (getType.equals("author")) {
                System.out.print("Please enter the author: ");
                String author = keyboard.nextLine();
                controller.cGetBooksWithAuthor(author);
            } else if (getType.equals("title")) {
                System.out.print("Please enter the title: ");
                String title = keyboard.nextLine();
                controller.cGetBooksWithTitle(title);
            } else if (getType.equals("read")) {
                controller.cAllReadBooks();
            } else if (getType.equals("unread")) {
                controller.cAllUnreadBooks();
            }
        }

        // suggestRead
        if (commandType.equals("suggestread")) {
            // retrieve a random unread book from the library
            controller.cGetRandomBook();
        }

        // addBooks
        if (commandType.equals("addbooks")) {
            // ask for file name
            System.out.println("Enter the book file name: ");
            String fileName = keyboard.nextLine();
            controller.cAddBooksFromFile(fileName);
        }

        // Closes the Scanner object for the keyboard
        keyboard.close();
    }
}
