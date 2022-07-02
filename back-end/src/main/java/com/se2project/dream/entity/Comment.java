package com.se2project.dream.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/***
 * Abstraction of a determined Comment. Every Comment has a body (comment),
 * a date and a number of likes.
 */
@Entity
public class Comment {
    private  @Id @GeneratedValue Long commentId;
    @Lob
    @Column(columnDefinition = "TEXT")
    private  String comment;
    private Date date;
    private int likes;

    /**
     * @ see Topic foreign key
     */
    @ManyToOne
    @JoinColumn(name="topic_fk")
    private Topic topic;

    /**
     * @ see Farmer foreign key
     */
    @ManyToOne
    @JoinColumn(name="farmer_fk")
    private Farmer farmer;

    /**
     * one to many relationship with @see LikeComment, a Comment can be liked many times
     */
    @OneToMany(mappedBy="comment", cascade = CascadeType.ALL)
    private Set<LikeComment> farmers;

    /**Constructor*/
    public Comment(){}

    /**Constructor
     * @param farmer the Farmer who wrote the Comment;
     * @param topic the Topic to which the Comment refers;
     * @param comment the body of the Comment
     * */
    public Comment(String comment, Topic topic, Farmer farmer){
        this.comment=comment;
        this.topic=topic;
        this.farmer=farmer;
        this.date= Calendar.getInstance().getTime();
    }

    /**@return commentId*/
    public Long getCommentId(){ return commentId;}

    /**@return the body of the comment*/
    public String getComment(){ return comment;}

    /**@return the number of likes of a Comment*/
    public int getLikes(){ return likes;}

    /**@return the id of the topic to which the Comment refers */
    public Long getTopic(){ return topic.getTopicId(); }

    /**@return the Farmer who wrote the Comment*/
    public Long getFarmer() { return farmer.getId(); }

    /**@return the Date when the Comment was written*/
    public String getDate() {
        String sDate= String.valueOf(date);
        return sDate.substring(0,10); }

    /**@return the firstName of the Farmer who wrote the Comment*/
    public String getFirstName(){return farmer.getFirstName();}

    /**@return the lastName of the Farmer who wrote the Comment*/
    public String getLastName(){return farmer.getLastName();}

    /**Set the body of the Comment @param comment*/
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**Increments the number of likes of a Comment*/
    public void setLikes() { this.likes++; }

    /**Decrements the number of likes of a Comment*/
    public void unlike() { this.likes--; }

    /**Set the Topic to which the Comment refers @param topic*/
    public void setTopic(Topic topic) { this.topic = topic; }

    /**Set the Farmer who wrote the Comment @param farmer*/
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    /**Set the Date when the Comment was published @param date*/
    public void setDate(){this.date=Calendar.getInstance().getTime();}
}
