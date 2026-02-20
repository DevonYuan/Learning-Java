package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.UserBase;

public class JSONReader {
    private String source; 

    // REQUIRES: the source is a relative path leading to the data folder in this
    // directory, where the data will be stored
    // EFFECTS: constructs a new reader to read the data at the given location
    public JSONReader(String source) {
        this.source = source; 
    }

    // EFFECTS: Returns a userbase whose state is the same as the last time the data
    // was saved upon closing the program, and throws an IOException if an error
    // occurs while trying to read the file
    public UserBase read() throws IOException {
        return null; 
    }
}
