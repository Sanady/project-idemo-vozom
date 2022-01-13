package com.example.projekatidemovozom.models;

public class TimestampModel {
    public int arrival;
    public int departure;
    public RouteModel route;
    public String train;

    public TimestampModel() { }

    public TimestampModel(int arrival, int departure, String train, RouteModel route) {
        this.arrival = arrival;
        this.departure = departure;
        this.train = train;
        this.route = route;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public int getDeparture() {
        return departure;
    }

    public void setDeparture(int departure) {
        this.departure = departure;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public RouteModel getRoute() {
        return route;
    }

    public void setRoute(RouteModel route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "TimestampModel{" +
                "arrival='" + arrival + '\'' +
                ", departure='" + departure + '\'' +
                ", route=" + route +
                ", train='" + train + '\'' +
                '}';
    }
}
