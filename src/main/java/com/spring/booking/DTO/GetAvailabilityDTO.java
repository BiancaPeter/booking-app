package com.spring.booking.DTO;

import java.time.LocalDateTime;

public class GetAvailabilityDTO {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer numberOfPersons;

    public GetAvailabilityDTO(LocalDateTime startDate, LocalDateTime endDate, Integer numberOfPersons) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfPersons = numberOfPersons;
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

    public Integer getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(Integer numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }
}
