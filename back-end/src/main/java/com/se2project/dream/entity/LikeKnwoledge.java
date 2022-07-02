package com.se2project.dream.entity;

import javax.persistence.*;

/***
 * Abstraction of LikeKnowledge.
 */
@Entity
public class LikeKnwoledge {

    private  @Id @GeneratedValue Long id;

    /**
     * @ see Knowledge foreign key
     */
    @ManyToOne
    @PrimaryKeyJoinColumn(name="knowledge_fk", referencedColumnName="knowledge_id")
    private Knowledge knowledge;

    /**
     * @ see Farmer foreign key
     */
    @ManyToOne
    @PrimaryKeyJoinColumn(name="farmer_fk", referencedColumnName="farmer_id")
    private Farmer farmer;

    /**Constructor
     * @param knowledge  the knowledge to which the like refers
     * @param farmer the farmer to which the like refers
     * */    public LikeKnwoledge(Knowledge knowledge, Farmer farmer) {
        this.knowledge = knowledge;
        this.farmer = farmer;
    }

    /**Constructor*/
    public LikeKnwoledge() {
    }

    /**@return likeKnowledge's Id*/
    public Long getId() {
        return id;
    }


    /**@return the knowledge to which the likeKnowledge refers*/
    public Knowledge getKnowledge() {
        return knowledge;
    }

    /**Set the knowledge to which the likeKnowledge refers*/
    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    /**@return the farmer to which the likeKnowledge refers*/
    public Farmer getFarmer() {
        return farmer;
    }

    /**Set the farmer to which the likeKnowledge refers*/
    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
}
