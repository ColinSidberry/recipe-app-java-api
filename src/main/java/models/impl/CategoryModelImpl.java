package models.impl;

import models.interfaces.CategoryModel;

/**
 * Represents a recipe category in the system.
 */
public class CategoryModelImpl implements CategoryModel {
    private String id;
    private String name;
    private String description;

    /**
     * Default constructor.
     */
    public CategoryModelImpl() {}

    /**
     * Constructor with name and description.
     *
     * @param name The category name
     * @param description The category description
     */
    public CategoryModelImpl(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Constructor with all fields.
     *
     * @param id The category ID
     * @param name The category name
     * @param description The category description
     */
    public CategoryModelImpl(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
