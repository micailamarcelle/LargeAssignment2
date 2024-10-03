/*
File: LibraryCollection.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: This class is designed to organize the Book objects that will
be stored within the library collection, and will provide methods allowing
for sorting all of the books according to different characteristics, searching
for books with particular characteristics, and updating the books currently
in the collection. Note that the underlying data structure for the collection
is an ArrayList of Book objects, since using such a data structure allows for 
new books to be added to the collection easily and efficiently, and allows the
class as a whole to better model a collection of unique books, although there
are a few trade-offs with search efficiency, which are noted below. Note that this
class represents the Model element of the Model-View-Controller design pattern.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/*
When it comes to maintaining encapsulation, this class does quite a bit of the 
heavy lifting for the program as a whole. As is described above, the underlying
data structure for the library collection is an ArrayList of Book objects. To
prevent any escaping references from occurring, each time a Book object is returned
by a method (either directly or within an ArrayList of Books), the actual thing
being returned is a copy of the Book within the underlying ArrayList, rather than
the mutable Book object itself. Furthermore, anytime the method returns an ArrayList 
containing all of the books within the collection, the list being returned is 
a copy of the underlying ArrayList rather than this underlying ArrayList itself,
with all of the Book objects within being copied as well. With this, the class is
able to effectively maintain a great degree of encapsulation. Additional protection
is then provided via input validation in the MyLibrary class, which prevents any
improper data from corrupting the Books in the collection. Note also that the sole 
instance variable is declared as private to further maintain encapsulation.
 */

public class LibraryCollection {
    // Declares the sole private instance variable of the LibraryCollection class,
    // which is an ArrayList of the Book objects currently within the collection
    private ArrayList<Book> bookList;

    /*
    Public constructor for the LibraryCollection class, which takes no inputs, and
    which produces an (empty) LibraryCollection object. Note that the sole private
    instance variable is initialized to an empty ArrayList of Book objects. Since this
    method takes no inputs and has no assumptions, it also has no preconditions.
     */
    public LibraryCollection() {
        this.bookList = new ArrayList<Book>();
    }

