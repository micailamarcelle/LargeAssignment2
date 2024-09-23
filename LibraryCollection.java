/*
File: LibraryCollection.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: This class is designed to organize the Book objects that will
be stored within the library collection, and will provide methods allowing
for the sorting of books according to different characteristics, searching
for books with particular characteristics, and updating the books currently
in the collection.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

// NEED TO DO: UPDATE THE COMMENTS BEFORE THE METHODS TO BE BETTER DOCUMENTED

public class LibraryCollection {
    // Declares the sole private instance variable of the LibraryCollection class,
    // which is an ArrayList of the 
    private ArrayList<Book> bookList;

    // Public constructor for the LibraryCollection class, which creates a new (empty)
    // LibraryCollection object
    public LibraryCollection() {
        this.bookList = new ArrayList<Book>();
    }

    // Gets a sorted version of the underlying ArrayList, with an enumerated type being
    // used to determine which type of sorting to use. Note that this returns a copy of
    // the ArrayList and the books within it!
    public ArrayList<Book> getSortedCollection(TypeSort howSort) {
        // First, the Collections class is used to sort the underlying ArrayList according
        // to the given type of sorting
        if (howSort.equals(TypeSort.TITLE)) {
            Collections.sort(bookList, Book.makeComparatorTitle());
        } else if (howSort.equals(TypeSort.AUTHOR)) {
            Collections.sort(bookList, Book.makeComparatorAuthor());
        } else {
            Collections.sort(bookList, Book.makeComparatorRating());
        }

        // We then copy all of the books from the sorted ArrayList into a new ArrayList,
        // with this new ArrayList being returned
        ArrayList<Book> copy = new ArrayList<Book>();
        for (int i = 0; i < bookList.size(); i++) {
            Book curBook = bookList.get(i);
            copy.add(new Book(curBook.getTitle(), curBook.getAuthor(), curBook.getRating(), curBook.getReadStatus()));
        }
        return copy;
    }

    // Gets a set of books with a particular author (note that the books in the returned
    // ArrayList are a copy!). An empty ArrayList is returned if no books with that author
    // are found
    public ArrayList<Book> getBooksWithAuthor(String author) {
        // First, we sort the underlying ArrayList by author
        Collections.sort(bookList, Book.makeComparatorAuthor());

        // Initializes the ArrayList to be returned
        ArrayList<Book> authorList = new ArrayList<Book>();

        // We then iterate until we find a book with the given author
        int i = 0;
        while (i < bookList.size()) {
            Book curBook = bookList.get(i);
            if (curBook.getAuthor().equals(author)) {
                // If we find a book with the given author, then we add all books with this
                // author into the return list
                while (i < bookList.size() && curBook.getAuthor().equals(author)) {
                    // Adds the copy to the ArrayList to be returned
                    Book copy = new Book(curBook.getTitle(), curBook.getAuthor(), curBook.getRating(), curBook.getReadStatus());
                    authorList.add(copy);

                    // Gets the next book in the list, if such a book exists
                    i++;
                    if (i < bookList.size()) {
                        curBook = bookList.get(i);
                    }
                }
                
                // The while loop is then broken, since we need not do anything else
                break;
            }

            // Otherwise, we increment i
            i++;
        }

        // We then return the resulting list of Book objects, which will be empty if
        // no books with the specified author are present in the collection.
        return authorList;
    }

    // Gets a set of books with a particular title(not that the books in the returned 
    // ArrayList are a copy!). If there are no books in the collection with this title,
    // then an empty ArrayList is simply returned
    public ArrayList<Book> getBooksWithTitle(String title) {
        // First, we sort the underlying ArrayList by title
        Collections.sort(bookList, Book.makeComparatorTitle());

        // Initializes the ArrayList to be returned
        ArrayList<Book> titleList = new ArrayList<Book>();

        // We then iterate until we find a book with the given title
        int i = 0;
        while (i < bookList.size()) {
            Book curBook = bookList.get(i);
            if (curBook.getTitle().equals(title)) {
                // If we find a book with the given title, then we iterate through all such
                // books, adding copies of them to our ArrayList to be returned
                while (i < bookList.size() && curBook.getTitle().equals(title)) {
                    // Adds the copy to the ArrayList to be returned
                    Book copy = new Book(curBook.getTitle(), curBook.getAuthor(), curBook.getRating(), curBook.getReadStatus());
                    titleList.add(copy);

                    // Gets the next book in the collection, if such a book exists
                    i++;
                    if (i < bookList.size()) {
                        curBook = bookList.get(i);
                    }
                }

                // We then break the while loop, since we've already found all of the books we need
                break;
            }

            // Otherwise, we increment i and continue looping
            i++;
        }

        // Finally, we return the resulting ArrayList of books with the given title
        return titleList;
    }

    // Gets a set of books with a particular rating (note that the books in the returned
    // ArrayList are a copy!). If no books with this rating exist, an empty ArrayList is 
    // returned
    public ArrayList<Book> getBooksWithRating(int rating) {
        // First, we sort the underlying ArrayList by rating
        Collections.sort(bookList, Book.makeComparatorRating());

        // Initializes the ArrayList to be returned
        ArrayList<Book> ratingList = new ArrayList<Book>();

        // We then iterate until we find a book with the given rating
        int i = 0;
        while (i < bookList.size()) {
            Book curBook = bookList.get(i);
            if (curBook.getRating() == rating) {
                // If we find a book with a particular rating, then we iterate through all
                // such books, adding them to the ArrayList to be returned
                while (i < bookList.size() && curBook.getRating() == rating) {
                    // Adds a copy of the book to the ArrayList
                    Book copy = new Book(curBook.getTitle(), curBook.getAuthor(), curBook.getRating(), curBook.getReadStatus());
                    ratingList.add(copy);

                    // Increments i and gets the next book in the collection, if there is one
                    i++;
                    if (i < bookList.size()) {
                        curBook = bookList.get(i);
                    }
                }

                // We can then break the loop, since we've found everything that we need
                break;
            }

            // Otherwise, we simply increment i and continue looping
            i++;
        }

        // We then return the resulting ArrayList
        return ratingList;
    }

    // Allows a book with a specific title and author to be added to the library collection
    // Assumes that this book is not already in the collection! Also, preconditions are that
    // title and author are not null.
    public void addBook(String title, String author) {
        // Creates a new Book object and adds it to the underlying ArrayList
        Book newBook = new Book(title, author);
        bookList.add(newBook);
    }

    // Determines whether a book with a given title and author already exist within the
    // library collection, returning true if so and false otherwise. Preconditions are
    // that title and author are not null
    public boolean alreadyInCollection(String title, String author) {
        // Creates a dummy Book object with this title and author
        Book dummy = new Book(title, author);

        // Iterates through the collection to see if such a book can be found
        for (int i = 0; i < bookList.size(); i++) {
            Book curBook = bookList.get(i);
            if (curBook.equals(dummy)) {
                // If we find this book, we return true
                return true;
            }
        }

        // If no books with this title and author are found, we return false
        return false;
    }

    // Sets the book with a particular title and author to read. Note that this assumes that
    // all books in the collection are unique, and that if the book has already been marked 
    // as read, then nothing is changed. Also, if the book cannot be found, then nothing is 
    // done. Preconditions are that title and author are not null.
    public void setToRead(String title, String author) {
        // First, a dummy Book is constructed with the same title and author
        Book dummy = new Book(title, author);

        // We then iterate through the collection to find the desired book
        for (int i = 0; i < bookList.size(); i++) {
            Book curBook = bookList.get(i);
            if (curBook.equals(dummy)) {
                // If we find the desired book, then we update its read status to read,
                // then exit the method, since nothing else needs to be done
                curBook.setRead();
                return;
            }
        }
    }

    // Updates the rating for a particular book, assuming that the book is in the collection.
    // Note that this assumes that all books in the collection are unique, and that if the 
    // desired book cannot be found, then nothing is changed. Preconditions are that title
    // and author are not null, and that rating is between 1 and 5
    public void updateBookRating(String title, String author, int rating) {
        // Creates a dummy Book with the desired title and author
        Book dummy = new Book(title, author);

        // We then iterate through the collection to find the desired book
        for (int i = 0; i < bookList.size(); i++) {
            Book curBook = bookList.get(i);
            if (curBook.equals(dummy)) {
                // If we find the desired book, then we update its rating and exit the
                // method, since there's nothing else to be done
                curBook.updateRating(rating);
                return;
            }
        }
    }

    // Gets a list of all the books that have been read (note that this returns a copy of
    // these books within the ArrayList). These will be sorted by title. If no books that
    // are read can be found within the collection, then an empty ArrayList is simply returned
    public ArrayList<Book> allReadBooks() {
        // First, the underlying ArrayList for the collection is sorted by title
        Collections.sort(bookList, Book.makeComparatorTitle());

        // Initializes the ArrayList of read books that will be returned
        ArrayList<Book> readBooks = new ArrayList<Book>();
        
        // We then iterate through the full ArrayList, finding all of the books that
        // have been read
        for (int i = 0; i < bookList.size(); i++) {
            Book curBook = bookList.get(i);
            if (curBook.getReadStatus().equals(Read.READ)) {
                // If we find a book that has been read, then we make a copy of it and 
                // add it to the ArrayList that will be returned
                Book copy = new Book(curBook.getTitle(), curBook.getAuthor(), curBook.getRating(), curBook.getReadStatus());
                readBooks.add(copy);
            }
        }

        // We then return the resulting ArrayList of read books
        return readBooks;
    }

    // Gets a list of all the books that have not been read (note that this returns a copy 
    // of these books within the ArrayList). These will be sorted by title. If no books that
    // are read can be found within the collection, then an empty ArrayList is returned
    public ArrayList<Book> allUnreadBooks() {
        // First, the underlying ArrayList for the collection is sorted by title
        Collections.sort(bookList, Book.makeComparatorTitle());

        // Initializes the ArrayList of unread books that will be returned
        ArrayList<Book> unreadBooks = new ArrayList<Book>();
        
        // We then iterate through the full ArrayList, finding all of the books that
        // have not been read
        for (int i = 0; i < bookList.size(); i++) {
            Book curBook = bookList.get(i);
            if (curBook.getReadStatus().equals(Read.UNREAD)) {
                // If we find a book that has not been read, then we make a copy of it and 
                // add it to the ArrayList that will be returned
                Book copy = new Book(curBook.getTitle(), curBook.getAuthor(), curBook.getRating(), curBook.getReadStatus());
                unreadBooks.add(copy);
            }
        }

        // We then return the resulting ArrayList of unread books
        return unreadBooks;
    }

    // Gets a random book from the library collection (note that a copy of this book is 
    // returned). Note that this assumes that there is at least one book in the collection
    public Book getRandomBook() {
        // First, we get a random index within the underlying ArrayList, assuming that this 
        // ArrayList is not empty
        Random random = new Random();
        int randIndex = random.nextInt(bookList.size());

        // We then copy the Book at this index and return the copy
        Book randBook = bookList.get(randIndex);
        Book copy = new Book(randBook.getTitle(), randBook.getAuthor(), randBook.getRating(), randBook.getReadStatus());
        return copy;
    }

    // Adds all of the books from a file into the library collection. Note that this method
    // assumes that the first line of the text file is simply Title;Author, as in the given
    // example, and that all books within this file are described in this format, with one
    // book being described per line. If the file does not contain any books, then no books
    // are added to the collection. Preconditions are that filename is not null, and that 
    // the file contains at least one line describing the format
    public void addBooksFromFile(String filename) {
        // First, we open this file and create a corresponding Scanner object for it
        File bookFile = new File(filename);
        Scanner scanner;
        try {
            scanner = new Scanner(bookFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error: given file not found\n");   
            return;
        }

        // We then ignore the first line in the file, and iterate until there are
        // no more lines left to read
        scanner.nextLine();
        while (scanner.hasNext()) {
            // Gets the current line from the Scanner object
            String curLine = scanner.nextLine();

            // We then iterate through this line until we hit a semicolon to construct
            // a String representing the title
            String curTitle = "";
            int i = 0;
            while (curLine.charAt(i) != ';') {
                curTitle += curLine.charAt(i);
                i++;
            }

            // Once we find the semicolon, we ignore it, then iterate through the rest 
            // of the line to construct the author string
            String curAuthor = "";
            i++;
            while (i < curLine.length()) {
                curAuthor += curLine.charAt(i);
                i++;
            }

            // We then create a new Book object with this title and author, and add it 
            // to our collection
            Book curBook = new Book(curTitle, curAuthor);
            bookList.add(curBook);
        }

        // Finally, when we're done reading in lines, the Scanner object is closed
        scanner.close();
    }

    // Determines whether the collection of books is empty, returning true if it is,
    // and false otherwise
    public boolean isEmpty() {
        if (bookList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
