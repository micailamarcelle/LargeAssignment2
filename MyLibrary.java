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
        System.out.println("STOP: finish using your library");
        System.out.print("\n");

        // First, a Scanner object representing the keyboard is constructed
        Scanner keyboard = new Scanner(System.in);

        boolean endUse = false;
        
        while (!endUse) {
            // Determine the command type
            String commandType = "";
            while (!(commandType.equals("search") || commandType.equals("addbook") || commandType.equals("settoread")
                    || commandType.equals("rate") || commandType.equals("getbooks") || commandType.equals("suggestread")
                    || commandType.equals("addbooks") || commandType.equals("stop"))) {
                if (commandType.equals("stop")) {
                    endUse = true;
                }
                System.out.println("Please enter one of the above: ");
                commandType = keyboard.nextLine().toLowerCase();
            }

            // search
            if (commandType.equals("search")) {
                searchHelper();
            }
    
            // addBook
            if (commandType.equals("addbook")) {
                addBookHelper();
            }
    
            // setToRead
            if (commandType.equals("settoread")) {
                setToReadHelp();
            }
    
            // rate
            if (commandType.equals("rate")) {
                rateHelper();
            }
    
            // getBooks
            if (commandType.equals("getbooks")) {
                getBooksHelper();
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

        private void searchHelper() {
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
    
                ourList = controller.cGetSortedCollection(enumSearchType);
                if (ourList.length() == 0) {
                    System.out.println("No books match your search :(");
                } else {
                    for (int i ==0; i < ourList.length(); i++) {
                        System.out.println(ourList.get(i));
                    }
                }
        }

        private void addBookHelper() {
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

        private void setToReadHelp() {
                // ask user for book they want to update
                System.out.println("Enter your book title: ");
                String newTitle = keyboard.nextLine();
    
                System.out.println("Enter your book author: ");
                String newAuthor = keyboard.nextLine();

                while (!(controller.cAlreadyInCollection(newTitle, newAuthor))) {
                    System.out.println("Sorry, that book is not in your collection.")
                    System.out.println("Enter your book title: ");
                    newTitle = keyboard.nextLine();
    
                    System.out.println("Enter your book author: ");
                    newAuthor = keyboard.nextLine();   
                }
                controller.cSetToRead(newTitle, newAuthor);
        }

        private void rateHelper() {
                // ask the user what book they want to rate
                // ask for the rating
                System.out.println("Enter your book title: ");
                String newTitle = keyboard.nextLine();
    
                System.out.println("Enter your book author: ");
                String newAuthor = keyboard.nextLine();

                while (!(controller.cAlreadyInCollection(newTitle, newAuthor))) {
                    System.out.println("Sorry, that book is not in your collection.")
                    System.out.println("Enter your book title: ");
                    newTitle = keyboard.nextLine();
    
                    System.out.println("Enter your book author: ");
                    newAuthor = keyboard.nextLine();   
                }
    
                System.out.println("Enter your book rating (1-5): ");
                int rating = keyboard.nextInt();
                while (!(rating >= 1 || rating <= 5)) {
                    System.out.println("Sorry, that's not between 1 and 5. Enter your book rating (1-5): ");
                    rating = keyboard.nextInt();
                }
    
                controller.cUpdateBookRating(newTitle, newAuthor, rating);
        }

        private void getBooksHelper() {
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
        
    }
    
}
