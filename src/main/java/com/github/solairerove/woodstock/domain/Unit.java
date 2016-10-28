package com.github.solairerove.woodstock.domain;


import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NodeEntity(label = "Unit")
public class Unit implements Serializable {

    @GraphId
    private Long id;

    private String createdDate;

    private String updatedDate;

    private String label;

    @Relationship(type = "HAS_IN", direction = Relationship.INCOMING)
    private List<Category> categories;

    public List<Category> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
        }

        return categories;
    }
}