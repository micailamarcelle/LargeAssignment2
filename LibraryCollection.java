/*
File: LibraryCollection.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: This class is designed to organize the Book objects that will
be stored within the library collection, and will provide methods allowing
for the sorting of books according to different characteristics, searching
for books with particular characteristics, and updating the books currently
in the collection. Note that the underlying data structure for the collection
is an ArrayList of Book objects, since using such a data structure allows for 
new books to be added to the collection easily and efficiently. Note that this
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
the Book object itself. Furthermore, anytime the method returns an ArrayList 
containing all of the books within the collection, the list being returned is 
a copy of the underlying ArrayList rather than this underlying ArrayList itself,
with all of the Book objects within being copied as well. With this, the class is
able to effectively maintain a great degree of encapsulation. Additional protection
is then provided via input validation in the MyLibrary class, which prevents any
unintended misuse of the methods within this class. Note also that the sole instance
variable is declared as private to further maintain encapsulation.
 */

public class LibraryCollection {
    // Declares the sole private instance variable of the LibraryCollection class,
    // which is an ArrayList of the 
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
    the only possible sorting options are by title and author, according to the 
    functionality of the getBooks command. 

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
    actual ArrayList.

    @pre author != null
    @return ArrayList<Book> of Book objects with the given author
     */
    public ArrayList<Book> getBooksWithAuthor(String author) {
        // First, we sort the underlying ArrayList by author
        Collections.sort(bookList, Book.makeComparatorAuthor());

        // Initializes the ArrayList to be returned
        ArrayList<Book> authorList = new ArrayList<Book>();

        // Converts the given author name to lowercase to avoid case sensitivity
        author = author.toLowerCase();

        // We then iterate until we find a book with the given author
        int i = 0;
        while (i < bookList.size()) {
            Book curBook = bookList.get(i);
            if (curBook.getAuthor().toLowerCase().equals(author)) {
                // If we find a book with the given author, then we add all books with this
                // author into the return list
                while (i < bookList.size() && curBook.getAuthor().toLowerCase().equals(author)) {
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
    the actual ArrayList of the class. 

    @pre title != null
    @return ArrayList<Book> of the Books with the given title
     */
    public ArrayList<Book> getBooksWithTitle(String title) {
        // First, we sort the underlying ArrayList by title
        Collections.sort(bookList, Book.makeComparatorTitle());

        // Initializes the ArrayList to be returned
        ArrayList<Book> titleList = new ArrayList<Book>();

        // Puts the given title in lowercase to avoid case sensitivity
        title = title.toLowerCase();

        // We then iterate until we find a book with the given title
        int i = 0;
        while (i < bookList.size()) {
            Book curBook = bookList.get(i);
            if (curBook.getTitle().toLowerCase().equals(title)) {
                // If we find a book with the given title, then we iterate through all such
                // books, adding copies of them to our ArrayList to be returned
                while (i < bookList.size() && curBook.getTitle().toLowerCase().equals(title)) {
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
    the valid range from 1-5

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
    been added to the library collection. This method then does not return anything.

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
    is already in the collection, and false otherwise. 

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
    return anything.

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
    return anything.

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
    any preconditions, since it has no inputs and makes no assumptions.

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
    any preconditions, since it has no inputs and makes no assumptions.

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
    Public method which returns a random Book object corresponding to a book in the library
    collection. In order to maintain encapsulation, this Book object is actually a copy of
    one of the Books in the underlying ArrayList<Book> of the class. This method also assumes
    that at least one unread Book exists within the library collection. Note also that this method 
    specifically returns a books that has not been read. 

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
    the library collection from the file. This method does not return anything.

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

    // Determines whether the collection of books is empty, returning true if it is,
    // and false otherwise
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
