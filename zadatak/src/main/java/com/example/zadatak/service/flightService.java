package com.example.zadatak.service;

import com.example.zadatak.model.Flight;
import com.example.zadatak.model.FlightReq;

import java.util.List;

public interface flightService {

    public List<Flight> getFlights(FlightReq flightReq);
}
