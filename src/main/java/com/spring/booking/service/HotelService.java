package com.spring.booking.service;

import com.spring.booking.model.Hotel;
import com.spring.booking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    private HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel addHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
