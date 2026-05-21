package org.gymtrack.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WgerExerciseInfo {

    private int id;
    private String name;
    private String description;
    private WgerCategory category;
    private List<WgerMuscle> muscles;

    @JsonProperty("muscles_secondary")
    private List<WgerMuscle> musclesSecondary;

    private List<WgerEquipment> equipment;

    public WgerExerciseInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<WgerEquipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<WgerEquipment> equipment) {
        this.equipment = equipment;
    }

    public int getTotalMuscleCount() {
        int count = 0;
        if (muscles != null)
            count += muscles.size();
        if (musclesSecondary != null)
            count += musclesSecondary.size();
        return count;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d muscles", name, (category != null ? category.getName() : "??"),
                getTotalMuscleCount());
    }
}
