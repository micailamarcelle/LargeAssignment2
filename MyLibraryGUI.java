/*
File: MyLibraryGUI.java
Authors: Micaila Marcelle (micailamarcelle) and Elise Bushra (ebushra)
Course: CSC 335
Purpose: Provides an alternate View for the library collection, which is GUI-based rather
than text-based. Running this file will run the overall library collection program, and 
will provide the user with a GUI (built using Swing) that they can utilize in order to 
interact with the library collection itself. Note that this GUI will have all of the 
functionalities provided by the text-based view, but in a different, slightly more 
convenient format.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

public class MyLibraryGUI extends JFrame {
    private LibraryCollectionController controller;
    private JPanel panel;
    private JLabel primaryLabel;
    private JComboBox<String> optionsComboBox;
    private JLabel buttonOutputLabel;
    private JButton comboBoxSubmitButton;

    private JFrame addBooksWindow;
    private JTextField addBooksTextField;
    private JLabel addBooksErrorLabel;

    private JFrame addBookWindow;
    private JTextField addBookAuthorField;
    private JTextField addBookTitleField;

    private JFrame setToReadWindow;
    private JTextField setToReadAuthorField;
    private JTextField setToReadTitleField;

    private JFrame rateWindow;
    private JTextField rateAuthorField;
    private JTextField rateTitleField;
    private JComboBox<Integer> rateComboBox;

    private JFrame searchWindow;
    private JTextField searchAuthorTitleTextField;
    private JLabel searchAuthorTitleLabel;
    private JComboBox<Integer> searchRatingComboBox;
    private JComboBox<String> searchByComboBox;
    private JLabel searchOutputText;
    private JLabel searchRatingLabel;
    private JLabel searchComboBoxLabel;
    private JButton searchOptionsSubmitButton;
    private JButton searchAuthorTitleSubmitButton;
    private JButton searchRatingSubmitButton;
    private JLabel searchListOutputLabel;

    private JFrame getBooksWindow;
    private JComboBox<String> getBooksComboBox;
    private JLabel getBooksListOutputLabel;

    // Main method
    public static void main(String[] args) {
        MyLibraryGUI view = new MyLibraryGUI();
        view.start();
        view.setVisible(true);
    }

    /*
        Constructor for the GUI
     */
    public MyLibraryGUI() {
        controller = new LibraryCollectionController();
        setUp();
    }

    /*
        Sets up the GUI
     */
    private void setUp() {
        // Sets the size of the frame
        this.setSize(600, 600);

        // Sets up the label which will be situated at the top of the window, explaining how
        // to use the GUI
        String textAtTop = "<html>---------------------------<br>";
        textAtTop += "- WELCOME TO YOUR LIBRARY -<br>";
        textAtTop += "---------------------------<br><br>";
        textAtTop += "Enter one of the following commands in the text box,<br>";
        textAtTop += "then press the 'Enter' button to carry out the command<br><br>";
        textAtTop += "search: find a book<br>";
        textAtTop += "addBook: add a book<br>";
        textAtTop += "setToRead: update a book to read<br>";
        textAtTop += "rate: rate a book<br>";
        textAtTop += "getBooks: retrieve and display a list of books<br>";
        textAtTop += "suggestRead: choose a random book from library<br>";
        textAtTop += "addBooks: add files to your library<br><br>";
        textAtTop += "When done, simply exit out of the window<br>";
        primaryLabel = new JLabel(textAtTop, JLabel.CENTER);
        primaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Nonsense numbers for now- adjust as necessary
        primaryLabel.setSize(350, 450);
        this.add(primaryLabel, BorderLayout.NORTH);

        // Sets up the label associated with the output of the button for the combo box
        // Note that we currently have arbitrary size for this button
        // Also need to figure out how to place well within the window
        buttonOutputLabel = new JLabel("", SwingConstants.CENTER);
        buttonOutputLabel.setSize(350, 100);
        buttonOutputLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        buttonOutputLabel.setForeground(Color.RED);

        // Adds the panel
        panel = new JPanel(new BorderLayout());
        this.add(panel);
        panel.add(buttonOutputLabel, BorderLayout.SOUTH);

        // Adds a window listener for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    /*
        Sets up the necessary text fields 
     */
    private void start() {
        // Sets up the combo box, with all of the possible options that the user can select from
        String[] optionsArray = {"", "search", "addBook", "setToRead", "rate", "getBooks", "suggestRead", "addBooks"};
        optionsComboBox = new JComboBox<String>(optionsArray);

        // Adds this combo box to the panel, along with a label to its left prompting the user to select
        // one of the options within the combo box
        JPanel submitPanel = new JPanel();
        JLabel comboLabel = new JLabel("Select from the possible set of actions: ");
        submitPanel.add(comboLabel);
        submitPanel.add(optionsComboBox);

        // Sets up the button associated with this combo box, which will be pressed when the user wants 
        // to submit their current selection in the combo box
        comboBoxSubmitButton = new JButton("Submit");
        comboBoxSubmitButton.setActionCommand("submitted");
        comboBoxSubmitButton.addActionListener(new ButtonListener());
        submitPanel.add(comboBoxSubmitButton);
        panel.add(submitPanel, BorderLayout.CENTER);
    }

    /*
        Button listener
     */
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("submitted")) {
                String commandType = (String) optionsComboBox.getSelectedItem();
                commandType = commandType.toLowerCase();

                // Checks to see whether the user selected an appropriate command
                if (! (commandType.equals("search") || commandType.equals("addbook") || commandType.equals("settoread")
                        || commandType.equals("rate") || commandType.equals("getbooks") || commandType.equals("suggestread")
                        || commandType.equals("addbooks"))) {
                    // If not, then we inform the user of this, and do nothing
                    buttonOutputLabel.setText("Please select one of the specified commands");
                } else {
                    // If it is one of our supported commands, then we set our button output label to
                    // be blank
                    buttonOutputLabel.setText("");

                    // We then call the appropriate helper method for the particular task
                    if (commandType.equals("search")) {
                        searchHelper();
                    } else if (commandType.equals("getbooks")) {
                        getBooksHelper();
                    } else if (commandType.equals("addbook")) {
                        addBookHelper();
                    } else if (commandType.equals("settoread")) {
                        setToReadHelper();
                    } else if (commandType.equals("rate")) {
                        rateHelper();
                    } else if (commandType.equals("suggestread")) {
                        suggestReadHelper();
                    } else {
                        addBooksHelper();
                    }
                }
            } else if (command.equals("addBooksSubmit")) {
                // Gets the current text from the text field
                String curText = addBooksTextField.getText();

                // Checks to see whether this file exists and is readable
                File givenFile = new File(curText);
                if (! (givenFile.exists() && givenFile.canRead())) {
                    addBooksErrorLabel.setText("Error: given file cannot be read. Please try again");
                } else {
                    // Otherwise, we make the error label blank, and read the books from the given
                    // file into our library collection
                    addBooksErrorLabel.setText("");
                    controller.cAddBooksFromFile(curText);

                    // We then close the current window, since the job is done
                    addBooksWindow.dispose();

                    // We also give the user a message that the books were added successfully
                    buttonOutputLabel.setText("Books added successfully from file");
                }
            } else if (command.equals("addBookSubmit")) {
                // Gets the current text from our two text fields
                String author = addBookAuthorField.getText();
                String title = addBookTitleField.getText();

                // Attempts to add the corresponding book to the library
                if (!controller.cAlreadyInCollection(title, author)) {
                    // If the book is not already in the library, we add it to the library, close
                    // the window, and inform the user that the book was successfully added
                    controller.cAddBook(title, author);
                    addBookWindow.dispose();
                    buttonOutputLabel.setText("Book added successfully");
                }  else {
                    // If the book has already been added to the library, then we also close the 
                    // window, but give the user an error message
                    addBookWindow.dispose();
                    buttonOutputLabel.setText("Book has already been added to the library");
                }
            } else if (command.equals("setToReadSubmit")) {
                // Gets the current text from our two text fields
                String author = setToReadAuthorField.getText();
                String title = setToReadTitleField.getText();

                // Checks to see whether the book is actually in the library
                if (!controller.cAlreadyInCollection(title, author)) {
                    // If not, then we close the window and inform the user of this
                    setToReadWindow.dispose();
                    buttonOutputLabel.setText("Given book is not in current library collection");
                } else {
                    // Otherwise, we set the book to read, then inform the user that this was done 
                    // successfully
                    controller.cSetToRead(title, author);
                    setToReadWindow.dispose();
                    buttonOutputLabel.setText("Book was successfully set to read");
                }
            } else if (command.equals("rateSubmit")) {
                // Gets the relevant pieces of text for the title and author, and the rating
                String title = rateTitleField.getText();
                String author = rateAuthorField.getText();
                int rating = (Integer) rateComboBox.getSelectedItem();

                // Checks to see whether the given book is actually in the collection
                if (!controller.cAlreadyInCollection(title, author)) {
                    // If not, then we close the window and give the user an error message
                    rateWindow.dispose();
                    buttonOutputLabel.setText("Given book is not in current library collection");
                } else {
                    // Otherwise, we set the rating of the given book, and tell the user that this 
                    // was done successfully
                    controller.cUpdateBookRating(title, author, rating);
                    buttonOutputLabel.setText("Rating was successfully updated");

                    // We then get rid of the rate window, since it is no longer needed
                    rateWindow.dispose();
                }
            } else if (command.equals("searchOptionsSubmit")) {
                // Gets the current option selected by the user, and converts to lowercase for simplicity
                String curOption = (String) searchByComboBox.getSelectedItem();
                curOption = curOption.toLowerCase();

                // No matter what, the options combo box should disappear, as should its associated label and
                // submit button
                searchByComboBox.setVisible(false);
                searchComboBoxLabel.setText("");
                searchOptionsSubmitButton.setVisible(false);

                // Updates the view according to the option selected by the user
                if (curOption.equals("author")) {
                    // Sets the label for the text field to ask for the author
                    searchAuthorTitleLabel.setText("Enter the author to search for: ");

                    // Makes the text field and submit button visible
                    searchAuthorTitleTextField.setVisible(true);
                    searchAuthorTitleSubmitButton.setVisible(true);
                } else if (curOption.equals("title")) {
                    // Sets the label for the text field to ask for the title
                    searchAuthorTitleLabel.setText("Enter the title to search for: ");

                    // Makes the text field and submit button visible
                    searchAuthorTitleTextField.setVisible(true);
                    searchAuthorTitleSubmitButton.setVisible(true);
                } else if (curOption.equals("rating")) {
                    // Makes the label, combo box, and submit button visible
                    searchRatingLabel.setText("Select the rating to search for: ");
                    searchRatingComboBox.setVisible(true);
                    searchRatingSubmitButton.setVisible(true);
                }
            } else if (command.equals("searchAuthorTitleSubmit")) {
                // First, determine whether we're looking for the title or author
                String searchBy = (String) searchByComboBox.getSelectedItem();
                searchBy = searchBy.toLowerCase();

                // Sets the output labels to be blank
                searchOutputText.setText("");
                searchListOutputLabel.setText("");

                // Then, we get the ArrayList of desired books
                ArrayList<Book> ourBooks;
                if (searchBy.equals("author")) {
                    // Gets the given author
                    String author = searchAuthorTitleTextField.getText();

                    // Gets all of the books associated with this author
                    ourBooks = controller.cGetBooksWithAuthor(author);
                } else {
                    // Gets the given title
                    String title = searchAuthorTitleTextField.getText();

                    // Gets all of the books associated with this title
                    ourBooks = controller.cGetBooksWithTitle(title);
                }

                // Checks to see whether we actually obtained any books
                if (ourBooks.size() == 0) {
                    // If not, then we inform the user that there were no books matching their search
                    searchOutputText.setText("No books matching your search :(");
                } else {
                    // Otherwise, we add these books to our scrollable output label
                    String outputLabelText = "<html>";
                    for (int i = 0; i < ourBooks.size(); i++) {
                        outputLabelText += ourBooks.get(i).toString() + "<br>";
                    }
                    searchListOutputLabel.setText(outputLabelText);
                }
            } else if (command.equals("searchRatingSubmit")) {
                // First, we make the output labels empty
                searchOutputText.setText("");
                searchListOutputLabel.setText("");

                // We then get the rating to search for
                int rating = (Integer) searchRatingComboBox.getSelectedItem();

                // All books with this rating are obtained
                ArrayList<Book> ourBooks = controller.cGetBooksWithRating(rating);

                // Checks to see whether we actually found any books
                if (ourBooks.size() == 0) {
                    // If not, then we inform the user that there were no books matching their search
                    searchOutputText.setText("No books matching your search :(");
                } else {
                    // Otherwise, we add these books to our scrollable output label
                    String outputLabelText = "<html>";
                    for (int i = 0; i < ourBooks.size(); i++) {
                        outputLabelText += ourBooks.get(i).toString() + "<br>";
                    }
                    searchListOutputLabel.setText(outputLabelText);
                }
            } else if (command.equals("getBooksSubmit")) {
                // First, we get the selected item from the combo box
                String getBooksOption = (String) getBooksComboBox.getSelectedItem();

                // We clear out whatever text is currently in the output label
                getBooksListOutputLabel.setText("");

                // We then determine how to retrieve our list of books
                ArrayList<Book> ourBooks;
                if (getBooksOption.equals("title")) {
                    TypeSort enumSearchType = TypeSort.TITLE;
                    ourBooks = controller.cGetSortedCollection(enumSearchType);
                } else if (getBooksOption.equals("author")) {
                    TypeSort enumSearchType = TypeSort.AUTHOR;
                    ourBooks = controller.cGetSortedCollection(enumSearchType);
                } else if (getBooksOption.equals("read")) {
                    ourBooks = controller.cAllReadBooks();
                } else {
                    ourBooks = controller.cAllUnreadBooks();
                }

                // Finally, we check to see whether the retrieved list of books is empty
                if (ourBooks.size() == 0) {
                    // If so, then we inform the user that there were no books matching their request
                    getBooksListOutputLabel.setText("No books matching your request :(");
                } else {
                    // Otherwise, we provide the user with a list of books matching their request
                    String booksToDisplay = "<html>";
                    for (int i = 0; i < ourBooks.size(); i++) {
                        booksToDisplay += ourBooks.get(i).toString() + "<br>";
                    }
                    getBooksListOutputLabel.setText(booksToDisplay);
                }
            }
        }
    }

    /*
        Helper method for the addBooks functionality
     */
    private void addBooksHelper() {
        // Creates a new window, which will be used for the addBooks functionality
        addBooksWindow = new JFrame("addBooks Window");
        addBooksWindow.setSize(500, 300);
        addBooksWindow.setVisible(true);

        // Constructs a panel, which will be used to control the layout
        JPanel addBooksPanel = new JPanel(new BorderLayout());
        addBooksWindow.add(addBooksPanel);

        // Separate panel containing just the text field and button
        JPanel textPanel = new JPanel();

        // Constructs a label for the below text field
        JLabel addBooksTextFieldLabel = new JLabel("Enter file name here:", SwingConstants.CENTER);
        textPanel.add(addBooksTextFieldLabel);


        // Constructs a text field, which will be used to get input from the user
        addBooksTextField = new JTextField("", 20);
        textPanel.add(addBooksTextField);
        addBooksTextField.setSize(300, 100);

        // Constructs an error label for the addBooks text field entry
        addBooksErrorLabel = new JLabel("", SwingConstants.CENTER);
        addBooksErrorLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addBooksErrorLabel.setForeground(Color.RED);
        addBooksPanel.add(addBooksErrorLabel, BorderLayout.SOUTH);

        // Constructs a button, which will be used to determine when to get text from the 
        // text field
        JButton textFieldButton = new JButton("Submit");
        textFieldButton.setActionCommand("addBooksSubmit");
        textFieldButton.addActionListener(new ButtonListener());
        textPanel.add(textFieldButton);

        // Adds our separate panel to the overall panel
        addBooksPanel.add(textPanel, BorderLayout.NORTH);

    }

    /*
        Helper method for the suggestRead functionality
     */
    private void suggestReadHelper() {
        // Checks to see whether the library collection currently empty
        if (controller.cIsEmpty()) {
            // If so, we inform the user of this via the button output label
            buttonOutputLabel.setText("No books currently in the collection");
        } else {
            // Otherwise, we try to get a random unread book from the library
            Book randBook = controller.cGetRandomBook();
            if (randBook == null) {
                // If there are no unread books, we inform the user of this
                buttonOutputLabel.setText("No unread books are currently in the collection");
            } else {
                // Otherwise, we provide the text for this book
                buttonOutputLabel.setText(randBook.getTitle() + " by " + randBook.getAuthor());
            }
        }
    }

    /*
        Helper method for the search functionality
        BROKEN! NEED TO MAKE IT SO THAT THINGS ARE ACTUALLY VISIBLE
     */
    private void searchHelper() {
        // Constructs a new window, which will be used to aid with the search functionality
        searchWindow = new JFrame("search Window");
        searchWindow.setSize(800, 800);
        searchWindow.setVisible(true);

        // Constructs a panel, which will be used to organize components within the window
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchWindow.add(searchPanel);

        // Adds a label containing information about how to actually use this functionality
        // to our primary panel
        String searchInfoString = "<html>AUTHOR: Find books with a particular author<br>";
        searchInfoString += "TITLE: Find books with a particular title<br>";
        searchInfoString += "RATING: Find books with a particular rating<br>";
        JLabel searchInfoLabel = new JLabel(searchInfoString, SwingConstants.CENTER);
        searchPanel.add(searchInfoLabel, BorderLayout.NORTH);

        // Constructs a secondary panel, which will be used to organize our text fields,
        // combo boxes, and labels
        JPanel searchTextPanel = new JPanel(new GridLayout(3, 3, 10, 10));


        // Sets up the main combo box and associated label, which will allow the user to determine
        // how they want to perform their search, and adds these to our grid panel
        String[] searchOptionsArray = {"Title", "Author", "Rating"};
        searchByComboBox = new JComboBox<String>(searchOptionsArray);
        searchByComboBox.setVisible(true);
        searchComboBoxLabel = new JLabel("Select the type of searching: ", SwingConstants.RIGHT);
        searchComboBoxLabel.setVisible(true);
        searchTextPanel.add(searchComboBoxLabel);
        searchTextPanel.add(searchByComboBox);

        // Constructs a submit button for the search options combo box, and adds this to our
        // panel
        searchOptionsSubmitButton = new JButton("Submit");
        searchOptionsSubmitButton.setActionCommand("searchOptionsSubmit");
        searchOptionsSubmitButton.addActionListener(new ButtonListener());
        searchTextPanel.add(searchOptionsSubmitButton);

        // Fills in the rest of our grid with the fields that we may need, depending on the
        // search method given by the user. Note that we also add in additional buttons for submitting 
        // the additional information from the user
        searchAuthorTitleTextField = new JTextField("", 25);
        searchAuthorTitleTextField.setVisible(false);
        searchAuthorTitleLabel = new JLabel("", SwingConstants.RIGHT);
        searchAuthorTitleSubmitButton = new JButton("Submit");
        searchAuthorTitleSubmitButton.setActionCommand("searchAuthorTitleSubmit");
        searchAuthorTitleSubmitButton.addActionListener(new ButtonListener());
        searchAuthorTitleSubmitButton.setVisible(false);
        searchTextPanel.add(searchAuthorTitleLabel);
        searchTextPanel.add(searchAuthorTitleTextField);
        searchTextPanel.add(searchAuthorTitleSubmitButton);

        searchRatingLabel = new JLabel("", SwingConstants.RIGHT);
        Integer[] ratingArray = {Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5)};
        searchRatingComboBox = new JComboBox<Integer>(ratingArray);
        searchRatingComboBox.setVisible(false);
        searchRatingSubmitButton = new JButton("Submit");
        searchRatingSubmitButton.setActionCommand("searchRatingSubmit");
        searchRatingSubmitButton.addActionListener(new ButtonListener());
        searchRatingSubmitButton.setVisible(false);
        searchTextPanel.add(searchRatingLabel);
        searchTextPanel.add(searchRatingComboBox);
        searchTextPanel.add(searchRatingSubmitButton);

        // Adds our grid panel to our overall panel
        searchTextPanel.setVisible(true);
        searchPanel.add(searchTextPanel, BorderLayout.CENTER);

        // Constructs a label which will be additionally used to tell the user if the given search was unsuccessful
        searchOutputText = new JLabel("", SwingConstants.CENTER);
        searchOutputText.setFont(new Font("Times New Roman", Font.BOLD, 20));
        searchOutputText.setForeground(Color.RED);
        searchPanel.add(searchOutputText, BorderLayout.EAST);

        // Constructs a scrollable panel, which will be used to present the outputted list of books
        JPanel booksOutputPanel = new JPanel();
        JScrollPane booksOutputScrollPanel = new JScrollPane(booksOutputPanel);
        booksOutputScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        searchPanel.add(booksOutputPanel, BorderLayout.SOUTH);

        // Adds the label which will actually present the books to this panel
        searchListOutputLabel = new JLabel("", SwingConstants.CENTER);
        booksOutputPanel.add(searchListOutputLabel);
    }

    /*
        Helper method for the getBooks functionality
        BROKEN! HOW TO MAKE IT SO THAT EVERYTHING SHOWS IN THE WINDOW?
     */
    private void getBooksHelper() {
        // Constructs a new window, which will be used to aid with the getBooks functionality
        getBooksWindow = new JFrame("getBooks Window");
        getBooksWindow.setSize(800, 800);
        getBooksWindow.setVisible(true);

        // Constructs a panel, which will be used to organize components within the window
        JPanel getBooksPanel = new JPanel(new BorderLayout());
        getBooksWindow.add(getBooksPanel);

        // Constructs a secondary, inner panel, which will be used to organize the label, combo
        // box, and submit button
        JPanel getBooksInputPanel = new JPanel();
        getBooksPanel.add(getBooksInputPanel, BorderLayout.CENTER);

        // Creates a label describing the getBooks functionality of the library collection
        String getBooksInfoString = "<html>Retrieve a list of... <br>";
        getBooksInfoString += "title: all books sorted by title<br>";
        getBooksInfoString += "author: all books sorted by author<br>";
        getBooksInfoString += "read: all read books<br>";
        getBooksInfoString += "unread: all unread books<br>";
        JLabel getBooksInfoLabel = new JLabel(getBooksInfoString, SwingConstants.CENTER);
        getBooksPanel.add(getBooksInfoLabel, BorderLayout.NORTH);

        // Constructs our combo box label, combo box, and submit button, adding these to our inner panel
        JLabel getBooksComboLabel = new JLabel("Select from one of these options: ");
        String[] getBooksOptions = {"title", "author", "read", "unread"};
        getBooksComboBox = new JComboBox<String>(getBooksOptions);
        JButton getBooksSubmitButton = new JButton("Submit");
        getBooksSubmitButton.setActionCommand("getBooksSubmit");
        getBooksSubmitButton.addActionListener(new ButtonListener());
        
        getBooksInputPanel.add(getBooksComboLabel);
        getBooksInputPanel.add(getBooksComboBox);
        getBooksInputPanel.add(getBooksSubmitButton);

        // Sets up our scrollable label, which will contain the output of the getBooks functionality
        getBooksListOutputLabel = new JLabel("");
        JPanel getBooksOutputPanel = new JPanel();
        JScrollPane getBooksOutputScrollPanel = new JScrollPane(getBooksOutputPanel);
        getBooksOutputScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getBooksPanel.add(getBooksOutputPanel, BorderLayout.SOUTH);
        getBooksOutputPanel.add(getBooksListOutputLabel);
    }

    /*
        Helper method for the addBook functionality
     */
    private void addBookHelper() {
        // Constructs a new window, which will be used to aid with the addBook functionality
        addBookWindow = new JFrame("addBook Window");
        addBookWindow.setSize(1000, 150);
        addBookWindow.setVisible(true);

        // Constructs a panel, which will be used to control the organization of elements
        // within the window
        JPanel addBookPanel = new JPanel();
        addBookWindow.add(addBookPanel);

        // Constructs a secondary panel, which will only contain the text fields and labels
        JPanel addBookTextPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Sets up the text fields, which will be used to get user input
        addBookAuthorField = new JTextField("", 25);
        addBookAuthorField.setSize(25, 30);
        addBookTitleField = new JTextField("", 25);
        addBookAuthorField.setSize(25, 30);

        // Sets up the labels for the text fields
        JLabel addBookAuthorLabel = new JLabel("Enter the author here: ", SwingConstants.RIGHT);
        addBookAuthorLabel.setSize(20, 30);
        JLabel addBookTitleLabel = new JLabel("Enter the title here: ", SwingConstants.RIGHT);
        addBookTitleLabel.setSize(20, 30);

        // Adds all of these to the text panel
        addBookTextPanel.add(addBookTitleLabel);
        addBookTextPanel.add(addBookTitleField);
        addBookTextPanel.add(addBookAuthorLabel);
        addBookTextPanel.add(addBookAuthorField);

        // Adds this panel to the overall panel 
        addBookPanel.add(addBookTextPanel);

        // Constructs a button for actually submitting the input
        JButton addBookTextButton = new JButton("Submit");
        addBookTextButton.setActionCommand("addBookSubmit");
        addBookTextButton.addActionListener(new ButtonListener());
        addBookPanel.add(addBookTextButton);
    }

    /*
        Helper method for the setToRead functionality
     */
    private void setToReadHelper() {
        // Constructs a new window, which will be used to aid with the setToRead functionality
        setToReadWindow = new JFrame("setToRead Window");
        setToReadWindow.setSize(1000, 150);
        setToReadWindow.setVisible(true);

        // Constructs a panel, which will be used to control the organization of elements
        // within the window
        JPanel setToReadPanel = new JPanel();
        setToReadWindow.add(setToReadPanel);

        // Constructs a secondary panel, which will only contain the text fields and labels
        JPanel setToReadTextPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Sets up the text fields, which will be used to get user input
        setToReadAuthorField = new JTextField("", 25);
        setToReadAuthorField.setSize(25, 30);
        setToReadTitleField = new JTextField("", 25);
        setToReadAuthorField.setSize(25, 30);

        // Sets up the labels for the text fields
        JLabel setToReadAuthorLabel = new JLabel("Enter the author here: ", SwingConstants.RIGHT);
        setToReadAuthorLabel.setSize(20, 30);
        JLabel setToReadTitleLabel = new JLabel("Enter the title here: ", SwingConstants.RIGHT);
        setToReadTitleLabel.setSize(20, 30);

        // Adds all of these to the text panel
        setToReadTextPanel.add(setToReadTitleLabel);
        setToReadTextPanel.add(setToReadTitleField);
        setToReadTextPanel.add(setToReadAuthorLabel);
        setToReadTextPanel.add(setToReadAuthorField);

        // Adds this panel to the overall panel 
        setToReadPanel.add(setToReadTextPanel);

        // Constructs a button for actually submitting the input
        JButton setToReadTextButton = new JButton("Submit");
        setToReadTextButton.setActionCommand("setToReadSubmit");
        setToReadTextButton.addActionListener(new ButtonListener());
        setToReadPanel.add(setToReadTextButton);
    }

    /*
        Helper method for the rate functionality
     */
    private void rateHelper() {
        // Constructs a new window, which will be used to aid with the rate functionality
        rateWindow = new JFrame("rate Window");
        rateWindow.setSize(1000, 150);
        rateWindow.setVisible(true);

        // Constructs a panel, which will be used to control the organization of elements
        // within the window
        JPanel ratePanel = new JPanel();
        rateWindow.add(ratePanel);

        // Constructs a secondary panel, which will only contain the text fields and labels,
        // along with our combo box
        JPanel rateTextPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Sets up the text fields, which will be used to get user input
        rateAuthorField = new JTextField("", 25);
        rateAuthorField.setSize(25, 30);
        rateTitleField = new JTextField("", 25);
        rateAuthorField.setSize(25, 30);

        // Sets up the labels for the text fields
        JLabel rateAuthorLabel = new JLabel("Enter the author here: ", SwingConstants.RIGHT);
        rateAuthorLabel.setSize(20, 30);
        JLabel rateTitleLabel = new JLabel("Enter the title here: ", SwingConstants.RIGHT);
        rateTitleLabel.setSize(20, 30);

        // Sets up the ComboBox and its associated label for selecting the rating
        Integer[] intArray = new Integer[5];
        intArray[0] = Integer.valueOf(1);
        intArray[1] = Integer.valueOf(2);
        intArray[2] = Integer.valueOf(3);
        intArray[3] = Integer.valueOf(4);
        intArray[4] = Integer.valueOf(5);
        rateComboBox = new JComboBox<Integer>(intArray);
        JLabel rateComboBoxLabel = new JLabel("Select the new rating: ", SwingConstants.RIGHT);

        // Adds all of these to the text panel
        rateTextPanel.add(rateTitleLabel);
        rateTextPanel.add(rateTitleField);
        rateTextPanel.add(rateAuthorLabel);
        rateTextPanel.add(rateAuthorField);
        rateTextPanel.add(rateComboBoxLabel);
        rateTextPanel.add(rateComboBox);

        // Adds this panel to the overall panel 
        ratePanel.add(rateTextPanel);

        // Constructs a button for actually submitting the input
        JButton rateTextButton = new JButton("Submit");
        rateTextButton.setActionCommand("rateSubmit");
        rateTextButton.addActionListener(new ButtonListener());
        ratePanel.add(rateTextButton);
    }
}
