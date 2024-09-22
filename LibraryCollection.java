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
        return null;
    }

    // Gets a set of books with a particular author (note that the books in the returned
    // ArrayList are a copy!)
    public ArrayList<Book> getBooksWithAuthor(String author) {
        return null;
    }

    // Gets a set of books with a particular title(not that the books in the returned 
    // ArrayList are a copy!)
    public ArrayList<Book> getBooksWithTitle(String title) {
        return null;
    }

    // Gets a set of books with a particular rating (note that the books in the returned
    // ArrayList are a copy!)
    public ArrayList<Book> getBooksWithRating(int rating) {
        return null;
    }
}
