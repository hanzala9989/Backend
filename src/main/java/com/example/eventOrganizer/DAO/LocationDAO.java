package com.example.eventOrganizer.DAO;

import java.util.List;


import com.example.eventOrganizer.Entity.LocationEntity;

public interface LocationDAO{
    public LocationEntity addLocation(LocationEntity locationEntity);

    public LocationEntity editLocation(LocationEntity locationEntity);

    public LocationEntity getLocation(Long locationID);

    public boolean deleteLocation(Long loactionID);

    public List<LocationEntity> getAllLocation();


}
