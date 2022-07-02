package com.se2project.dream.entity;

import javax.persistence.*;
import java.util.Set;

/***
 * Abstraction of a determined Location. Every Location has a locationID,
 * location name, a district and a mandal.
 */
@Entity
public class Location {
    @Id
    private Long locationId;
    private String location;
    private String district;
    private String mandal;

    /**
     * one to one relationship with @see Agronomist, a location can be associated with only one agronomist
     * */
    @OneToOne(mappedBy="location")
    private Agronomist agronomist;

    /**
     * one to many relationship with @see Farm, a location can have many farms
     */
    @OneToMany(mappedBy="location")
    private Set<Farm> farms;

    /**Constructor*/
    public Location (){}

    /**Constructor
     * @param id Location's Id;
     * @param location  Location's name;
     * @param district Location's district;
     * @param mandal  Location's mandal.
     * */
    public Location(Long id, String location, String district, String mandal){
        this.locationId=id;
        this.location=location;
        this.mandal=mandal;
        this.district=district;
    }

    /**@return locationId*/
    public Long getLocationId() {
        return locationId;
    }

    /**@return location's name*/
    public String getLocation() {
        return location;
    }

    /**@return district's name*/
    public String getDistrict() {
        return district;
    }

    /**@return mandal*/
    public String getMandal() {
        return mandal;
    }

    /**Set value of the locationId with @param locationId*/
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    /**Set name of the location with @param location*/
    public void setLocation(String location) {
        this.location = location;
    }

    /**Set name of the district with @param district*/
    public void setDistrict(String district) {
        this.district = district;
    }

    /**Set name of the mandal with @param mandal*/
    public void setMandal(String mandal) {
        this.mandal = mandal;
    }
}
