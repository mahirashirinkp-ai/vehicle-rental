package com.vrms.service;

import com.vrms.model.Customer;
import com.vrms.model.Rental;
import com.vrms.model.Vehicle;
import com.vrms.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private VehicleService vehicleService;

    public Rental createRental(Rental rental) {
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());
        if (days <= 0) throw new RuntimeException("End date must be after start date");
        double cost = days * rental.getVehicle().getPricePerDay();
        rental.setTotalCost(cost);
        rental.setStatus("PENDING");
        return rentalRepository.save(rental);
    }

    public List<Rental> findByCustomer(Customer customer) {
        return rentalRepository.findByCustomer(customer);
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public List<Rental> findByStatus(String status) {
        return rentalRepository.findByStatus(status);
    }

    public Optional<Rental> findById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental updateStatus(Long id, String status, String note) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        rental.setStatus(status);
        rental.setAdminNote(note);
        if ("APPROVED".equals(status)) {
            vehicleService.setAvailability(rental.getVehicle().getId(), false);
        } else if ("COMPLETED".equals(status) || "REJECTED".equals(status)) {
            vehicleService.setAvailability(rental.getVehicle().getId(), true);
        }
        return rentalRepository.save(rental);
    }
}
