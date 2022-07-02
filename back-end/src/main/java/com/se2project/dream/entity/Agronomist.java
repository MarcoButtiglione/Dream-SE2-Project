package com.se2project.dream.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;


/***
 * Abstraction of a determined Agronomist. Every Agronomist has a firstName,
 * lastName, an aadhaar, an email, a password and a telephone number.
 */
@Entity @Data
public class Agronomist {

    private @Id @GeneratedValue Long agronomistId;
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
     * @ see location foreign key
     */
    @OneToOne
    @JoinColumn(name="location_fk")
    private Location location;

    /**
     * one to many relationship with @see Meeting, an agronomist can have many meetings
     */
    @OneToMany(mappedBy="agronomist")
    private Set<Meeting> meetings;

    /**
     * one to many relationship with @see Request, an agronomist can receive many requests
     */
    @OneToMany(mappedBy="agronomist")
    private Set<Request> requests;
    /**
     * one to many relationship with @see NotificationAgronomist, an agronomist can receive many notification
     */
    @OneToMany(mappedBy="agronomist")
    private Set<NotificationAgronomist> notificationAgronomists;
    /**
     * one to many relationship with @see Knowledge, an agronomist can create many knowledges
     */
    @OneToMany(mappedBy="agronomist")
    private Set<Knowledge> knowledges;


    /**Constructor*/
    public Agronomist() {
    }


    /**Constructor
     * @param firstName Agronomist's firstname;
     * @param lastName Agronomist's lastname;
     * @param aadhaar Agronomist's personal Indian fiscal code;
     * @param email Agronomist's email, username for login;
     * @param password Agronomist's password;
     * @param telephone Agronomist's personal telephone number:
     * @param location Agronomist's location his responsible for.
     * */
    public Agronomist(String firstName, String lastName, String aadhaar, String email, String password, String telephone, Location location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aadhaar = aadhaar;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.location=location;
    }



    /**@return agronomistId*/
    public Long getId() {
        return agronomistId;
    }

    /**@return agronomist firstName*/
    public String getFirstName() {
        return firstName;
    }

    /**@return agronomist lastname*/
    public String getLastName() {
        return lastName;
    }

    /**@return agronomist aadhaar*/
    public String getAadhaar() {
        return aadhaar;
    }

    /**@return agronomist email*/
    public String getEmail() {
        return email;
    }

    /**@return agronomist password*/
    public String getPassword() {
        return password;
    }

    /**@return agronomist telephone*/
    public String getTelephone() {
        return telephone;
    }

    /**@return agronomist location*/
    public Long getLocation(){ return location.getLocationId();}

    /**@return the authority of the agronomist*/
    public ArrayList<String> getRole() {
        ArrayList<String> roles = new ArrayList<>();
        roles.add("Agronomist");
        return roles;
    }

    /**@return all the request of the Agronomist*/
    public Set<Request> getRequests() { return requests; }

    /**@return all the meeting of the Agronomist*/
    public Set<Meeting> getMeetings() { return meetings; }

    /**@return all knowledges created by the Agronomist*/
    public Set<Knowledge> getKnowledges() { return knowledges; }

    /**Set value of the firstName with @param firstname*/
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
    public void setPassword(String password) { this.password = password; }

    /**Set value of the telephone with @param telephone*/
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**Set value of the location with @param location*/
    public void setLocation(Location location){ this.location=location; }

}
