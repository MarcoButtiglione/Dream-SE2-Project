package com.se2project.dream.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/***
 * Abstraction of a determined Request. Every Request has a subject, a requestId, a body of the
 * Request, a feedback, a status, a date, a farmer and an agronomist.
 */
@Entity
public class Request {
    private @Id @GeneratedValue Long requestId;
    private String subject;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String request;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String response;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String feedback;
    private String status;
    private Date date;

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

    /**Constructor*/
    public Request(){}

    /**Constructor
     * @param agronomist the agronomist to which the request is sent
     * @param farmer  the farmer who sends the request
     * @param request the body of the request
     * @param feedback the feedback to the request
     * @param response  the response to the request
     * @param subject the subject of the request*/
    public Request(String subject,String request, Farmer farmer, Agronomist agronomist, String response, String feedback){
        this.subject=subject;
        this.request=request;
        this.agronomist=agronomist;
        this.farmer=farmer;
        this.status="pendant";
        this.date= Calendar.getInstance().getTime();
    }

    /**@return requestId*/
    public Long getRequestId(){ return requestId; }

    /**@return the body of the request*/
    public String getRequest() { return request; }

    /**@return the response of the request*/
    public String getResponse() { return response; }

    /**@return the feedback to the request*/
    public String getFeedback() { return feedback; }

    /**@return the id of the agronomist to which the request is sent*/
    public Long getAgronomist() { return agronomist.getId(); }

    /**@return the id of the farmer that sends the request*/
    public Long getFarmer() { return farmer.getId(); }

    /**@return the name of the farmer that sends the request*/
    public String getFarmerName(){
        String name=farmer.getLastName()+" "+ farmer.getFirstName();
        return name;
    }

    /**@return the status of the request*/
    public String getStatus() { return status; }

    /**@return the date of the request*/
    public LocalDate getDate(){
        String sDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        LocalDate day= LocalDate.parse(sDate);
        return day;
    }

    /**@return the subject of the request*/
    public String getSubject() {
        return subject;
    }

    /**Set subject of the Request @param subject*/
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**Set body of the Request @param request*/
    public void setRequest(String request) { this.request=request; }

    /**Set response to the Request @param response*/
    public void setResponse(String response) { this.response=response; }

    /**Set feedback of the Request @param feedback*/
    public void setFeedback(String feedback) { this.feedback=feedback; }

    /**Set farmer who sent the request @param farmer*/
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    /**Set agronomist who received the request @param agronomist*/
    public void setAgronomist(Agronomist agronomist) {  this.agronomist = agronomist; }

    /**Set status of the Request @param status*/
    public void setStatus(String status){this.status=status;}

    /**Set date of the Request @param date*/
    public void setDate(){this.date=Calendar.getInstance().getTime();}
}
