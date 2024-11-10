package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import model.Book;
import model.Read;

public class testBookClass {
	@Test
	public void testGettersandSetters() {
		// create a book without rating or read status
		Book lotr = new Book("The Fellowship of the Ring", "J.R.R. Tolkein");
		assertEquals(lotr.getTitle(), "THE FELLOWSHIP OF THE RING");
		assertEquals(lotr.getAuthor(), "J.R.R. TOLKEIN");
		assertEquals(lotr.getRating(), -1);
		assertEquals(lotr.getReadStatus(),  Read.UNREAD);
		
		lotr.setRead();
		assertEquals(lotr.getReadStatus(),  Read.READ);
		
		lotr.updateRating(5);
		assertEquals(lotr.getRating(), 5);
		
		// for a book with rating and read status
		Book harryPotter = new Book("The Sorcerer's Stone", "J.K. Rowling", 4, Read.READ);
		assertEquals(harryPotter.getTitle(), "THE SORCERER'S STONE");
		assertEquals(harryPotter.getAuthor(), "J.K. ROWLING");
		assertEquals(harryPotter.getRating(), 4);
		assertEquals(harryPotter.getReadStatus(), Read.READ);
	}
	
	private Book a = new Book("The Fellowship of the Ring", "J.R.R. Tolkein");
	private Book b = new Book("The Fellowship of the Ring", "J.R.R. Tolkein");
	private Book c = new Book("The Fellowship of the Ring", "J.R.R. Tolkein", 5, Read.READ);
	private Book d = new Book("The Two Towers", "J.R.R. Tolkein", 5, Read.READ);
	private Book e = new Book("The Sorcerer's Stone", "J.K. Rowling", 4, Read.READ);
	private Book f = new Book("The Sorcerer's Stone", "Not J.K. Rowling", 4, Read.READ);
	
	@Test
	public void testEquals() {
		assertTrue(a.equals(b));
		assertTrue(a.equals(c));
		assertFalse(a.equals(d));
		assertFalse(a.equals(e));
		assertFalse(e.equals(f));
	}
	
	@Test
	public void testToString() {
		assertEquals(a.toString(), "Title: THE FELLOWSHIP OF THE RING; Author: J.R.R. TOLKEIN; Rating: not rated yet; Read Status: not read");
		assertEquals(c.toString(), "Title: THE FELLOWSHIP OF THE RING; Author: J.R.R. TOLKEIN; Rating: 5; Read Status: read");
	}
	
	@Test
	public void compareByTitle() {
		Comparator<Book> comparator = Book.makeComparatorTitle();
		int result = comparator.compare(a, d);
		assertTrue(result < 0);
		
		int result2 = comparator.compare(a, e);
		assertTrue(result2 < 0);
		
		int result3 = comparator.compare(d, f);
		assertTrue(result3 > 0);
	}
	
	@Test
	public void compareByAuthor() {
		Comparator<Book> comparator = Book.makeComparatorAuthor();
		int result = comparator.compare(a, d);
		assertTrue(result == 0);
		
		int result2 = comparator.compare(a, e);
		assertTrue(result2 > 0);
		
		int result3 = comparator.compare(d, f);
		assertTrue(result3 < 0);
	}
	
	@Test
	public void compareByRating() {
		Comparator<Book> comparator = Book.makeComparatorRating();
		int result = comparator.compare(c, d);
		assertTrue(result == 0);
		
		int result2 = comparator.compare(c, e);
		assertTrue(result2 > 0);
		
		int result3 = comparator.compare(f, d);
		assertTrue(result3 < 0);
	}
}
