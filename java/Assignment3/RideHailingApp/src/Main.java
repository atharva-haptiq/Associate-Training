import entity.DriverPool;
import util.DriverGenerator;
import util.PassengerUtil;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numDrivers = 10;
        int numPassengers = 20;

        List<String> drivers = DriverGenerator.generateDriverNames(numDrivers);
        DriverPool driverPool = new DriverPool(drivers);

        Semaphore driverSemaphore = new Semaphore(numDrivers);
        CountDownLatch latch = new CountDownLatch(numPassengers);
        ExecutorService executor = Executors.newFixedThreadPool(numPassengers);

        for (int i = 1; i <= numPassengers; i++) {
            executor.submit(new PassengerUtil(i, driverPool, driverSemaphore, latch));
        }

        latch.await();
        executor.shutdown();
        System.out.println("All ride requests processed.");
    }
}