    /*
    Public method for obtaining a sorted version of the underlying ArrayList, with an
    enumerated type (specifically, TypeSort) being the only input, which is used to 
    determine what type of sorting to utilize. To prevent issues with encapsulation,
    the ArrayList<Book> that is returned by this method is a copy of the underlying
    ArrayList<Book> for the class, with each Book object within the ArrayList being
    copied as well to prevent any escaping references. Note that, based on the spec, 
    the only client-available sorting options are by title and author, according to the 
    functionality of the getBooks command. The use of the TypeSort enum then removes
    any potential ambiguity involved in determining how to sort, and simplifies the 
    process of ensuring that the type of sorting being passed into this method is a 
    valid one. If there are no books currently in the collection, an empty ArrayList<Book>
    is returned. Also, note that the Collections.sort() method is utilized in order
    to perform the actual sorting, with the appropriate Comparator<Book> being passed
    in according to the given type of sorting. 

    @pre howSort != null
    @return ArrayList<Book> of the books currently in the collection, sorted according to howSort
     */
    public ArrayList<Book> getSortedCollection(TypeSort howSort) {
        // First, the Collections class is used to sort the underlying ArrayList according
        // to the given type of sorting
        if (howSort.equals(TypeSort.TITLE)) {
            Collections.sort(bookList, Book.makeComparatorTitle());
        } else if (howSort.equals(TypeSort.AUTHOR)) {
            Collections.sort(bookList, Book.makeComparatorAuthor());
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

    /*
    Public method for obtaining a list of the Book objects currently in the collection that
    have a particular author. Note that the returned list of Books will not have any particular
    order, and that if there are no books with the given author currently in the collection, 
    then an empty ArrayList<Book> will simply be returned. Also, to maintain encapsulation,
    the ArrayList<Book> that is returned is not connected in any way to the underlying ArrayList
    of the class, and all Book objects in the returned ArrayList are copies of those in the 
    actual ArrayList. In terms of the sorting algorithm used, this method first sorts the ArrayList
    according to author, then uses linear search to find the given author and all books associated
    with them. While this is slightly less efficient than using hashing, performing searching
    in this way allows for more rapid sorting, without too bad of a search runtime in the average
    case, and allows for the Book class to contain all book-related information. This method is 
    not case-sensitive. 

    @pre author != null
    @return ArrayList<Book> of Book objects with the given author
     */
    public ArrayList<Book> getBooksWithAuthor(String author) {
        // First, we sort the underlying ArrayList by author
        Collections.sort(bookList, Book.makeComparatorAuthor());

        // Initializes the ArrayList to be returned
        ArrayList<Book> authorList = new ArrayList<Book>();

        // Converts the given author name to uppercase to avoid case sensitivity, knowing that
        // the Book class ensures that all internal author data is in all uppercase
        author = author.toUpperCase();

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

    /*
    Public method for obtaining a list of the Book objects currently in the collection 
    that have a particular title. Note that the returned list of Books will not have
    any particular order, and that if there are no books in the collection with the given
    title, then an empty ArrayList is simply returned. Also, to maintain encapsulation,
    the returned ArrayList<Book> has no connection to the underlying ArrayList<Book> of
    the class, and all Book objects within the returned ArrayList are copies of those in
    the actual ArrayList of the class. In terms of how this is actually done, this method
    first sorts the underlying ArrayList<Book> according to title, then uses linear search
    to find all the books with the given title. While this may be slightly less efficient
    of a search algorithm than hashing might provide, structuring our program in this manner
    allows for more rapid, simple sorting while keeping all of the book-related information
    within the Book class, and, ultimately, the runtime in the average case is not all too
    much worse for searching. Note that this method is also not case-sensitive. 

    @pre title != null
    @return ArrayList<Book> of the Books with the given title
     */
    public ArrayList<Book> getBooksWithTitle(String title) {
        // First, we sort the underlying ArrayList by title
        Collections.sort(bookList, Book.makeComparatorTitle());

        // Initializes the ArrayList to be returned
        ArrayList<Book> titleList = new ArrayList<Book>();

        // Puts the given title in uppercase to avoid case sensitivity, considering that the
        // Book class constructor ensures that all internal strings are in uppercase
        title = title.toUpperCase();

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

    /*
    Public method for obtaining a list of the Book objects currently in the collection that
    have a particular rating. Note that the returned list of Books will not have any particular
    order, and that if there are no books in the collection with the given rating, then an 
    empty ArrayList<Book> will simply be returned. Also, to maintain encapsulation,
    the returned ArrayList<Book> has no connection to the underlying ArrayList<Book> of
    the class, and all Book objects within the returned ArrayList are copies of those in
    the actual ArrayList of the class. It's also assumed that the given rating is within 
    the valid range from 1-5, so books that have not yet been rated cannot be searched for
    in this manner. In terms of how this is actually done, the underlying ArrayList<Book>
    is first sorted with respect to rating, and linear search is then used to find all
    books with the given rating. While this is slightly less efficent than searching with 
    a HashMap might be, structuring our program in this way allows for fast, simple sorting
    while keeping all book-related information within the Book class, all without the 
    runtime of the search algorithm being overly inefficient.

    @pre rating >= 1 && rating <= 5
    @return ArrayList<Book> of the Books currently in the collection with the given rating
     */
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

    /*
    Public method which takes in two Strings representing the title and author (respectively),
    and which adds the corresponding Book object into the underlying ArrayList<Book> for the 
    class. Note that this method assumes that the corresponding Book object has not already
    been added to the library collection, with this assumption being ensured to be true by the 
    input validation present within the class representing the View element of the Model-View-Controller. 
    This method then does not return anything.

    @pre title != null && author != null && !alreadyInCollection(title, author)
     */
    public void addBook(String title, String author) {
        // Creates a new Book object and adds it to the underlying ArrayList
        Book newBook = new Book(title, author);
        bookList.add(newBook);
    }

    /*
    Public method that can be used to determine whether a book with the given title and
    author already exists within the collection. This method returns true if the book
    is already in the collection, and false otherwise. Note that, in order to actually
    try to find the given book, linear search is used on the underlying ArrayList. While
    this is slightly less efficient than binary search may be, using this algorithm ensures
    that if we have multiple books with the same author or the same title, then we know
    without additional checking that the book we've found is the correct one, which is 
    why we chose to implement this method in this manner. Furthermore, the average-case
    runtime for linear search is not significantly worse than that of binary search,
    especially when considering the additional checking required for binary search, which
    further supports this decision.

    @pre title != null && author != null
    @return true if the book is already in our library collection, and false otherwise
     */
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

    /*
    Public method which takes in two Strings representing the title and author, and which marks
    the corresponding book in the collection as being read. Note that this method assumes that
    all books in the collection are unique, and that a book with the given title and author
    is present within the library collection. Furthermore, if the book has already been marked
    as read, then nothing is actually changed within the program state. The method does not 
    return anything. Note again that the search algorithm used here is linear search, as is 
    the case with the method above, and this is similarly used to prevent any ambiguity and
    additional checking in the case that multiple books have the same author or the same title,
    especially considering that linear search is still a relatively efficient algorithm.

    @pre title != null && author != null && alreadyInCollection(title, author)
     */
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

    /*
    Public method which updates the rating for the book in the collection with the given title
    and author. Note that this method assumes that all books in the collection are unique, and
    that a book exists within the collection with the given title and author. Also, it is 
    assumed that the given rating is within the valid range from 1-5. The method then does not
    return anything. Note that these assumptions are ensured to be true via the input validation
    present within the View class of the Model-View-Controller structure. Furthermore, as is the
    case with the above two methods, linear search is used (rather than binary search) in order 
    to prevent any ambiguity and additional checking in the case when multiple books have the 
    same author or title, especially considering that linear search is still relatively efficient.

    @pre title != null && author != null && rating >= 1 && rating <= 5 && alreadyInCollection(title, author)
     */
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

    /*
    Public method which obtains a list of all of the Book objects currently in the library
    collection that have been marked as read, with the returned list being sorted according
    to the titles of the books. If no books that have been read are present within the library
    collection, then an empty ArrayList<Book> is simply returned. Also, note that in order to 
    maintain encapsulation, the returned ArrayList<Book> does not have any connections with the
    underlying ArrayList<Book> of the class, and that all of the Book objects in the returned
    ArrayList are copies of those in the underlying ArrayList. This method then does not have 
    any preconditions, since it has no inputs and makes no assumptions. Again, this method
    utilizes the linear search algorithm, in this case to more simply ensure that the 
    returned list is automatically sorted by title. The big-O efficiency also would not differ
    if the underlying ArrayList was sorted according to read vs. unread, which is why we chose
    not to utilize this approach. 

    @return ArrayList<Book> of all the Book objects in the collection that have been read
     */
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

    /*
    Public method which obtains a list of all of the Book objects currently in the library
    collection that have not been marked as read, with the returned list being sorted according
    to the titles of the books. If no books that have not been read are present within the library
    collection, then an empty ArrayList<Book> is simply returned. Also, note that in order to 
    maintain encapsulation, the returned ArrayList<Book> does not have any connections with the
    underlying ArrayList<Book> of the class, and that all of the Book objects in the returned
    ArrayList are copies of those in the underlying ArrayList. This method then does not have 
    any preconditions, since it has no inputs and makes no assumptions. As is the case with 
    the above method, linear search is utilized in order to find these unread books, since this
    helps to automatically ensure that the returned ArrayList<Book> is sorted according to title.
    Furthermore, the big-O of this method would not differ if we were to pre-sort the underlying
    array by read/unread, which is why we chose not to utilize this slightly more complicated
    approach. 

    @return ArrayList<Book> of all the Book objects in the collection that have not been read
     */
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

    /*
    Public method which returns a random Book object corresponding to an unread book in the library
    collection. In order to maintain encapsulation, this Book object is actually a copy of
    one of the Books in the underlying ArrayList<Book> of the class. This method also assumes
    that at least one Book exists within the library collection, though it need not necessarily
    be unread, since a null return value is used to indicate that there are no unread books present
    within the library collection. The class representing the View then ensures that these assumptions
    are upheld. Note that the requirement that the collection not be empty is simply there to ensure
    that the view throws an appropriate error message, according to whether there are no books or simply
    no unread books. 

    @pre !isEmpty()
    @return Book object which represents a copy of a random unread book in the library collection, or null
        if there are no unread books. 
     */
    public Book getRandomBook() {
        // First, we get a list of all of the unread books in the library
        ArrayList<Book> unread = allUnreadBooks();

        // If there is nothing in the array, then we return null
        if (unread.size() == 0) {
            return null;
        }

        // Otherwise, we get a random index within this ArrayList of unread books
        Random random = new Random();
        int randIndex = random.nextInt(unread.size());

        // We then return the copy of this random book
        Book randBook = unread.get(randIndex);
        return randBook;
    }

    /*
    Public method which adds all of the books from a file into the library collection. This 
    method assumes that the given file is structured in the same manner as the example that
    was given with the spec, with the first line simply being "Title;Author", and all other
    lines describing books with this specific format structure (one book per line). Note, 
    though, that only books that are not already in the library collection are added to 
    the library collection from the file. This method does not return anything. Also, if
    the given file cannot be found, then an error message is simply printed, and the method
    automatically returns. 

    @pre filename != null && (file structure is as described in the spec)
     */
    public void addBooksFromFile(String filename) {
        // First, we open this file and create a corresponding Scanner object for it
        File bookFile = new File(filename);
        Scanner scanner;
        try {
            scanner = new Scanner(bookFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error: given file not found");   
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
            // to our collection, if it is not already in the collection. Otherwise,
            // nothing is done
            if (!alreadyInCollection(curTitle, curAuthor)) {
                Book curBook = new Book(curTitle, curAuthor);
                bookList.add(curBook);
            }
        }

        // Finally, when we're done reading in lines, the Scanner object is closed
        scanner.close();
    }

    /*
    Public method for determining whether the library collection is empty; in other words,
    a method for determining whether the underlying ArrayList<Book> contains any Book
    objects. Returns true is the underyling ArrayList is empty, and false otherwise. This
    method has no preconditions, as it makes no assumptions and takes in no inputs.

    @return true if the underlying ArrayList<Book> is empty, false otherwise
     */
    public boolean isEmpty() {
        if (bookList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
