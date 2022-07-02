package com.se2project.dream.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/***
 * Abstraction of a determined NotificationFarmer. Every NotificationFarmer has a date,
 * a type, a description, a date, a Farmer to which it refers to and a Meeting.
 */
@Entity
public class NotificationFarmer {
    private @Id  @GeneratedValue Long notificationId;
    private String type;
    private String description;
    private Date date;

    /**
     * @ see Farmer foreign key
     */
    @ManyToOne
    @JoinColumn(name = "farmer_fk")
    private Farmer farmer;

    /**
     * @ see Meeting foreign key
     */
    @ManyToOne
    @JoinColumn(name = "meeting_fk")
    private Meeting meeting;

    /**Constructor*/
    public NotificationFarmer(){}

    /**Constructor
     * @param farmer the Farmer sending the Notification
     * @param farmer the Farmer who receives the Notification
     * @param description the description of the Notification
     * @param meeting the Meeting to which the Notification refers to
     * @param type the type of the Notification
     * */
    public NotificationFarmer(String type, String description, Farmer farmer, Meeting meeting) {
        this.type = type;
        this.description = description;
        this.date = Calendar.getInstance().getTime();
        this.farmer = farmer;
        this.meeting = meeting;
    }

    /**@return notificationId*/
    public Long getNotificationId() {
        return notificationId;
    }

    /**@return the type of the Notification*/
    public String getType() {
        return type;
    }

    /**Set the type of the NotificationFarmer @param type*/
    public void setType(String type) {
        this.type = type;
    }

    /**@return the description of the Notification*/
    public String getDescription() {
        return description;
    }

    /**Set the description of the Notification @param description*/
    public void setDescription(String description) {
        this.description = description;
    }

    /**@return the date of the Notification*/
    public String getDate() {
        return date.toString().substring(0,10);
    }

    /**Set the date for a specified Notification @param date*/
    public void setDate(Date date) {
        this.date = date;
    }

    /**@return the Farmer's Id*/
    public Long getFarmer() {
        return farmer.getId();
    }

    /**Set the Farmer as receiver of a Notification @param farmer*/
    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
}
