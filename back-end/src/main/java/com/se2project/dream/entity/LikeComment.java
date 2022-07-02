package com.se2project.dream.entity;

import javax.persistence.*;

/***
 * Abstraction of LikeComment.
 */
@Entity
public class LikeComment {
    private @Id @GeneratedValue Long id;

    /**
     * @ see Comment foreign key
     */
    @ManyToOne
    @PrimaryKeyJoinColumn(name="comment_fk", referencedColumnName="comment_id")
    private Comment comment;

    /**
     * @ see Farmer foreign key
     */
    @ManyToOne
    @PrimaryKeyJoinColumn(name="farmer_fk", referencedColumnName="farmer_id")
    private Farmer farmer;

    /**Constructor*/
    public LikeComment(){}

    /**Constructor
     * @param comment the comment to which the like refers
     * @param farmer the farmer to which the like refers
     * */
    public LikeComment(Comment comment, Farmer farmer) {
        this.comment = comment;
        this.farmer = farmer;
    }

    /**@return likeComment's Id*/
    public Long getId() {
        return id;
    }

    /**@return the comment to which the likeComment refers*/
    public Long getComment() {
        return comment.getCommentId();
    }

    /**Set the comment to which the likeComment refers*/
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**@return the farmer to which the likeComment refers*/
    public Long getFarmer() {
        return farmer.getId();
    }

    /**Set the farmer to which the likeComment refers*/
    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
}
