package persistence;

import org.json.JSONObject;

// Note: Structured from JsonSerialization example, much of my methods from JsonReader and JsonWriter are in some way
// modified from the example.

// Ensures all model classes implement toJson.
public interface Writable {
    // Effect: returns as JsonObject
    JSONObject toJson();

}
