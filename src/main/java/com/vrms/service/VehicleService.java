package com.vrms.service;

import com.vrms.model.Vehicle;
import com.vrms.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> findAvailable() {
        return vehicleRepository.findByAvailableTrue();
    }

    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }

    public void setAvailability(Long id, boolean available) {
        vehicleRepository.findById(id).ifPresent(v -> {
            v.setAvailable(available);
            vehicleRepository.save(v);
        });
    }
}
