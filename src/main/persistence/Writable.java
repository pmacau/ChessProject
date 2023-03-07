package persistence;

import org.json.JSONObject;

// Note: Structured from JsonSerialization example.

public interface Writable {
    // Effect: returns as JsonObject
    JSONObject toJson();

}
