package org.gymtrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WgerTranslation {
    private String name;
    private  int language;

    public WgerTranslation() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLanguage() {
        return  language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }
}
