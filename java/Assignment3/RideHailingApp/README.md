# 🚕 Ride-Hailing App Backend Simulation (Java + Concurrency)

A Java-based console simulation of a **ride-hailing backend system** where multiple passengers attempt to book rides with a limited number of drivers. This project demonstrates proper usage of **Java concurrency** features to ensure **thread-safe** driver allocation and avoid race conditions or deadlocks.

---

## 📌 Problem Statement

Simulate a ride-hailing app backend where multiple passengers try to book rides with limited drivers available. You must ensure safe, concurrent access to drivers.

### 🧪 Requirements

- 10 concurrent **driver threads**
- 20 concurrent **passenger threads**
- Use of `synchronized`, `ReentrantLock`, or `Semaphore`
- Proper match logging: `Passenger 1 → Driver A`
- Use of `ExecutorService` and `CountDownLatch` for coordination
- Ensure **thread-safe access** and **avoid deadlocks**

---

## 🧱 Project Structure

```
src/
├── entity/
│   └── DriverPool.java          # Thread-safe driver pool using ReentrantLock
│
├── util/
│   ├── DriverGenerator.java     # Generates driver names (Driver A, B, ...)
│   └── PassengerUtil.java       # Runnable for passenger ride request handling
│
└── Main.java                    # Entry point, launches passenger threads
```

---

## 🧠 Concurrency Features Demonstrated

| Feature            | Description |
|--------------------|-------------|
| `ReentrantLock`    | Ensures exclusive access to shared driver pool |
| `CountDownLatch`   | Waits for all passengers to finish |
| `ExecutorService`  | Thread pool to manage passenger threads |
| `Thread.sleep()`   | Simulates ride time |
| `Thread-safe Queue`| `LinkedList` wrapped with locks for safety |

---

## 🔧 How It Works

1. **Drivers** are added to a shared pool.
2. **20 Passenger threads** try to book a driver.
3. If a driver is available:
    - It's allocated to the passenger.
    - Match is logged: `Passenger X → Driver Y`
    - After ride (simulated delay), driver is released.
4. If no driver is available, the passenger is notified.

---

## ▶️ Sample Output

```
Passenger 1 → Driver A
Passenger 2 → Driver B
...
Passenger 11 → No Driver Available
...
Passenger 19 → Driver E
Passenger 20 → No Driver Available
All ride requests processed.
```

---

## 🚀 How to Run

### 1. Compile

```bash
javac Main.java entity/*.java util/*.java
```

### 2. Run

```bash
java Main
```

---

## 📌 Main Classes Explained

### `DriverPool.java`
- Manages available drivers in a queue
- Uses `ReentrantLock` for thread-safe access
- Provides `allocateDriver()` and `releaseDriver()`

### `PassengerUtil.java`
- Implements `Runnable`
- Tries to book a driver from `DriverPool`
- Waits for a while, then releases driver
- Uses `CountDownLatch` to notify completion

### `Main.java`
- Initializes drivers and passengers
- Submits passenger tasks using `ExecutorService`
- Waits for all rides using `CountDownLatch`

---

## 💡 Future Enhancements

- Retry if no driver is available
- Use `Semaphore` for limiting concurrent access
- Add rating or feedback simulation

---

## 🧑‍💻 Author

Built to demonstrate multithreading and real-time simulations using core Java.

---

## 📄 License

Licensed under [MIT License](LICENSE)