/*
File: MyLibrary.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: Implements the main() method for the library collection, so this is the
file that will be run in order to actually run the library collection program as
a whole. More specifically, this code will handle the user-facing front-end of the
code, using the previously-constructed classes to manage user interactions.
 */

// NEED TO DO: add encapsulation description
// NEED TO DO: put everything in main() in a while loop to allow repeated interaction,
//              and put all of the code for each task into private helper methods for improved modularity
// NEED TO DO: replace the model currently being used with a controller
// NEED TO DO: documentation for helper methods, once written

import java.util.Scanner;

public class MyLibrary {
    public static void main(String[] args) {
        LibraryCollection ourLibrary = new LibraryCollection();
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

            ourLibrary.getSortedCollection(enumSearchType);
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
            if (!(ourLibrary.alreadyInCollection(newTitle, newAuthor))) {
                ourLibrary.addBook(newTitle, newAuthor);
            }
        }

        // setToRead
        if (commandType.equals("settoread")) {
            // ask user for book they want to update
            System.out.println("Enter your book title: ");
            String newTitle = keyboard.nextLine();

            System.out.println("Enter your book author: ");
            String newAuthor = keyboard.nextLine();

            ourLibrary.setToRead(newTitle, newAuthor);
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

            ourLibrary.updateBookRating(newTitle, newAuthor, rating);
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
                ourLibrary.getBooksWithAuthor(author);
            } else if (getType.equals("title")) {
                System.out.print("Please enter the title: ");
                String title = keyboard.nextLine();
                ourLibrary.getBooksWithTitle(title);
            } else if (getType.equals("read")) {
                ourLibrary.allReadBooks();
            } else if (getType.equals("unread")) {
                ourLibrary.allUnreadBooks();
            }
        }

        // suggestRead
        if (commandType.equals("suggestread")) {
            // retrieve a random unread book from the library
            ourLibrary.getRandomBook();
        }

        // addBooks
        if (commandType.equals("addbooks")) {
            // ask for file name
            System.out.println("Enter the book file name: ");
            String fileName = keyboard.nextLine();
            ourLibrary.addBooksFromFile(fileName);
        }

        // Closes the Scanner object for the keyboard
        keyboard.close();
    }
}
