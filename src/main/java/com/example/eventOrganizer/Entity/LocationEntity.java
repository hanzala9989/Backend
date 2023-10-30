package com.example.eventOrganizer.Entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private Long LocationID;

    @Column(name = "location_name")
    private String name;

    @Column(name = "location_latitude")
    private double latitude;

    @Column(name = "location_longitude")
    private double longitude;

    @Column(name = "location_address")
    private String address;
    
    @Column(name = "location_description")
    private String description;

    // Constructors
    public LocationEntity() {
    }

    public LocationEntity(Long locationID, String name, double latitude, double longitude, String address,
            String description) {
        LocationID = locationID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.description = description;
    }

    public LocationEntity setLocationID(Long locationID) {
        this.LocationID = locationID;
        return this;
    }

    public Long getLocationID() {
        return LocationID;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public LocationEntity setName(String name) {
        this.name = name;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public LocationEntity setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocationEntity setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LocationEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LocationEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LocationEntity location = (LocationEntity) o;
        return Double.compare(location.latitude, latitude) == 0 &&
                Double.compare(location.longitude, longitude) == 0 &&
                Objects.equals(name, location.name) &&
                Objects.equals(address, location.address) &&
                Objects.equals(description, location.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, address, description);
    }

    // toString method
    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
