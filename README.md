# 🏨 Hotel Reservation System — CSE241

**Ain Shams University | Faculty of Engineering**
**CSE241 – Object-Oriented Computer Programming**
**2nd Semester 2025/2026 | Team 13 | Group 4 | Section 7**

---

## 📌 Project Overview

A Desktop Hotel Reservation System built in Java applying core OOP principles.
The system manages all core hotel operations including guest management, room administration, reservations, invoicing, and staff operations.

The system supports three types of users:
- **Guests** — register, login, browse rooms, make/cancel reservations, checkout, and pay invoices
- **Admins** — full CRUD on rooms, room types, and amenities, and register receptionists
- **Receptionists** — manage guest check-in and check-out operations

---

## 👥 Team Members

| Name | Student ID | Contribution |
|------|-----------|--------------|
| Seif Eldin Ahmed | 25P0033 | Admin, RoomType, Room, HotelDatabase, Amenity, Main (Admin & Guest Dashboard) |
| Abdullah Haytham Mohamadi | 25P0015 | HotelDatabase, Guest, Reservation, Room, Invoice, Exception classes |
| Mohamed Osama Mohamed | 25P0109 | Interfaces, Main (Guest Dashboard, Receptionist Dashboard) |
| Ahmed Hany Ahmed | 25P0449 | Validation |
| Abdelrhman Mahmoud Hussien | 25P0428 | Reservation, Guest, Invoice, RoomPreference, Receptionist |

---

## 🚀 How to Run

1. Clone the repo:
```
git clone https://github.com/seifahmed01376-afk/hotel-reservation-system.git
```
2. Open in **IntelliJ IDEA**
3. Make sure **JDK 17+** is configured
4. Run `Main.java` located in `src/com/hotel/main/`

---

## 📁 Project Structure

```
src/
└── com/hotel/
    ├── models/         ← Guest, Room, RoomType, Amenity, Staff, Admin,
    │                     Receptionist, Reservation, Invoice, RoomPreference
    ├── enums/          ← Gender, Role, ReservationStatus, PaymentMethod
    ├── interfaces/     ← Payable, Manageable
    ├── database/       ← HotelDatabase (in-memory data store)
    ├── Validation/     ← Validator (centralized input validation)
    ├── Exceptions/     ← 8 custom exception classes
    └── main/           ← Main.java (entry point)
```

---

## 🗂️ Milestones

- [x] **Milestone 1** — Backend OOP (Due April 26, 2026) ✅
- [ ] **Milestone 2** — JavaFX GUI + Threading + Networking (Due May 6, 2026)

---

## 🛠️ Technologies Used

- Java 17+
- JavaFX *(Milestone 2)*
- Git & GitHub

---

## 🔑 OOP Concepts Applied

| Technique | Where Used |
|-----------|-----------|
| **Inheritance** | Admin, Receptionist extend Staff |
| **Interfaces** | Guest implements Payable, Admin implements Manageable |
| **Abstraction** | Staff is abstract |
| **Encapsulation** | All fields private with getters/setters + validation |
| **Polymorphism** | Payable.pay(), Manageable CRUD methods |
| **Enums** | Gender, Role, ReservationStatus, PaymentMethod |
| **Static Factory Methods** | Guest.register(), Receptionist.Register() |
| **Custom Exceptions** | 8 custom exception classes |
| **Composition** | Room has RoomType and ArrayList<Amenity> |
| **Aggregation** | Reservation has Guest and Room references |

---

## 🔄 Reservation Lifecycle

```
PENDING → CONFIRMED → CHECKED_IN → COMPLETED
                ↓
           CANCELLED
```

| Status | Triggered By |
|--------|-------------|
| PENDING | Guest.makeReservation() |
| CONFIRMED | HotelDatabase.makeReservation() |
| CHECKED_IN | Receptionist.checkIn() |
| COMPLETED | Guest.checkOut() or Receptionist.checkout() |
| CANCELLED | Guest.cancelReservation() |

---

## ⚠️ Custom Exceptions

| Exception | When Thrown |
|-----------|------------|
| InvalidUserInformationException | Invalid username, password, or duplicate username |
| InvalidRoomDataException | Invalid room number, price, or duplicate room |
| ReservationNotFoundException | Reservation ID not found |
| InvalidDateRangeException | Check-out before check-in, or past dates |
| InvalidReservationStatusException | Invalid status transition |
| InvalidInvoiceAmountException | Invoice amount is zero or negative |
| InvalidPaymentException | Insufficient balance or invoice already paid |
| RoomNotAvailableException | Attempting to book an already-booked room |

---

## ⚙️ Default Credentials (Dummy Data)

| Role | Username | Password |
|------|----------|----------|
| Guest | seif | seif2007 |
| Guest | Abdo | Abdo2007 |
| Guest | sara | sara123 |
| Admin | Admin1 | Admin123 |
| Receptionist | Receptionist1 | Receptionist123 |

---

## 📋 Limitations

- Data is stored **in-memory only** and is lost when the application is closed
- Room availability uses a simple boolean flag — no date-based overlap checking
- No role-based access control at runtime — relies on user menu selection

---

## 📚 References

- CSE241 Course Lecture Slides – Ain Shams University, Faculty of Engineering, 2025/2026

---

*Instructor: Dr. Mahmoud Khalil / Eng. Mazen*