/*
File: Book.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: This class implements the Book object, which will be used to model the
books within the library collection. Each Book object will have an associated title,
author, rating, and read status, and the class contains several static factory methods for
creating Comparator<Book> objects, which will be used to sort Book objects according
to differing characteristics. 
 */

// NEED TO DO: ENSURE THAT ALL RETURN VALUES ARE UPDATED

import java.util.Comparator;

/*
When it comes to maintaining encapsulation, first note that all of the instance variables
of this class are marked as private, which prevents any direct manipulation. These instance
variables are also all immutable objects or primitive types, which means that whenver a user
passes a value for one of these variables into a constructor or setter, they do not maintain
an external reference that can be used to directly manipulate the internal instance variables.
However, it is important to note that this class contains setters for the rating and readStatus
instance variables, which makes the class mutable. Each time a Book is returned within the model,
though, it's a copy of a Book object within the underlying model to prevent any escaping 
references, and design by contract is used to help prevent misuse of the setter method for the
rating instance variable, with input validation being used to ensure that no improper values are
placed within the Book object. 
 */

public class Book {
    // Declares the private instance variables, which include a String for the title,
    // a String for the author, and int for the rating (must be 1 through 5), and 
    // a Read enumerated type object for the read status of the book.
    private String title;
    private String author;
    private int rating;
    private Read readStatus;

    /*
    Public constructor for the Book class, which takes in a title and author, then
    initializes a new Book object. Note that the rating for this Book object is
    initialized to -1, to show that it has not yet been rated, and the read status
    is initializes to UNREAD

    @pre title != null && author != null
     */
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.rating = -1;
        this.readStatus = Read.UNREAD;
    }

    /*
    Secondary public constructor for the Book class, which effectively acts as a 
    copy constructor. It takes in a title, author, rating, and read status, then
    constructs a new Book object with all of the given values as the values for 
    its instance variables.

    @pre title != null && author != null && rating >= 1 && rating <= 5 && readStatus != null
     */
    public Book(String title, String author, int rating, Read readStatus) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.readStatus = readStatus;
    }

    /*
    Public method for determining whether a particular Book object is equal to another
    Book object. This returns true if both books have the same author and title (with
    rating and read status being ignored), and false if they differ in either of these
    characteristics. Such a method will be utilized in the LibraryCollection class in
    order to quickly determine whether a book with a particular author and title is 
    already in the collection. 

    @pre title != null && author != null
     */  
    public boolean equals(Book other) {
        // Checks to see whether these books have the same title and author
        // Note that everything is converted to lowercase to avoid case sensitivity
        String lowerTitle = this.title.toLowerCase();
        String lowerOtherTitle = other.title.toLowerCase();
        String lowerAuthor = this.author.toLowerCase();
        String lowerOtherAuthor = other.author.toLowerCase();
        if (lowerTitle.equals(lowerOtherTitle) && lowerAuthor.equals(lowerOtherAuthor)) {
            // If so, return true
            return true;
        } else {
            // Otherwise, return false
            return false;
        }
    }

    /*
    Public method for updating the read status of a book to READ. If the book has 
    already been set to READ, then no changes to the Book object are made. This
    method takes no inputs and has no outputs, and thus does not have any preconditions
     */
    public void setRead() {
        readStatus = Read.READ;
    }

    /*
    Public method for updating the rating of a book. Takes in a single integer input,
    which represents the updated rating of the book, and it is assumed that this integer
    is between 1 and 5 (inclusive), since this is the valid range of possible ratings.
    This condition is ensured to be true within the code that obtains the new rating
    from the user.

    @pre newRating >= 1 && newRating <= 5
     */
    public void updateRating(int newRating) {
        rating = newRating;
    }

    /*
    Public getter for the title of a particular book. Takes no inputs, and returns 
    a String representing the book's title.
     */
    public String getTitle() {
        return title;
    }

    /*
    Public getter for the for the author of a particular book. Takes no inputs, and
    returns a String representing the book's author
     */
    public String getAuthor() {
        return author;
    }

    /*
    Public getter for the rating of a particular book. Takes no inputs, and returns
    an integer representing the book's rating
     */
    public int getRating() {
        return rating;
    }

    /*
    Public getter for the read status of a particular book. Takes no inputs, and returns
    a Read enum type representing the book's read status
     */
    public Read getReadStatus() {
        return readStatus;
    }

    /*
    Public factory method for returning an object implementing the Comparator<Book> 
    interface. Specifically, this Comparator object will be constructed in order to
    allow for the Book objects to be sorted in terms of their title, in ascending
    order, which will be necessary for the overall functionality of the LibraryCollection
    class. This method takes no inputs, and returns an object of the Comparator class,
    defined within the method itself. As a result, it has no preconditions. Note also
    that the method is defined as static in order to allow for such a Comparator to 
    be constructed directly through the class rather than through an instance of it.
     */
    public static Comparator<Book> makeComparatorTitle() {
        return new Comparator<Book>() {
            public int compare(Book book1, Book book2) {
                return book1.title.compareTo(book2.title);
            }
        };
    }

    /*
    Public factory method for returning an object implementing the Comparator<Book> 
    interface. Specifically, this Comparator object will be constructed in order to
    allow for the Book objects to be sorted in terms of their author, in ascending
    order, which will be necessary for the overall functionality of the LibraryCollection
    class. This method takes no inputs, and returns an object of the Comparator class,
    defined within the method itself. As a result, it has no preconditions. Note also
    that the method is defined as static in order to allow for such a Comparator to 
    be constructed directly through the class rather than through an instance of it.
     */
    public static Comparator<Book> makeComparatorAuthor() {
        return new Comparator<Book>() {
            public int compare(Book book1, Book book2) {
                return book1.author.compareTo(book2.author);
            }
        };
    }

    /*
    Public factory method for returning an object implementing the Comparator<Book> 
    interface. Specifically, this Comparator object will be constructed in order to
    allow for the Book objects to be sorted in terms of their rating, in ascending
    order, which will be necessary for the overall functionality of the LibraryCollection
    class. This method takes no inputs, and returns an object of the Comparator class,
    defined within the method itself. As a result, it has no preconditions. Note also
    that the method is defined as static in order to allow for such a Comparator to 
    be constructed directly through the class rather than through an instance of it.
     */
    public static Comparator<Book> makeComparatorRating() {
        return new Comparator<Book>() {
            public int compare(Book book1, Book book2) {
                return book1.rating - book2.rating;
            }
        };
    }
}
