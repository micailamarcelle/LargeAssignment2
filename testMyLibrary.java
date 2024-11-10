//package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Book;
import model.LibraryCollection;
import model.LibraryCollectionController;
import model.MyLibrary;
import model.Read;
import model.TypeSort;

public class testMyLibrary {
	private LibraryCollectionController controller = new LibraryCollectionController();
	
	@Test
	public void testGetSorted() {
		// sort by author
		controller.cAddBook("The Fellowship of the Ring", "J.R.R. Tolkein");
		controller.cAddBook("The Two Towers", "J.R.R. Tolkein");
		controller.cAddBook("The Sorcerer's Stone", "J.K. Rowling");
		 ArrayList<Book> sortedByAuthor = controller.cGetSortedCollection(TypeSort.AUTHOR);
		 //assertEquals(sortedByAuthor.get(0).getTitle(), "THE SORCERER'S STONE");
		 assertEquals(sortedByAuthor.get(0).getTitle(), "THE SORCERER'S STONE");
		 assertEquals(sortedByAuthor.get(1).getTitle(), "THE FELLOWSHIP OF THE RING");
		 assertEquals(sortedByAuthor.get(2).getTitle(), "THE TWO TOWERS");
		 
		 //sort by title
		 ArrayList<Book> sortedByTitle = controller.cGetSortedCollection(TypeSort.TITLE);
		 assertEquals(sortedByTitle.get(1).getTitle(), "THE SORCERER'S STONE");
		 assertEquals(sortedByTitle.get(0).getTitle(), "THE FELLOWSHIP OF THE RING");
		 assertEquals(sortedByTitle.get(2).getTitle(), "THE TWO TOWERS");
	}
	
	@Test
	public void testBooksWithAuthor() {
		controller.cAddBook("The Fellowship of the Ring", "J.R.R. Tolkein");
		controller.cAddBook("The Two Towers", "J.R.R. Tolkein");
		controller.cAddBook("The Sorcerer's Stone", "J.K. Rowling");
		ArrayList<Book> lotr = controller.cGetBooksWithAuthor("J.R.R. Tolkein");
		assertEquals(lotr.size(), 2);
		assertEquals(lotr.get(0).getTitle(), "THE FELLOWSHIP OF THE RING");
		assertEquals(lotr.get(1).getTitle(), "THE TWO TOWERS");
	}
	
	@Test
	public void testBooksWithTitle() {
		controller.cAddBook("It", "Stephen King");
		controller.cAddBook("The Fellowship of the Ring", "J.R.R. Tolkein");
		controller.cAddBook("The Two Towers", "J.R.R. Tolkein");
		controller.cAddBook("The Sorcerer's Stone", "J.K. Rowling");
		controller.cAddBook("It", "Other Author");
		ArrayList<Book> it = controller.cGetBooksWithTitle("it");
		assertEquals(it.size(), 2);
		assertEquals(it.get(1).getAuthor(), "OTHER AUTHOR");
		assertEquals(it.get(0).getAuthor(), "STEPHEN KING");
	}
	
	@Test
	public void testBooksWithRating() {
		controller.cAddBook("It", "Stephen King");
		controller.cUpdateBookRating("it", "Stephen King", 3);
		controller.cAddBook("The Fellowship of the Ring", "J.R.R. Tolkein");
		controller.cUpdateBookRating("The Fellowship of the Ring", "J.R.R. Tolkein", 5);
		controller.cAddBook("The Two Towers", "J.R.R. Tolkein");
		controller.cUpdateBookRating("The Two Towers", "J.R.R. Tolkein", 4);
		controller.cAddBook("The Sorcerer's Stone", "J.K. Rowling");
		controller.cUpdateBookRating("The Sorcerer's Stone", "J.K. Rowling", 4);
		
		ArrayList<Book> booksAs4 = controller.cGetBooksWithRating(4);
		assertEquals(booksAs4.get(1).getTitle(), "THE SORCERER'S STONE");
		assertEquals(booksAs4.get(0).getTitle(), "THE TWO TOWERS");
		
		ArrayList<Book> booksAs3 = controller.cGetBooksWithRating(3);
		assertEquals(booksAs3.get(0).getTitle(), "IT");
		
		ArrayList<Book> booksAs5 = controller.cGetBooksWithRating(5);
		assertEquals(booksAs5.get(0).getTitle(), "THE FELLOWSHIP OF THE RING");
	}
	
	@Test
	public void testInCollection() {
		controller.cAddBook("It", "Stephen King");
		assertTrue(controller.cAlreadyInCollection("it", "Stephen king"));
		assertFalse(controller.cAlreadyInCollection("The Fellowship of the Ring", "J.R.R. Tolkein"));
		assertFalse(controller.cAlreadyInCollection("The Hunger Games", "Suzanne Collins"));
	}
	
	@Test
	public void testSetToRead() {
		controller.cAddBook("It", "Stephen King");
		assertTrue(controller.cAllReadBooks().size() == 0);
		controller.cSetToRead("It", "Stephen King");
		assertTrue(controller.cAllReadBooks().size() == 1);
		assertTrue(controller.cAllUnreadBooks().size() == 0);
		
		controller.cAddBook("The Fellowship of the Ring", "J.R.R. Tolkein");
		assertTrue(controller.cAllReadBooks().size() == 1);
		assertTrue(controller.cAllUnreadBooks().size() == 1);
		
		controller.cAddBook("The Hunger Games", "Suzanne Collins");
		assertTrue(controller.cAllUnreadBooks().size() == 2);
		controller.cSetToRead("The Hunger Games", "Suzanne Collins");
		assertTrue(controller.cAllUnreadBooks().size() == 1);
		assertTrue(controller.cAllReadBooks().size() == 2);
	}
	
	@Test
	public void testRandomBook() {
		assertNull(controller.cGetRandomBook());
		controller.cAddBook("It", "Stephen King");
		assertEquals(controller.cGetRandomBook().getTitle(), "IT");
		controller.cAddBook("The Hunger Games", "Suzanne Collins");
		Book random = controller.cGetRandomBook();
		assertTrue(controller.cAlreadyInCollection(random.getTitle(), random.getAuthor()));
	}
	
	@Test
	public void testFromFile() {
		assertTrue(controller.cIsEmpty());
		controller.cAddBooksFromFile("books.txt");
		assertFalse(controller.cIsEmpty());
		assertFalse(controller.cIsEmpty());
	}
}
