package com.se2project.dream.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/***
 * Abstraction of a determined Knowledge. Every Knowledge has a title,
 * date, a description, an article, a category and the number of likes.
 */
@Entity
public class Knowledge {
    private @Id @GeneratedValue Long knowledgeId;
    private String title;
    private Date date;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String article;
    private String category;
    private int likes;

    /**
     * @ see agronomist foreign key
     */
    @ManyToOne
    @JoinColumn(name="agronomist_fk")
    private Agronomist agronomist;

    /**
     * one to many relationship with @see LikeKnowledge, a farmer can like many knoledges
     */
    @OneToMany(mappedBy="knowledge", cascade = CascadeType.ALL)
    private Set<LikeKnwoledge> farmers;

    /**Constructor*/
    public Knowledge(){}

    /**Constructor
     * @param title Knowledge's title;
     * @param description Knowledge's title;
     * @param /date Knowledge's date;
     * @param article Knowledge's article;
     * @param category Knowledge's category;
     * @param /likes Knowledge's number of likes;
     * @param agronomist Knowledge's publisher, the agronomist that published the Knowledge
     * */
    public Knowledge(String title, String description, String article, String category, Agronomist agronomist){
        this.title=title;
        this.description=description;
        this.date= Calendar.getInstance().getTime();
        this.article=article;
        this.category=category;
        this.likes=0;
        this.agronomist=agronomist;
    }

    /**@return knowledgeId*/
    public Long getKnowledgeId(){ return knowledgeId; }

    /**@return title*/
    public String getTitle(){ return title; }

    /**@return description*/
    public String getDescription(){ return description; }

    /**@return date of publication*/
    public LocalDate getDate(){
        String sDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        LocalDate day= LocalDate.parse(sDate);
        return day; }

    /**@return the text of the article*/
    public String getArticle() { return article; }

    /**@return category*/
    public String getCategory() { return category; }

    /**@return number of likes*/
    public int getLikes() { return likes; }

    /**@return agronomistId of the publisher*/
    public Long getAgronomist() { return agronomist.getId(); }

    /**Set title of the knowledge with @param title*/
    public void setTitle(String title) { this.title = title; }

    /**Set description of the knowledge with @param description*/
    public void setDescription(String description) { this.description = description; }

    /**Set value of the date of the knowledge with @param date*/
    public void setDate() { this.date = Calendar.getInstance().getTime(); }

    /**Set body of the article of the knowledge with @param article*/
    public void setArticle(String article) { this.article = article; }

    /**Set category of the knowledge with @param category*/
    public void setCategory(String category) { this.category = category; }

    /**Set the agronomist who published the knowledge with @param agronomist*/
    public void setAgronomist(Agronomist agronomist) { this.agronomist = agronomist; }

    /**Increments the number of likes to the knowledge*/
    public  void like() { this.likes++; }

    /**Decrements the number of likes to the knowledge*/
    public void unlike() {  this.likes--; }
}
