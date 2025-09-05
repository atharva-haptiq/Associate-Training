import entity.DriverPool;
import util.DriverGenerator;

import java.util.List;
public class Main {
    public static void main(String[] args) {
        int numDrivers = 10;
        int numPassengers = 20;

        List<String> drivers = DriverGenerator.generateDriverNames(numDrivers);
        DriverPool driverPool = new DriverPool(drivers);

        for (int i = 1; i <= numPassengers; i++) {
            String driver = driverPool.allocateDriver();
            if (driver != null) {
                System.out.println("Passenger " + i + " → Driver " + driver);

                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            } else {
                System.out.println("Passenger " + i + " → No Driver Available");
            }
        }

        System.out.println("All ride requests processed.");
    }
}
