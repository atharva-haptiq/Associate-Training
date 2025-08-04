package util;

import entity.DriverPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class PassengerUtil implements Runnable {
    private final int passengerId;
    private final DriverPool driverPool;
    private final Semaphore driverSemaphore;
    private final CountDownLatch latch;

    public PassengerUtil(int passengerId, DriverPool driverPool, Semaphore driverSemaphore, CountDownLatch latch) {
        this.passengerId = passengerId;
        this.driverPool = driverPool;
        this.driverSemaphore = driverSemaphore;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            driverSemaphore.acquire();
            String driver = driverPool.allocateDriver();
            if (driver != null) {
                System.out.println("Passenger " + passengerId + " → Driver " + driver);

                Thread.sleep((long) (Math.random() * 1000));

                driverPool.releaseDriver(driver);
            } else {
                System.out.println("Passenger " + passengerId + " → No Driver Available");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            driverSemaphore.release();
            latch.countDown();
        }
    }
}

