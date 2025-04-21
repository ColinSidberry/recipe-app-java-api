package models.impl;

import models.interfaces.IngredientModel;

/**
 * Represents an ingredient that can be used in recipes.
 */
public class IngredientModelImpl implements IngredientModel {
    private String id;
    private String name;
    private String unit;

    /**
     * Default constructor.
     */
    public IngredientModelImpl() {}

    /**
     * Constructor with name and unit.
     *
     * @param name The ingredient name
     * @param unit The unit of measurement (e.g., "cup", "gram", "piece")
     */
    public IngredientModelImpl(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    /**
     * Constructor with all fields.
     *
     * @param id The ingredient ID
     * @param name The ingredient name
     * @param unit The unit of measurement
     */
    public IngredientModelImpl(String id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
