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

import java.awt.event.*;
import javax.swing.*;

public class MyLibraryGUI extends JFrame {
    private LibraryCollectionController controller;
    private JPanel panel;
    private JTextField primaryTextField;
    private JLabel primaryLabel;
    private JLabel buttonOutputText;

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
        String textAtTop = "---------------------------\n";
        textAtTop += "- WELCOME TO YOUR LIBRARY -\n";
        textAtTop += "---------------------------\n\n";
        textAtTop += "Enter one of the following commands in the text box,\n";
        textAtTop += "then press the 'Enter' button to carry out the command\n\n";
        textAtTop += "search: find a book\n";
        textAtTop += "addBook: add a book\n";
        textAtTop += "setToRead: update a book to read\n";
        textAtTop += "rate: rate a book\n";
        textAtTop += "getBooks: retrieve and display a list of books\n";
        textAtTop += "suggestRead: choose a random book from library\n";
        textAtTop += "addBooks: add files to your library\n\n";
        textAtTop += "When done, simply exit out of the window\n";
        primaryLabel = new JLabel(textAtTop, JLabel.CENTER);
        // Nonsense numbers for now- adjust as necessary
        primaryLabel.setSize(300, 400);
        this.add(primaryLabel);

        // Sets up the text field for the text that will be displayed when the user presses 
        // the "Enter" button
        buttonOutputText = new JLabel("", JLabel.RIGHT);
        buttonOutputText.setSize(100, 100);
        this.add(buttonOutputText);

        // Adds the panel
        panel = new JPanel();
        this.add(panel);

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
        // Sets up the submit button associated with the primary text field
        JButton primaryTextFieldButton = new JButton("Enter");
        primaryTextFieldButton.setActionCommand("entered");
        panel.add(primaryTextFieldButton);


        // Sets up the main text field, which will have a width of 15, and which will be situated
        // at the center of our panel
        primaryTextField = new JTextField("Enter library command here", 15);

        panel.add(primaryTextField);
    }

    /*
        Button listener
     */
    private class primaryTextFieldEnterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("entered")) {
                String commandType = primaryTextField.getText();
                commandType = commandType.toLowerCase();

                // Checks to see whether the given command is one of our 
                if (! (commandType.equals("search") || commandType.equals("addbook") || commandType.equals("settoread")
                        || commandType.equals("rate") || commandType.equals("getbooks") || commandType.equals("suggestread")
                        || commandType.equals("addbooks"))) {
                    // If not, then we inform the user of this, and do nothing
                    buttonOutputText.setText("This is not one of the supported commands. Please try again");
                } else {
                    // If it is one of our supported commands, then we set our button output label to
                    // be blank
                    buttonOutputText.setText("");

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
            }
        }
    }

    /*
        Helper method for the addBooks functionality
     */
    private void addBooksHelper() {
    
    }

    /*
        Helper method for the suggestRead functionality
     */
    private void suggestReadHelper() {

    }

    /*
        Helper method for the search functionality
     */
    private void searchHelper() {

    }

    /*
        Helper method for the getBooks functionality
     */
    private void getBooksHelper() {

    }

    /*
        Helper method for the addBook functionality
     */
    private void addBookHelper() {

    }

    /*
        Helper method for the setToRead functionality
     */
    private void setToReadHelper() {

    }

    /*
        Helper method for the rate functionality
     */
    private void rateHelper() {

    }
}
