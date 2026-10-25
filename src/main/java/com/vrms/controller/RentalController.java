package com.vrms.controller;

import com.vrms.model.Customer;
import com.vrms.model.Rental;
import com.vrms.model.Vehicle;
import com.vrms.service.CustomerService;
import com.vrms.service.RentalService;
import com.vrms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class RentalController {

    @Autowired private VehicleService vehicleService;
    @Autowired private CustomerService customerService;
    @Autowired private RentalService rentalService;

    // ---- Vehicle Listing ----
    @GetMapping("/vehicles")
    public String listVehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.findAvailable());
        return "vehicles";
    }

    // ---- Rental Booking Form ----
    @GetMapping("/book/{vehicleId}")
    public String bookForm(@PathVariable Long vehicleId, Model model, Authentication auth) {
        Vehicle vehicle = vehicleService.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("today", LocalDate.now().toString());
        return "rental-form";
    }

    @PostMapping("/book/{vehicleId}")
    public String bookSubmit(@PathVariable Long vehicleId,
                              @RequestParam String startDate,
                              @RequestParam String endDate,
                              Authentication auth,
                              RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.findByEmail(auth.getName()).orElseThrow();
            Vehicle vehicle = vehicleService.findById(vehicleId).orElseThrow();

            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setVehicle(vehicle);
            rental.setStartDate(LocalDate.parse(startDate));
            rental.setEndDate(LocalDate.parse(endDate));

            rentalService.createRental(rental);
            redirectAttributes.addFlashAttribute("success", "Booking submitted! Awaiting admin approval.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Booking failed: " + e.getMessage());
        }
        return "redirect:/my-rentals";
    }

    // ---- My Rentals ----
    @GetMapping("/my-rentals")
    public String myRentals(Model model, Authentication auth) {
        Customer customer = customerService.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("rentals", rentalService.findByCustomer(customer));
        return "my-rentals";
    }

    // ---- Admin Dashboard ----
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("rentals", rentalService.findAll());
        model.addAttribute("vehicles", vehicleService.findAll());
        model.addAttribute("customers", customerService.findAll());
        return "admin";
    }

    @PostMapping("/admin/rental/{id}/status")
    public String updateRentalStatus(@PathVariable Long id,
                                      @RequestParam String status,
                                      @RequestParam(required = false) String note,
                                      RedirectAttributes redirectAttributes) {
        rentalService.updateStatus(id, status, note != null ? note : "");
        redirectAttributes.addFlashAttribute("success", "Rental #" + id + " updated to " + status);
        return "redirect:/admin";
    }

    @GetMapping("/admin/vehicle/add")
    public String addVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "admin-vehicle-form";
    }

    @PostMapping("/admin/vehicle/save")
    public String saveVehicle(@ModelAttribute Vehicle vehicle, RedirectAttributes redirectAttributes) {
        vehicleService.save(vehicle);
        redirectAttributes.addFlashAttribute("success", "Vehicle saved successfully.");
        return "redirect:/admin";
    }

    @GetMapping("/admin/vehicle/edit/{id}")
    public String editVehicleForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.findById(id).orElseThrow();
        model.addAttribute("vehicle", vehicle);
        return "admin-vehicle-form";
    }

    @PostMapping("/admin/vehicle/delete/{id}")
    public String deleteVehicle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        vehicleService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Vehicle deleted.");
        return "redirect:/admin";
    }
}
