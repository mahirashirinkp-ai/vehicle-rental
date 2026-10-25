package com.vrms.config;

import com.vrms.model.Customer;
import com.vrms.model.Vehicle;
import com.vrms.repository.CustomerRepository;
import com.vrms.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create admin user if not exists
        if (!customerRepository.existsByEmail("admin@vrms.com")) {
            Customer admin = new Customer();
            admin.setFullName("Admin");
            admin.setEmail("admin@vrms.com");
            admin.setPhone("9999999999");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            customerRepository.save(admin);
            System.out.println("✅ Admin created: admin@vrms.com / admin123");
        }

        // Add sample vehicles if none exist
        if (vehicleRepository.count() == 0) {
            vehicleRepository.save(new Vehicle(null, "Toyota Innova", "Car", "Toyota", 2500.0, true,
                    "https://images.unsplash.com/photo-1549399542-7d3b4e6b7d9e?w=400", "Spacious 7-seater SUV, perfect for family trips."));
            vehicleRepository.save(new Vehicle(null, "Honda City", "Car", "Honda", 1800.0, true,
                    "https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=400", "Comfortable sedan for city and highway travel."));
            vehicleRepository.save(new Vehicle(null, "Royal Enfield Classic 350", "Bike", "Royal Enfield", 800.0, true,
                    "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400", "Classic cruiser bike ideal for scenic rides."));
            vehicleRepository.save(new Vehicle(null, "Tata Ace Mini Van", "Van", "Tata", 1500.0, true,
                    "https://images.unsplash.com/photo-1600298882525-1e4f7fdc9b37?w=400", "Compact van great for goods transport."));
            vehicleRepository.save(new Vehicle(null, "Maruti Swift", "Car", "Maruti", 1200.0, true,
                    "https://images.unsplash.com/photo-1596768900012-f5f1e4c69a69?w=400", "Fuel-efficient hatchback for daily commutes."));
            vehicleRepository.save(new Vehicle(null, "Bajaj Pulsar 150", "Bike", "Bajaj", 600.0, true,
                    "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400", "Popular sports bike for quick city rides."));
            System.out.println("✅ Sample vehicles added.");
        }
    }
}
