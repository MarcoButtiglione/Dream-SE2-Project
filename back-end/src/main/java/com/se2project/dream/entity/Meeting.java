package com.se2project.dream.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/***
 * Abstraction of a determined Meeting. Every Meeting has a date,
 * a startTime, an endTime, a state and an evaluation field.
 */
@Entity
public class Meeting {
    private @Id @GeneratedValue Long meetingId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String state;
    private float evaluation;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String note;

    /**
     * @ see Farmer foreign key
     */
    @ManyToOne
    @JoinColumn(name="farmer_fk")
    private Farmer farmer;

    /**
     * @ see Agronomist foreign key
     */
    @ManyToOne
    @JoinColumn(name="agronomist_fk")
    private Agronomist agronomist;

    /**
     * one to many relationship with @see NotificationFarmer, a meeting can send many notifications to the farmer
     */
    @OneToMany(mappedBy="meeting", cascade = CascadeType.ALL)
    private Set<NotificationFarmer> notificationFarmers;

    /**
     * one to many relationship with @see NotificationAgronomist, a meeting can send many notifications to the agronomist
     */
    @OneToMany(mappedBy="meeting", cascade = CascadeType.ALL)
    private Set<NotificationAgronomist> notificationAgronomists;

    /**Constructor*/
    public Meeting(){}

    /**Constructor
     * @param date the Meeting's date;
     * @param startTime the Meeting's start time;
     * @param endTime the Meeting's end time;
     * @param /state the Meeting's state;
     * @param agronomist the Agronomist involved in the meeting;
     * @param farmer the Farmer involved in the meeting.
     * */
    public Meeting(String date, String startTime, String endTime, Farmer farmer, Agronomist agronomist){
        this.date= LocalDate.parse(date);
        this.startTime=LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.state= "pendant";
        this.agronomist=agronomist;
        this.farmer=farmer;
    }

    /**@return meetingId*/
    public Long getMeetingId() { return meetingId; }

    /**@return meeting's date*/
    public LocalDate getDate() { return date; }

    /**@return meeting's startTime*/
    public LocalTime getStartTime() { return startTime; }

    /**@return meeting's endTime*/
    public LocalTime getEndTime() { return endTime; }

    /**@return meeting's state*/
    public String getState(){ return state; }

    /**@return meeting's evaluation*/
    public float getEvaluation() { return evaluation; }

    /**@return meeting's note*/
    public String getNote() { return note; }

    /**@return the id of the Agronomist involved in the meeting*/
    public Long getAgronomist() { return agronomist.getId(); }

    /**@return the id of the Farmer involved in the meeting*/
    public Long getFarmer() { return farmer.getId(); }

    /**@return the name of the Farmer involved in the meeting*/
    public String getFarmerName(){
        String name=farmer.getLastName()+" "+ farmer.getFirstName();
        return name;
    }

    /**@return the Address of the Farmer involved in the meetings*/
    public String getAddress(){
        return farmer.getFarm().getAddress();
    }

    //public void setDateString(String date) { this.date = LocalDate.parse(date); }

    /**Set value of the date of the Meeting with @param date*/
    public void setDate(LocalDate date) {this.date=date;}

    /**Set value of the start time of the Meeting with @param start time*/
    public void setStartTime(String hour) { this.startTime = LocalTime.parse(hour);  }

    /**Set value of the end time of the Meeting with @param end time*/
    public void setEndTime(String hour) { this.endTime=LocalTime.parse(hour);}

    /**Set value of the state of the Meeting with @param state*/
    public void setState(String state) { this.state = state; }

    /**Set value of the evaluation the agronomist gives to the farmer after the Meeting with @param evaluation*/
    public void setEvaluation(float evaluation) { this.evaluation = evaluation; }

    /**Set value of the note of the Meeting with @param note*/
    public void setNote(String note) { this.note = note; }

    /**Set Farmer of the Meeting @param farmer*/
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    /**Set Agronomist of the Meeting @param agronomist*/
    public void setAgronomist(Agronomist agronomist) { this.agronomist = agronomist; }
}
