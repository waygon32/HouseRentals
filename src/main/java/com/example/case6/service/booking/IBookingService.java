package com.example.case6.service.booking;

import com.example.case6.model.Booking;
import com.example.case6.service.IGeneralService;

import java.util.List;

public interface IBookingService extends IGeneralService<Booking> {
    List<Booking> getBookingsByHouseHouseId(Long id);
}
