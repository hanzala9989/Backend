package com.example.eventOrganizer.Service;

import java.util.List;

import com.example.eventOrganizer.Entity.LocationEntity;

public interface LoactionService {
    public LocationEntity addLocationService(LocationEntity locationEntity);

    public LocationEntity editLocationService(LocationEntity locationEntity);

    public LocationEntity getLocationService(Long locationID);

    public boolean deleteLocationService(Long loactionID);

    public List<LocationEntity> getAllLocationService();
}
