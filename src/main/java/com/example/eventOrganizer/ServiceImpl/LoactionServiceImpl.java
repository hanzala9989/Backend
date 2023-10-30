package com.example.eventOrganizer.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventOrganizer.DAO.LocationDAO;
import com.example.eventOrganizer.Entity.LocationEntity;
import com.example.eventOrganizer.Service.LoactionService;

@Service
public class LoactionServiceImpl implements LoactionService {
    @Autowired
    LocationDAO locationDAO;

    @Override
    public LocationEntity addLocationService(LocationEntity locationEntity) {
        return locationDAO.addLocation(locationEntity);
    }

    @Override
    public LocationEntity editLocationService(LocationEntity locationEntity) {
        return locationDAO.editLocation(locationEntity);
    }

    @Override
    public LocationEntity getLocationService(Long locationID) {
        return locationDAO.getLocation(locationID);
    }

    @Override
    public boolean deleteLocationService(Long loactionID) {
        return locationDAO.deleteLocation(loactionID);
    }

    @Override
    public List<LocationEntity> getAllLocationService() {
        return locationDAO.getAllLocation();
    }

}
