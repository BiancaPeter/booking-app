package com.spring.booking.controller;

import com.spring.booking.DTO.AddReservationDTO;
import com.spring.booking.model.Reservation;
import com.spring.booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public Reservation addReservation(@RequestBody AddReservationDTO addReservationDTO){
        return reservationService.addReservation(addReservationDTO);
    }
}
