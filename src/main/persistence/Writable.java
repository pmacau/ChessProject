package persistence;

import org.json.JSONObject;


// Ensures all model classes implement toJson.
public interface Writable {
    // Effect: returns as JsonObject
    JSONObject toJson();

}
