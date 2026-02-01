import java.util.concurrent.TimeUnit;
public class Book {
    private final String title;
    private final String author;
    private final int pages;
    private boolean borrowed;
    static int counter;

    private String reference;
    public Book (String author, String title, int pages) {
        this.author = author;
        this.title = title;
        this.pages = pages;
        counter ++;
        borrowed = false;
        reference = configureRefNumber(author);
    }

    public String getTitle () {
        return title;
    }

    public String getAuthor () {
        return author;
    }

    public void setToBorrowed () {
        borrowed = true;
    }

    public void setToReturned () {
        borrowed = false;
    }

    public boolean getBorrowedStatus () {
        return borrowed;
    }

    public void animateStrings (String toAnimate) throws InterruptedException {
        for (int a = 0; a < toAnimate.length(); a++){
            System.out.print(toAnimate.charAt(a));
            TimeUnit.MILLISECONDS.sleep(69);
        }
        System.out.print("\n");
    }

    public void printDetails () throws InterruptedException {
        String animatedPageCount = "Number of Pages: " + pages;
        int lineLength = animatedPageCount.length();
        animateLines(lineLength);
        animateStrings(("Title: " + title));
        animateStrings(("Author: " + author));
        animateStrings(animatedPageCount);
        animateStrings(("Reference Code: " + reference));
        animateLines(lineLength);
    }

    public String configureRefNumber (String author){
        String surName = author.split(" ") [author.split(" ").length - 1].toUpperCase();
        StringBuilder refNumber = new StringBuilder();
        for (int a = 0; a < 3; a++){
            refNumber.append(surName.charAt(a));
        }
        String myNumber = Integer.toString(counter);
        if (myNumber.length() < 3){
            refNumber.append("0".repeat((3 - myNumber.length())));
        }
        for (int a = 0; a < myNumber.length(); a++){
            if (a < 3){
                refNumber.append(myNumber.charAt(a));
            }
        }
        return refNumber.toString();
    }

    public void animateLines (int myLineLength){
        for (int a = 0; a < myLineLength; a++){
            System.out.print("-");
        }
        System.out.print("\n");
    }
}