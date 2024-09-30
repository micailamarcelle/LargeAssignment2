/*
File: LibraryCollectionController.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: In order to improve the re-usability of the code for our library
collection, we chose to utilize the Model-View-Controller design pattern, which
will allow for pieces of the implementation (i.e. the model, view, or controller)
to be swapped out in the future, if necessary. To this end, this class acts as the
Controller element of the MVC pattern, and it essectially functions as a wrapper
class for the LibraryCollection model class.
 */

import java.util.ArrayList;

/*
When it comes to maintaining encapsulation within this class, considering that the
class largely acts as a wrapper for the LibraryCollection class, the task was a 
relatively simple one. All of the inputs to methods of this class are either immutable
objects or primitive types, and all of the outputs for the methods of this class do
not provide the client code with any external references to internal class data, preventing
any escaping references. The vast majority of the work for maintaining encapsulation
with the library collection is done in the LibraryCollection, which ensures that all
of the ArrayLists and Book objects that are returned do not produce any escaping references
to the underlying data structures of the library collection. Note also that additional
protection is provided by the MyLibrary class, which uses input validation to ensure
that none of the methods within this class are being misused in an unintended manner.
Note also that the sole instance variable is defined as private in order to further
maintain encapsulation.
 */

public class LibraryCollectionController {
    // Declares the sole private instance variable of the class, which is a 
    // LibraryCollection object that acts as the underlying model
    private LibraryCollection model;

    /*
    Public constructor for the LibraryCollectionController class, which takes
    no inputs, and which produces a new (empty) LibraryCollection object. The
    sole private instance variable of the class is initialized to an empty
    LibraryCollection object. Considering that there are no inputs to this
    method, there are no preconditions.
     */
    public LibraryCollectionController() {
        model = new LibraryCollection();
    }

    /*
    Public method for obtaining a list of the books currently in the collection,
    with an enumerated type being given as the input to determine what type of 
    sorting to use for this returned list. 

    @pre howSort != null
    @return ArrayList<Book> containing a sorted list of books in the collection
     */
    public ArrayList<Book> cGetSortedCollection(TypeSort howSort) {
        return model.getSortedCollection(howSort);
    }

    /*
    Public method for obtaining a list of the books currently in the collection
    that have a particular author. A String corresponding to the author's name
    is given as the only input to determine which books to return, and note
    that this method is not case-sensitive with respect to the author's name

    @pre author != null
    @return ArrayList<Book> containing a list of Books with a particular author
     */
    public ArrayList<Book> cGetBooksWithAuthor(String author) {
        return model.getBooksWithAuthor(author);
    }

    /*
    Public method for obtaining a list of the books currently in the collection
    that have a particular title. A String corresponding to this title is given
    as the only input to determine which books to return, and note that this 
    method is not case-sensitive with respect to the title. 

    @pre title != null
    @return ArrayList<Book> containing a list of books with a particular title
     */
    public ArrayList<Book> cGetBooksWithTitle(String title) {
        return model.getBooksWithTitle(title);
    }

    /*
    Public method for obtaining a list of the books currently in the collection
    that have a particular rating. An integer corresponding to this rating is 
    given as the only input to determine which books to return, and it is assumed
    that this rating is within the valid range of 1-5.

    @pre rating >= 1 && rating <= 5
    @return ArrayList<Book> containing a list of books with a particular rating
     */
    public ArrayList<Book> cGetBooksWithRating(int rating) {
        return model.getBooksWithRating(rating);
    }

    /*
    Public method for adding a book with a particular title and author to the
    library collection. Note that this method assumes that a book with this
    title and author does not already exist within the collection.

    @pre title != null && author != null && !model.alreadyInCollection(title, author)
     */
    public void cAddBook(String title, String author) {
        model.addBook(title, author);
    }

    /*
    Public method for determining whether the collection already contains a 
    book with the given title and author; note that this method is no case sensitive.
    Returns true if the given book is already in the library, and false otherwise

    @pre title != null && author != null 
    @return true if the book is already in the collection, false otherwise
     */
    public boolean cAlreadyInCollection(String title, String author) {
        return model.alreadyInCollection(title, author);
    }

    /*
    Public method for setting the read status of a particular book in the library 
    to read. Note that this assumes that all books in the library collection are
    unique, and that if the book has already been marked as read, then nothing is
    changed. Also assumes that the given book is in the library collection

    @pre title != null && author != null && model.alreadyInCollection(title, author)
     */
    public void cSetToRead(String title, String author) {
        model.setToRead(title, author);
    }

    /*
    Public method for updating the rating associated with a particular book in the
    library collection. Note that this method assumes that all books in the library
    collection are unique, and that the given book is already in the library collection.
    The given rating must also be in the valid range from 1-5.

    @pre title != null && author != null && model.alreadyInCollection(title, author)
        && rating >= 1 && rating <= 5
     */
    public void cUpdateBookRating(String title, String author, int rating) {
        model.updateBookRating(title, author, rating);
    }

    /*
    Public method for obtaining a list of all the books currently in the library
    collection that have already been marked as read. Note that the returned list
    of books will be sorted by title. Also, considering that there are no parameters,
    this method has no preconditions.

    @return ArrayList<Book> of all the books in the collection that have been read
     */
    public ArrayList<Book> cAllReadBooks() {
        return model.allReadBooks();
    }

    /*
    Public method for obtaining a list of all the books currently in the library 
    collection that have not been marked as read. Note that the returned list
    of books will be sorted by title. Also, considering that there are no parameters,
    this method has no preconditions.

    @return ArrayList<Book> of all the books in the collection that haven't been read
     */
    public ArrayList<Book> cAllUnreadBooks() {
        return model.allUnreadBooks();
    }

    /*
    Public method for obtaining a random unread book from the library collection. Note that
    this method assumes that the collection contains at least one book

    @pre !model.isEmpty()
    @return Book object representing a random unread book from the collection, or null if there
        are no unread books.
     */
    public Book cGetRandomBook() {
        return model.getRandomBook();
    }

    /*
    Public method for adding all books from a given file into our library collection. 
    Note that if a book in the file is already in the library collection, then it will
    not be added to the library collection a second time. Also, this method assumes that
    the given file is in the format that was specified for this project, with the first
    line of the file always being "Title;Author" and every other line in the file following
    suit.

    @pre filename != null && filename is formatted according to the given example
     */
    public void cAddBooksFromFile(String filename) {
        model.addBooksFromFile(filename);
    }

    /*
    Public method for determining whether the library collection currently has any books
    in it. Returns true if it does, and returns false otherwise. Note that this method
    takes in nothing and requires no assumptions, so it has no preconditions.

    @return true if the library collection is empty, false otherwise
     */
    public boolean cIsEmpty() {
        return model.isEmpty();
    }
}

