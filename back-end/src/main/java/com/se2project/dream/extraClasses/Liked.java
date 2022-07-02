package com.se2project.dream.extraClasses;

import com.se2project.dream.entity.Knowledge;

/**
 * This class is used as help during the process
 * of keeping track of the liking of other entities
 */
public class Liked {
    private boolean liked;
    private Object entity;

    /**Constructor
     * @param liked  boolean that represents if the entity has been liked or not
     * @param obj entity that can be liked
     * */
    public Liked(boolean liked, Object obj) {
        this.liked = liked;
        this.entity = obj;
    }


    /**@return whether an entity is liked or not*/
    public boolean isLiked() {
        return liked;
    }

    /**Set the value pf liked parameter @param liked*/
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    /**@return the Entity we are considering*/
    public Object getEntity() {
        return entity;
    }

    /**Set the Entity @param onj*/
    public void setEntity(Object obj) {
        this.entity = obj;
    }
}
