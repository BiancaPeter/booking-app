package com.spring.booking.DTO;

import java.time.LocalDateTime;

public class AvailabilityOfHotelRoomsDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long idHotel;

    public AvailabilityOfHotelRoomsDTO(LocalDateTime startDate, LocalDateTime endDate, Long idHotel) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.idHotel = idHotel;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Long idHotel) {
        this.idHotel = idHotel;
    }
}
