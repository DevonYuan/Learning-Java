package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.UserBase;

// I modelled this class from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// (As per the project requirements, this is allowed as long as a citation is given)

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // REQUIRES: the source is a relative path leading to the data folder in this
    // directory, where the data will be stored
    // EFFECTS: constructs a new writer to save the data at the given location
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens wrter, throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: UsersData.json
    // EFFECTS: Stores the current state of the UserBase by writing to the
    // UsersData.json file
    public void write(UserBase currentUserBase) {
        JSONObject json = currentUserBase.toJson();
        saveToFile(json.toString(TAB));
    }
    
    // MODIFIES: this
    // EFFECTS: closes the writer 
    public void close(){
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
