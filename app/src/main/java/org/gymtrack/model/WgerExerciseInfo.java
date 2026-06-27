package org.gymtrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public  class WgerExerciseInfo {

    private String name;
    private List<WgerTranslation> translations;
    private WgerCategory category;
    private List<WgerMuscle> muscles;

    @JsonProperty("muscles_secondary")
    private List<WgerMuscle> musclesSecondary;

    private List<WgerEquipment> equipments;

    public WgerExerciseInfo() {}

    public String getName() {
        if (name != null) return name;
        if (translations != null) {
            for (WgerTranslation t: translations) {
                if (t.getLanguage() == 2) return t.getName(); // 2 -> English
            }
            if (!translations.isEmpty()) return translations.getFirst().getName();
        }
        return null;
    }

    public void setName(String name) { this.name = name; }

    public List<WgerTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<WgerTranslation> translations) {
        this.translations = translations;
    }

    public WgerCategory getCategory() {
        return category;
    }

    public void setCategory(WgerCategory category) {
        this.category = category;
    }

    public List<WgerMuscle> getMuscles() {
        return muscles;
    }

    public void setMuscles(List<WgerMuscle> muscles) {
        this.muscles = muscles;
    }

    public List<WgerMuscle> getMusclesSecondary() {
        return musclesSecondary;
    }

    public void setMusclesSecondary(List<WgerMuscle> musclesSecondary) {
        this.musclesSecondary = musclesSecondary;
    }

    public List<WgerEquipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<WgerEquipment> equipments) {
        this.equipments = equipments;
    }

    public int getTotalMuscleCount() {
        int count = 0;
        if (muscles != null) count += muscles.size();
        if (musclesSecondary != null) count += musclesSecondary.size();
        return count;
    }

    @Override
    public  String toString() {
        return getName() + " ("
                + (category != null ? category.getName() : ":")
                + ") - " + getTotalMuscleCount() + " muscles";
    }
}