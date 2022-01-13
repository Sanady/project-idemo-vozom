package com.example.projekatidemovozom.models;

public class RouteModel {
    public String cityFrom;
    public String cityTo;
    public String distance;
    public String travelTime;

    public RouteModel() {
    }

    public RouteModel(String cityFrom, String cityTo, String distance, String travelTime) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.distance = distance;
        this.travelTime = travelTime;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    @Override
    public String toString() {
        return "RouteModel{" +
                "cityFrom='" + cityFrom + '\'' +
                ", cityTo='" + cityTo + '\'' +
                ", distance='" + distance + '\'' +
                ", travelTime='" + travelTime + '\'' +
                '}';
    }
}
