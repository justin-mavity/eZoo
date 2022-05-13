package com.project.ezoo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animals")
public class Animal extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long animalid;

    @NotNull
    @Column(unique = true)
    private String name;
    private String taxKingdom;
    private String taxPhylum;
    private String taxClass;
    private String taxOrder;
    private String taxFamily;
    private String taxGenus;
    private String taxSpecies;
    private double height;
    private double weight;
    private String type;
    private String healthStatus;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "animal",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "animal",
            allowSetters = true)
    private Set<AnimalFeedingSchedules> feedingschedules = new HashSet<>();

    public Animal() {
    }

    public Animal(String name, String taxKingdom, String taxPhylum, String taxClass, String taxOrder, String taxFamily, String taxGenus, String taxSpecies, double height, double weight, String type, String healthStatus) {
        this.name = name;
        this.taxKingdom = taxKingdom;
        this.taxPhylum = taxPhylum;
        this.taxClass = taxClass;
        this.taxOrder = taxOrder;
        this.taxFamily = taxFamily;
        this.taxGenus = taxGenus;
        this.taxSpecies = taxSpecies;
        this.height = height;
        this.weight = weight;
        this.type = type;
        this.healthStatus = healthStatus;
    }

    public long getAnimalid() {
        return animalid;
    }

    public void setAnimalid(long animalid) {
        this.animalid = animalid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxKingdom() {
        return taxKingdom;
    }

    public void setTaxKingdom(String taxKingdom) {
        this.taxKingdom = taxKingdom;
    }

    public String getTaxPhylum() {
        return taxPhylum;
    }

    public void setTaxPhylum(String taxPhylum) {
        this.taxPhylum = taxPhylum;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getTaxOrder() {
        return taxOrder;
    }

    public void setTaxOrder(String taxOrder) {
        this.taxOrder = taxOrder;
    }

    public String getTaxFamily() {
        return taxFamily;
    }

    public void setTaxFamily(String taxFamily) {
        this.taxFamily = taxFamily;
    }

    public String getTaxGenus() {
        return taxGenus;
    }

    public void setTaxGenus(String taxGenus) {
        this.taxGenus = taxGenus;
    }

    public String getTaxSpecies() {
        return taxSpecies;
    }

    public void setTaxSpecies(String taxSpecies) {
        this.taxSpecies = taxSpecies;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Set<AnimalFeedingSchedules> getFeedingschedules() {
        return feedingschedules;
    }

    public void setFeedingschedules(Set<AnimalFeedingSchedules> feedingschedules) {
        this.feedingschedules = feedingschedules;
    }

    @Override
    public String toString() {
        return "Animal [animalID=" + animalid + ", name=" + name + ", taxKingdom=" + taxKingdom + ", taxPhylum="
                + taxPhylum + ", taxClass=" + taxClass + ", taxOrder=" + taxOrder + ", taxFamily=" + taxFamily
                + ", taxGenus=" + taxGenus + ", taxSpecies=" + taxSpecies + ", height=" + height + ", weight=" + weight
                + ", type=" + type + ", healthStatus=" + healthStatus + ", feeding schedule(s)=" + "]";
    }

}
