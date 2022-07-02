package com.se2project.dream.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

/***
 * Abstraction of a determined Farmer. Every Farmer has a firstName,
 * lastName, an aadhaar, an email, a password and a telephone number.
 */
@Entity @Data
public class Farmer {
    private @Id @GeneratedValue Long farmerId;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String aadhaar;
    @Column(unique=true)
    private String email;
    private String password;
    @Column(unique=true)
    private String telephone;

    /**
     * one to many relationship with @see Meeting, a Farmer can participate in many meetings
     */
    @OneToMany(mappedBy="farmer")
    private Set<Meeting> meetings;

    /**
     * one to many relationship with @see Request, a Farmer can create many requests
     */
    @OneToMany(mappedBy="farmer")
    private Set<Request> requests;

    /**
     * one to many relationship with @see Topic, a Farmer can create many topics
     */
    @OneToMany(mappedBy="farmer")
    private Set<Topic> topics;

    /**
     * one to many relationship with @see Comment, a Farmer can create many topics
     */
    @OneToMany(mappedBy="farmer")
    private Set<Comment> comments;

    /**
     * one to one relationship with @see Farm, a Farmer can be bound to only one farm
     */
    @OneToOne(mappedBy="farmer")
    private Farm farm;

    /**
     * one to many relationship with @see NotificationFarmer, a Farmer can receive many notifications
     */
    @OneToMany(mappedBy="farmer")
    private Set<NotificationFarmer> notificationFarmers;

    /**
     * one to many relationship with @see NotificationAgronomist, a Farmer can send many notifications to the agronomist
     */
    @OneToMany(mappedBy="farmer")
    private Set<NotificationAgronomist> notificationAgronomists;

    /**Constructor*/
    public Farmer() {
    }

    /**Constructor
     * @param firstName Farmer's firstname;
     * @param lastName Farmer's lastname;
     * @param aadhaar Farmer's personal Indian fiscal code;
     * @param email Farmer's email, username for login;
     * @param password Farmer's password;
     * @param telephone Farmer's personal phone number;
     * */
    public Farmer(String firstName, String lastName, String aadhaar, String email, String password, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aadhaar = aadhaar;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
    }

    /**@return farmer's Id*/
    public Long getId() {
        return farmerId;
    }

    /**@return farmer's firstName */
    public String getFirstName() {
        return firstName;
    }

    /**@return farmer's lastName*/
    public String getLastName() {
        return lastName;
    }

    /**@return farmer's aadhaar*/
    public String getAadhaar() {
        return aadhaar;
    }

    /**@return farmer's email*/
    public String getEmail() {
        return email;
    }

    /**@return farmer's password*/
    public String getPassword() {
        return password;
    }

    /**@return farmer's phone number*/
    public String getTelephone() {
        return telephone;
    }

    /**@return all the meeting of the farmer*/
    public Set<Meeting> getMeetings() { return meetings; }

    /**@return all the requests of the farmer*/
    public Set<Request> getRequests() { return requests; }

    /**@return all the topics of the farmer*/
    public Set<Topic> getTopics() { return topics; }

    /**@return all the comments of the farmer*/
    public Set<Comment> getComments() { return comments; }

    /**@return the farm of the farmer*/
    public Farm getFarm() { return farm; }

    /**@return the authority of the Farmer*/
    public ArrayList<String> getRole() {
        ArrayList<String> roles = new ArrayList<>();
        roles.add("Farmer");
        return roles;
        }


    /**Set value of the firstname with @param firstname*/
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**Set value of the lastname with @param lastname*/
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**Set value of the aadhaar with @param aadhaar*/
    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    /**Set value of the email with @param email*/
    public void setEmail(String email) {
        this.email = email;
    }

    /**Set value of the email with @param email*/
    public void setPassword(String password) {
        this.password = password;
    }

    /**Set value of the telephone with @param telephone*/
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**Set the meetings of the farmer with @param meetings*/
    public void setMeetings(Set<Meeting> meetings) { this.meetings = meetings; }

    /**Set the requests of the farmer with @param requests*/
    public void setRequests(Set<Request> requests) { this.requests = requests; }

    /**Set the topics of the farmer with @param topics*/
    public void setTopics(Set<Topic> topics) { this.topics = topics; }

    /**Set the comments of the farmer with @param comments*/
    public void setComments(Set<Comment> comments) { this.comments = comments; }

    /**Set value of the farm with @param farm*/
    public void setFarm(Farm farm) { this.farm = farm;}
}
