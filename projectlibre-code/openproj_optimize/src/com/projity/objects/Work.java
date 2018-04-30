package com.projity.objects;

import java.util.ArrayList;
import java.util.List;

public class Work {
    private String name;
    private List<Work> children = new ArrayList<>();

    public Work(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addWorkToParent(Work child) {
        children.add(child);
    }

    public List<Work> getChildren() {
        return children;
    }

    public void setDependenciesToParent(List<Work> parents) {
        if (parents != null)
            for (Work parent : parents) {
                parent.addWorkToParent(this);
            }
    }
}
