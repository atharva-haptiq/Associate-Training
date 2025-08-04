package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DriverPool {
    private final Queue<String> availableDrivers = new LinkedList<>();
    private final Lock lock = new ReentrantLock();

    public DriverPool(List<String> drivers) {
        availableDrivers.addAll(drivers);
    }

    public String allocateDriver() {
        lock.lock();
        try {
            return availableDrivers.poll();
        } finally {
            lock.unlock();
        }
    }

    public void releaseDriver(String driverName) {
        lock.lock();
        try {
            availableDrivers.offer(driverName);
        } finally {
            lock.unlock();
        }
    }
}

