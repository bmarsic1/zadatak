package com.example.zadatak.model;



public class Flight {

    private FlightReq flightReq;

    private Integer numOfTransfers;

    private Integer numOfTransfersBack;

    private float price;

    public Flight() {
    }

    public Flight(FlightReq flightReq, Integer numOfTransfers, Integer numOfTransfersBack, float price) {
        this.flightReq = flightReq;
        this.numOfTransfers = numOfTransfers;
        this.numOfTransfersBack = numOfTransfersBack;
        this.price = price;
    }

    public FlightReq getFlightReq() {
        return flightReq;
    }

    public void setFlightReq(FlightReq flightReq) {
        this.flightReq = flightReq;
    }

    public Integer getNumOfTransfers() {
        return numOfTransfers;
    }

    public void setNumOfTransfers(Integer numOfTransfers) {
        this.numOfTransfers = numOfTransfers;
    }

    public Integer getNumOfTransfersBack() {
        return numOfTransfersBack;
    }

    public void setNumOfTransfersBack(Integer numOfTransfersBack) {
        this.numOfTransfersBack = numOfTransfersBack;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
