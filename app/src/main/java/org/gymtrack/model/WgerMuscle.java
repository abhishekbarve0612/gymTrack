package org.gymtrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WgerMuscle {

    private int id;

    @JsonProperty("name_en")
    private String nameEn;

    @JsonProperty("is_front")
    private boolean isFront;

    public WgerMuscle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("name_en")
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @JsonProperty("is_front")
    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }

    @Override
    public String toString() {
        return nameEn;
    }
}
