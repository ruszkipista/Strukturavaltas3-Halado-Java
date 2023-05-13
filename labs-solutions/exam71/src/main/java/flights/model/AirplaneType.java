package flights.model;

public enum AirplaneType {
    BOEING_747(600), BOEING_787(200), AIRBUS_A380(800), AIRBUS_A340(300);

    private int numberOfPassengers;

    private AirplaneType(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getNumberOfPassengers() {
        return this.numberOfPassengers;
    }
}
