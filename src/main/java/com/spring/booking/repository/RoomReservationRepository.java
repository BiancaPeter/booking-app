package com.spring.booking.repository;

import com.spring.booking.model.Room;
import com.spring.booking.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    void deleteAllByRoom(Room room);

}
