package com.se2project.dream.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/***
 * Abstraction of a determined NotificationAgronomist. Every NotificationAgronomist has a date,
 * a type, a description, a date, an agronomist to which it refers to, a Farmer to which it refers to
 * and a Meeting.
 */
@Entity
public class NotificationAgronomist {
    private @Id  @GeneratedValue Long notificationId;
    private String type;
    private String description;
    private Date date;

    /**
     * @ see Agronomist foreign key
     */
    @ManyToOne
    @JoinColumn(name="agronomist_fk")
    private Agronomist agronomist;

    /**
     * @ see Meeting foreign key
     */
    @ManyToOne
    @JoinColumn(name="meeting_fk")
    private Meeting meeting;

    /**
     * @ see Farmer foreign key
     */
    @ManyToOne
    @JoinColumn(name="farmer_fk")
    private Farmer farmer;

    /**Constructor*/
    public NotificationAgronomist(){}

    /**Constructor
     * @param farmer the Farmer sending the Notification
     * @param agronomist the Agronomist who receives the Notification
     * @param description the description of the Notification
     * @param meeting the Meeting to which the Notification refers to
     * @param type the type of the Notification
     * */
    public NotificationAgronomist(String type, String description, Agronomist agronomist, Meeting meeting, Farmer farmer) {
        this.type = type;
        this.description = description;
        this.date = Calendar.getInstance().getTime();
        this.agronomist = agronomist;
        this.meeting = meeting;
        this.farmer = farmer;
    }

    /**@return notificationId*/
    public Long getNotificationId() {
        return notificationId;
    }

    /**@return the type of the Notification*/
    public String getType() {
        return type;
    }

    /**Set the type of the NotificationAgronomist @param type*/
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

    /**@return the Agronomist's Id*/
    public Long getAgronomist() {
        return agronomist.getId();
    }

    /**Set the Agronomist as receiver of a Notification @param agronomist*/
    public void setAgronomist(Agronomist agronomist) {
        this.agronomist = agronomist;
    }
}
