import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BookTracker {
    public static void animateText(String toAnimate) throws InterruptedException {
        for (int a = 0; a < toAnimate.length(); a++){
            System.out.print(toAnimate.charAt(a));
            TimeUnit.MILLISECONDS.sleep(10);
        }
        System.out.print("\n");
    }

    public static void animateLine(int myLineLength){
        for (int a = 0; a < myLineLength; a++){
            System.out.print("-");
        }
        System.out.print("\n");
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        boolean running = true;
        ArrayList <Book> library = new ArrayList<>();
        animateText("Welcome to the Book Tracker App!");
        animateText("1. Add books to the library");
        animateText("2. Borrow a book");
        animateText("3. Return a book");
        animateText("4. Access the information of a book");
        animateText("5. See a list of every book");
        animateText("6. Terminate the program");
        while (running){
            int command = input.nextInt();
            if (command == 6){ // Terminates Program
                System.out.println();
                String text = "Terminating . . . ";
                animateText(text);
                animateLine(text.length());
                running = false;
            } else if (command == 5) {
                String display = "Here is every book in the Library: ";
                animateText(display);
                animateLine(display.length());
                for (int a = 0; a < library.size(); a++){
                    System.out.println((a + 1) + ". " + library.get(a).getTitle() + " by " + library.get(a).getAuthor());
                }
                System.out.println();
            } else if (command == 4){
                System.out.println("Which book would you like to know about? ");
                input.nextLine();
                String requestedBook = input.nextLine();
                boolean found = false;
                for (Book book : library) {
                    if (book.getTitle().equals(requestedBook)) {
                        animateText("I found the book! Here is the information: ");
                        book.printDetails();
                        found = true;
                    }
                }
                if (!found){
                    System.out.println("Sorry, we don't have that book.");
                }
            } else if (command == 3){
                System.out.print("Which book would you like to return? ");
                input.nextLine();
                String returnedBook = input.nextLine();
                boolean found = false;
                boolean returned = false;
                for (Book book : library) {
                    if (book.getTitle().equals(returnedBook)) {
                        boolean isBorrowed = book.getBorrowedStatus();
                        found = true;
                        if (isBorrowed) {
                            book.setToReturned();
                            System.out.println("Thank you for returning the book!");
                            returned = true;
                        }
                    }
                }
                if (!found){
                    System.out.println("Sorry, that book is not a part of our library.");
                } else if (!returned) {
                    System.out.println("That book has already been returned.");
                }
            } else if (command == 2){ // Borrows books
                System.out.print("Which book would you like to borrow? ");
                input.nextLine();
                String requestedBook = input.nextLine();
                boolean found = false;
                boolean available = false;
                for (Book book : library) {
                    if (requestedBook.equals(book.getTitle())) {
                        found = true;
                        animateText("I found the book. Let me check if it's available . . . ");
                        if (!book.getBorrowedStatus()) {
                            book.setToBorrowed();
                            animateText("Done! Enjoy reading!");
                            available = true;
                        }
                    }
                }
                if (!found){
                    System.out.println("Sorry, we don't have that book.");
                } else if (!available){
                    System.out.println("Sorry, that book has already been borrowed");
                }
            } else if (command == 1){ // Adds books
                System.out.print("How many books would you like to add? ");
                int numberOfBooks = input.nextInt();
                input.nextLine(); // Weird input issue, but this will fix it
                System.out.println();
                for (int a = 0; a < numberOfBooks; a++){
                    String animate = "Book " + (a + 1) + ":";
                    animateText(animate);
                    for (int b = 0; b < animate.length(); b++){
                        System.out.print("-");
                    }
                    System.out.print("\n");
                    System.out.print("What is the name of the book? "); // Collecting info in a loop
                    String myTitle = input.nextLine();
                    System.out.print("Who is the author of the book? ");
                    String myAuthor = input.nextLine();
                    System.out.print("How many pages does the book have? ");
                    int numberOfPages = input.nextInt();
                    input.nextLine(); // Weird Java issue with input but this fixes it
                    library.add(new Book (myAuthor, myTitle, numberOfPages));
                    animateLine(50);
                }
            }
            System.out.println();
        }
    }
}
