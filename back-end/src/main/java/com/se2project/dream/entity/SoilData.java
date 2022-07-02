package com.se2project.dream.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/***
 * Abstraction of a determined SoilData. Every SoilData has an Id, a date, a value for pH, for nitrogen,
 * for phosphorus, for carbon and for limestone and a farm it refers to.
 */
@Entity
public class SoilData {
    private @Id @GeneratedValue Long soilDataId;
    private Date date;
    private float PH;
    private float nitrogen;
    private float phosphorus;
    private float organic_carbon;
    private float limestone;

    /**
     * @ see Farmer foreign key
     */
    @ManyToOne
    @JoinColumn(name="farm_fk")
    private Farm farm;

    /**Constructor*/
    public SoilData(){}

    /**Constructor
     * @param farm of which the soil is analyzed
     * @param limestone value
     * @param nitrogen  value
     * @param PH value
     * @param organic_carbon value
     * @param phosphorus value
     * */
    public SoilData(Farm farm, float PH, float nitrogen, float phosphorus, float organic_carbon, float limestone){
        this.farm=farm;
        this.date= Calendar.getInstance().getTime();
        this.PH=PH;
        this.nitrogen=nitrogen;
        this.phosphorus=phosphorus;
        this.organic_carbon=organic_carbon;
        this.limestone=limestone;
    }

    /**@return soilDataId*/
    public Long getSoilDataId() { return soilDataId; }

    /**@return PH value*/
    public float getPH() { return PH; }

    /**@return Nitrogen value*/
    public float getNitrogen() { return nitrogen; }

    /**@return Phosphorus value*/
    public float getPhosphorus() { return phosphorus; }

    /**@return OrganicCarbon value*/
    public float getOrganic_carbon() { return organic_carbon; }

    /**@return Date*/
    public LocalDate getDate() {
        String sDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        LocalDate day= LocalDate.parse(sDate);
        return day; }

    /**@return Farm which soil is analyzed*/
    public Long getFarm() { return farm.getFarmId(); }

    /**@return Limestone value*/
    public float getLimestone() {
        return limestone;
    }

    /**Set Limestone value @param limestone*/
    public void setLimestone(float limestone) {
        this.limestone = limestone;
    }

    /**Set Farm @param farm*/
    public void setFarm(Farm farm){this.farm=farm;}

    /**Set PH value @param PH*/
    public void setPH(float PH) { this.PH = PH; }

    /**Set Nitorgen value @param nitrogen*/
    public void setNitrogen(float nitrogen) { this.nitrogen = nitrogen; }

    /**Set Phosphorus value @param phosphorus*/
    public void setPhosphorus(float phosphorus) { this.phosphorus = phosphorus;  }

    /**Set OrganicCarbon value @param organic_carbon*/
    public void setOrganic_carbon(float organic_carbon) { this.organic_carbon = organic_carbon; }

    /**Set Date value @param date*/
    public void setDate() {this.date = Calendar.getInstance().getTime(); }
}
