/*
File: MyLibrary.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: Implements the main() method for the library collection, so this is the
file that will be run in order to actually run the library collection program as
a whole. More specifically, this code will handle the user-facing front-end of the
code, using the previously-constructed classes to manage user interactions.
 */

public class MyLibrary {
    public static void main(String[] args) {
        // search
        String searchType = "";
        while (!(searchType.equals("title") || searchType.equals("author") || searchType.equals("book"))) {
            System.out.print("Enter search type (title, author, or book): ");
            String searchType = new Scanner(System.in).nextLine().toLowerCase();
        }
        // addBook

        // setToRead

        // rate

        // getBooks

        // suggestRead

        // addBooks
    }
}