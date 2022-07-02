package com.se2project.dream.entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/***
 * Abstraction of a determined Topic. Every Topic has a name, an Id,
 * a description, a tag, a date, a set of comments, and a farmer who published it
 * */
@Entity
public class Topic {
    private @Id @GeneratedValue Long topicId;
    private String topic;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private String tag;
    private Date date;

    /**
     * one to many relationship with @see Comment, a topic can have many comments
     */
    @OneToMany(mappedBy="topic", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    /**
     * @ see Farmer foreign key
     */
    @ManyToOne
    @JoinColumn(name="farmer_fk")
    private Farmer farmer;

    /**Constructor*/
    public Topic(){}

    /**Constructor
     * @param topic the name of the Topic
     * @param description of the Topic
     * @param tag of the Topic
     * @param farmer who posted the Topic
     * @param /date when the Farmer posted the Topic
     * */
    public Topic(String topic, String description, String tag, Farmer farmer){
        this.topic=topic;
        this.description=description;
        this.tag=tag;
        this.farmer=farmer;
        this.date= Calendar.getInstance().getTime();
    }

    /**@return topicId*/
    public Long getTopicId(){return topicId;}

    /**@return the name of the topic*/
    public String getTopic(){return topic;}

    /**@return the description of the topic*/
    public String getDescription(){return description;}

    /**@return the tag of the topic*/
    public String getTag(){return tag;}

    /**@return the id of the farmer who published the topic*/
    public Long getFarmer(){return farmer.getId();}

    /**@return the firstname of the farmer who published the topic*/
    public String getFirstName(){return farmer.getFirstName();}

    /**@return the lastname of the farmer who published the topic*/
    public String getLastName(){return farmer.getLastName();}

    /**@return the day when the farmer who published the topic*/
    public LocalDate getDate(){
        String sDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        LocalDate day= LocalDate.parse(sDate);
        return day;
    }

    /**@return the comments to a topic*/
    public Set<Comment> getComments() { return comments; }

    /**Set the name of a topic @param topic*/
    public void setTopic(String topic){ this.topic=topic;}

    /**Set the description of a topic @param description*/
    public void setDescription(String description){ this.description=description;}

    /**Set the tag of a topic @param tag*/
    public void setTag(String tag){ this.tag=tag;}

    /**Set the comments of a topic @param comments*/
    public void setComments(Set<Comment> comments) { this.comments = comments; }

    /**Set the farmer of a topic @param farmer*/
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    /**Set the date of a topic @param date*/
    public void setDate(){this.date=Calendar.getInstance().getTime();}
}
