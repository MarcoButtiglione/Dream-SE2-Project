package com.se2project.dream.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/***
 * Abstraction of a determined Production. Every Production has a productionId, a date,
 * a quantity, a note, a farm and a product.
 */
@Entity
public class Production {
    private @Id @GeneratedValue Long productionId;
    private Date date;
    private float qta;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String note;

    /**
     * @ see Farm foreign key
     */
    @ManyToOne
    @JoinColumn(name="farm_fk")
    private Farm farm;

    /**
     * @ see Product foreign key
     */
    @ManyToOne
    @JoinColumn(name="product_fk")
    private Product product;

    /**Constructor*/
    public Production(){}

    /**Constructor
     * @param /data the date of the production
     * @param qta  the quantity of the production
     * @param product  the product of the production
     * @param farm  the farm od the production
     * @param note the note on the production
     * */
    public Production(float qta, String note, Farm farm, Product product){
        this.date=Calendar.getInstance().getTime();
        this.qta=qta;
        this.note=note;
        this.product=product;
        this.farm=farm;
    }

    /**@return productionId*/
    public Long getProductionId() { return productionId; }

    /**@return the day of the production*/
    public LocalDate getDate() {
        String sDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        LocalDate day= LocalDate.parse(sDate);
        return day; }

    /**@return the quantity of the production*/
    public float getQta() { return qta; }

    /**@return the note of the production*/
    public String getNote() { return note; }

    /**@return the farm od the production*/
    public Long getFarm() {  return farm.getFarmId(); }

    /**@return the product of the production*/
    public Long getProduct() { return product.getProductId(); }

    /**Set date for the production @param date*/
    public void setDate() { this.date = Calendar.getInstance().getTime(); }

    /**Set quantity for the production @param quantity*/
    public void setQta(float qta) { this.qta = qta; }

    /**Set note for the production @param note*/
    public void setNote(String note) { this.note = note; }

    /**Set farm for the production @param farm*/
    public void setFarm(Farm farm) { this.farm = farm; }

    /**Set product for the production @param product*/
    public void setProduct(Product product) { this.product = product; }

}
