package com.vrms.repository;

import com.vrms.model.Rental;
import com.vrms.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCustomer(Customer customer);
    List<Rental> findByStatus(String status);
}
