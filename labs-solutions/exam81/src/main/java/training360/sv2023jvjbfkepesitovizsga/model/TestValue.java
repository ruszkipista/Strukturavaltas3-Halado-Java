package training360.sv2023jvjbfkepesitovizsga.model;

public enum TestValue {
    PERFECT(75), AVERAGE(50), NOT_PASSED(0);

    private double percentage;

    private TestValue(double percentage){
        this.percentage = percentage;
    }

    public double getPercentage() {
        return this.percentage;
    }
}
