package com.se2project.dream.entity;

import javax.persistence.*;
import java.util.Set;

/***
 * Abstraction of a determined Farm. Every Farm has an Id, a number of squareKM, an address, a location
 * a farmer, a set of productions and a set of products.
 */
@Entity
public class Farm {
    @Id @GeneratedValue
    private Long farmId;
    private float squareKm;

    /**
     * @ see Location foreign key
     */
    @ManyToOne
    @JoinColumn(name="location_fk")
    private Location location;
    private String address;

    /**
     * one to many relationship with @see SoilData, a farm can have  many SoilData
     */
    @OneToMany(mappedBy="farm", cascade = CascadeType.ALL)
    private Set<SoilData> soilDatas;

    /**
     * @ see Farmer foreign key
     */
    @OneToOne
    @JoinColumn(name="farmer_fk")
    private Farmer farmer;

    /**
     * one to many relationship with @see Production, a Farm can have many productions
     */
    @OneToMany(mappedBy="farm", cascade = CascadeType.ALL)
    private Set<Production> productions;

    /**
     * one to many relationship with @see Product, a Farm can have many products
     * */
    @OneToMany(mappedBy="farm", cascade = CascadeType.ALL)
    private Set<Product> product;

    /**Constructor*/
    public Farm(){}

    /**Constructor
     * @param farmer  the Farmer owner of the farm
     * @param location the Location in which the Farm is located
     * @param address the address of the Farm
     * @param squareKm the area of the Farm
     * */
    public Farm(float squareKm, Location location, Farmer farmer, String address){
        this.squareKm=squareKm;
        this.location=location;
        this.farmer=farmer;
        this.address=address;
    }



    /**@return farmId*/
    public Long getFarmId() { return farmId; }

    /**@return the area of the Farm*/
    public float getSquareKm() { return squareKm; }

    /**@return Farmer owner of the Farm*/
    public Long getFarmer() { return farmer.getId(); }

    /**@return the Location in which the Farm is located*/
    public Long getLocation() { return location.getLocationId(); }

    /**@return the Production of the Farm*/
    public Set<Production> getProductions() { return productions; }

    /**@return the SoilData of the Farm*/
    public Set<SoilData> getSoilDatas() { return soilDatas; }

    /**@return the address of a Farm*/
    public String getAddress() { return address; }

    /**Set the area of the Farm @param squareKm*/
    public void setSquareKm(float squareKm) { this.squareKm = squareKm; }

    /**Set the Farmer of the Farm @param farmer*/
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    /**Set the Location of the Farm @param location*/
    public void setLocation(Location location) { this.location = location; }

    /**Set the SoildData of the Farm @param soilDatas*/
    public void setSoilDatas(Set<SoilData> soilDatas) { this.soilDatas = soilDatas; }

    /**Set the Productions of the Farm @param productions*/
    public void setProductions(Set<Production> productions) { this.productions = productions; }

    /**Set the address of the Farm @param address*/
    public void setAddress(String adress) { this.address = adress; }
}
