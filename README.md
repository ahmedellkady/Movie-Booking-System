# Movie Booking System

A JavaFX-based movie booking system for managing movies, bookings, and users. This project demonstrates the use of **multithreading** to handle concurrent seat bookings and prevent race conditions.

---

## Features
- **User Authentication**: Users can log in or register as new users.
- **Admin Dashboard**: Admins can manage movies, bookings, and users.
- **Movie Management**: Add, delete, and view movies with details like title, description, dates, and showtimes.
- **Booking Management**: Book seats for a selected movie, date, and time.
- **Booking History**: View and cancel bookings.
- **User Management**: Assign admin privileges to users.

---

## Technologies Used
- **JavaFX**: For the user interface.
- **MySQL**: For the database.
- **JDBC**: For database connectivity.
- **Maven**: For dependency management (if applicable).
- **Multithreading**: To handle concurrent seat bookings and prevent race conditions.

---

## Multithreading and Concurrency
This project addresses **concurrency issues** and **race conditions** that can occur when multiple users try to book the same seat simultaneously. Here's how the problem was solved:

### **Problem: Race Condition in Seat Booking**
- When multiple users try to book the same seat at the same time, a race condition can occur, leading to overbooking or inconsistent data.

### **Solution: Thread-Safe Seat Booking**
- A **thread-safe** solution was implemented using the following techniques:
  1. **Synchronized Methods**:
     - Critical sections of the code (e.g., booking a seat) were made thread-safe using the `synchronized` keyword.
     - Example:
       ```java
       public synchronized boolean bookSeat(String seatNumber) {
           if (!isSeatBooked(seatNumber)) {
               bookSeatInDatabase(seatNumber);
               return true;
           }
           return false;
       }
       ```

  2. **ConcurrentHashMap**:
     - A `ConcurrentHashMap` was used to store booked seats in memory, ensuring thread-safe access to shared data.
     - Example:
       ```java
       private ConcurrentHashMap<String, Boolean> bookedSeats = new ConcurrentHashMap<>();

       public boolean bookSeat(String seatNumber) {
           return bookedSeats.putIfAbsent(seatNumber, true) == null;
       }
       ```

  3. **Thread-Safe Database Operations**:
     - Database operations (e.g., inserting a booking) were made thread-safe by using transactions and proper locking mechanisms in SQL.

  4. **Periodic Seat Updates**:
     - A background thread periodically updates the seat availability in the UI to reflect real-time changes.
     - Example:
       ```java
       Thread seatUpdateThread = new Thread(() -> {
           while (true) {
               Platform.runLater(() -> updateSeatAvailability());
               try {
                   Thread.sleep(5000); // Update every 5 seconds
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       });
       seatUpdateThread.start();
       ```

---

## Project Structure
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── moviebooking/
│   │           ├── controller/          # Controller classes
│   │           ├── model/               # Model classes
│   │           ├── service/             # Service classes
│   │           ├── dao/                 # DAO classes
│   │           └── util/                # Utility classes
│   └── resources/
│       ├── fxml/                        # FXML files for UI
│       ├── img/                         # Images used in the application
│       └── other resources (e.g., CSS, properties files)

---

## Screenshots
