package com.helloworld.hwlocator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationsResponse {

    private List<LocationObject> locations = new ArrayList<LocationObject>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The locationObjects
     */
    public List<LocationObject> getLocationObjects() {
        return locations;
    }

    /**
     *
     * @param locationObjects
     * The locationObjects
     */
    public void setLocationObjects(List<LocationObject> locationObjects) {
        this.locations = locationObjects;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}