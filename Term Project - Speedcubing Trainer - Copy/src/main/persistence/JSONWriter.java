package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.UserBase;

public class JSONWriter {

    // REQUIRES: the source is a relative path leading to the data folder in this
    // directory, where the data will be stored
    // EFFECTS: constructs a new writer to save the data at the given location
    public JSONWriter(String source) {

    }

    // MODIFIES: this
    // EFFECTS: Opens wrter, throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {

    }

    // MODIFIES: UsersData.json
    // EFFECTS: Stores the current state of the UserBase by writing to the
    // UsersData.json file
    public void write(UserBase currenetUserBase) {

    }
    
    // MODIFIES: this
    // EFFECTS: closes the writer 
    public void close(){
        
    }
}
