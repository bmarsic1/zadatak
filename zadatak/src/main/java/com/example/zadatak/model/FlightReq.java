package com.example.zadatak.model;


import org.springframework.format.annotation.DateTimeFormat;
import java.util.Objects;

public class FlightReq implements Cloneable {


    private String originLocationCode;

    private String destinationLocationCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String departureDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String returnDate;

    private Integer adults;

    private String currencyCode;

    public String getOriginLocationCode() {
        return originLocationCode;
    }

    public void setOriginLocationCode(String originLocationCode) {
        this.originLocationCode = originLocationCode;
    }

    public String getDestinationLocationCode() {
        return destinationLocationCode;
    }

    public void setDestinationLocationCode(String destinationLocationCode) {
        this.destinationLocationCode = destinationLocationCode;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "FlightReq{" +
                "originLocationCode='" + originLocationCode + '\'' +
                ", destinationLocationCode='" + destinationLocationCode + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", adults=" + adults +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FlightReq flightReq = (FlightReq) o;
        return Objects.equals(originLocationCode, flightReq.originLocationCode) && Objects.equals(destinationLocationCode, flightReq.destinationLocationCode) && Objects.equals(departureDate, flightReq.departureDate) && Objects.equals(returnDate, flightReq.returnDate) && Objects.equals(adults, flightReq.adults) && Objects.equals(currencyCode, flightReq.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originLocationCode, destinationLocationCode, departureDate, returnDate, adults, currencyCode);
    }

    @Override
    public FlightReq clone() {
        try {
            FlightReq clone = (FlightReq) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
