package com.se2project.dream.extraClasses;

/**
 * Allert class
 */
public class Allert {
    private boolean allert;
    private Object entity;

    public Allert(boolean allert, Object obj) {
        this.allert = allert;
        this.entity = obj;
    }


    /**Constructor*/
    public boolean isAllert() {
        return allert;
    }

    /**Constructor
     * @param allert*/
    public void setAllert(boolean allert) {
        this.allert = allert;
    }

    /**@return the Entity we are considering*/
    public Object getEntity() {
        return entity;
    }

    /**Set the Entity we are considering*/
    public void setEntity(Object obj) {
        this.entity = obj;
    }
}
