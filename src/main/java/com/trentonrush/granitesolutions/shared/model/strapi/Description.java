package com.trentonrush.granitesolutions.shared.model.strapi;

import java.util.List;

public class Description {
    private String type;
    private List<Child> children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
