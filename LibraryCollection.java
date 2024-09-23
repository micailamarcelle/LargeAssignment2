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

import java.util.ArrayList;
import java.util.Collections;

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
                    Book copy = new Book(curBook.getTitle(), curBook.getAuthor(), curBook.getRating(), curBook.getReadStatus());
                    authorList.add(copy);

                    // Gets the next book in the list, if such a book exists
                    i++;
                    if (i < bookList.size()) {
                        curBook = bookList.get(i);
                    }
                }

            }
        }

        // We then return the resulting list of Book objects, which will be empty if
        // no books with the specified author are present in the collection.
        return authorList;
    }

    // Gets a set of books with a particular title(not that the books in the returned 
    // ArrayList are a copy!). If there are no books in the collection with this title,
    // then an empty ArrayList is simply returned
    public ArrayList<Book> getBooksWithTitle(String title) {
        return null;
    }

    // Gets a set of books with a particular rating (note that the books in the returned
    // ArrayList are a copy!)
    public ArrayList<Book> getBooksWithRating(int rating) {
        return null;
    }

    // Allows a book with a specific title and author to be added to the library collection
    // Assumes that this book is not already in the collection
    public void addBook(String title, String author) {

    }

    // Determines whether a book with a given title and author already exist within the
    // library collection, returning true if so and false otherwise
    public boolean alreadyInCollection(String title, String author) {
        return false;
    }

    // Sets the book with a particular title and author to read
    public void setToRead(String title, String author) {

    }

    // Updates the rating for a particular book, assuming that the book is in the collection
    public void updateBookRating(String title, String author, int rating) {

    }

    // Gets a list of all the books that have been read (note that this returns a copy of
    // these books within the ArrayList). These will be sorted by title
    public ArrayList<Book> allReadBooks() {
        return null;
    }

    // Gets a list of all the books that have not been read (note that this returns a copy 
    // of these books within the ArrayList). These will be sorted by title
    public ArrayList<Book> allUnreadBooks() {
        return null;
    }

    // Gets a random book from the library collection (note that a copy of this book is 
    // returned)
    public Book getRandomBook() {
        return null;
    }

    // Adds all of the books from a file into the library collection
    public void addBooksFromFile(String filename) {
        
    }
}
