package com.se2project.dream.entity;

import javax.persistence.*;
import java.util.Set;

/***
 * Abstraction of a determined Product. Every Product has a ProductId, a name of the product,
 * a type, a string of specifics, it is bound to a Production and to a Farm.
 */
@Entity
public class Product {
    private @Id @GeneratedValue Long ProductId;
    private String product;
    private  String type;
    private String specifics;


    /**
     * one to many relationship with @see Production, a Product can be part of many Productions
     * */
    @OneToMany(mappedBy="product", cascade = CascadeType.ALL)
    private Set<Production> productions;

    /**
     * @ see Farm foreign key
     */
    @ManyToOne
    @JoinColumn(name="farm_fk")
    private Farm farm;

    /**Constructor*/
    public Product(){}

    /**Constructor
     * @param type type of the Product
     * @param product name of the Product
     * @param specifics additional info on the Product
     * */
    public Product(String product, String type, String specifics){
        this.product=product;
        this.type=type;
        this.specifics=specifics;
    }

    /**@return productId*/
    public Long getProductId() { return ProductId; }

    /**@return the name of the Product*/
    public String getProduct() { return product;  }

    /**@return the type of the Product*/
    public String getType() { return type; }

    /**@return the specifics of the Product*/
    public String getSpecifics() { return specifics; }

    /**@return the farmId of the Farm with that specific Product*/
    public Long getFarm(){return farm.getFarmId();}

    /**@return the Production*/
    public Set<Production> getProductions() { return productions; }

    /**Set the name of the Product @param product*/
    public void setProduct(String product) { this.product = product;  }

    /**Set the type of the Product @param type*/
    public void setType(String type) { this.type = type; }

    /**Set the specifics of the Product @param specifics*/
    public void setSpecifics(String specifics) { this.specifics = specifics; }

    /**Set the Productions of the Product @param productions*/
    public void setProductions(Set<Production> productions) { this.productions = productions; }

    /**Set the Farm of the Product @param farm*/
    public void setFarm(Farm farm){this.farm=farm;}
}
