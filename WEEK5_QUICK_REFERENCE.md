# Week 5 Interfaces - Quick Reference Guide

## **Four Interfaces in Your Project**

### **1️⃣ Displayable**
```java
// What: Classes that can show their information
// Who implements: Patient, Doctor, Appointment, TimeSlot, Schedule

public interface Displayable {
    void display();        // Full detailed display
    String getSummary();   // Brief one-line summary
}

// Example:
Patient p = new Patient("John", 25, "Male", "1999-01-01", "0123456789");
p.display();              // Prints detailed patient info
String brief = p.getSummary();  // Returns "John (Age: 25, Appointments: 0)"
```

---

### **2️⃣ Bookable**
```java
// What: Objects that can be booked/released with availability tracking
// Who implements: TimeSlot, Schedule

public interface Bookable {
    boolean isAvailable(); // Is it available?
    void book();          // Mark as booked
    void release();       // Mark as available again
}

// Example:
TimeSlot slot = new TimeSlot("2024-12-15", "09:00", "10:00");
if (slot.isAvailable()) {
    slot.book();  // Patient books it
}
slot.release();   // Cancel - slot becomes available
```

---

### **3️⃣ StatusManageable**
```java
// What: Objects with status that can change
// Who implements: Appointment

public interface StatusManageable {
    AppointmentStatus getStatus();
    void setStatus(AppointmentStatus status);
    boolean hasStatus(AppointmentStatus status);
}

// Example:
Appointment apt = new Appointment(...);
apt.setStatus(AppointmentStatus.COMPLETED);
if (apt.hasStatus(AppointmentStatus.COMPLETED)) {
    System.out.println("Appointment finished");
}
```

---

### **4️⃣ Searchable**
```java
// What: System classes that manage searchable collections
// Who implements: HospitalSystem

public interface Searchable {
    Object searchByName(String name);
    List<?> getAllRecords();
    int getRecordCount();
}

// Example:
HospitalSystem hospital = new HospitalSystem();
Object found = hospital.searchByName("John");     // Patient or Doctor
int total = hospital.getRecordCount();             // Total records
```

---

## **Which Classes Implement Which Interfaces**

| Class | Interfaces | Purpose |
|-------|-----------|---------|
| Patient | Displayable | Display patient information |
| Doctor | Displayable | Display doctor information |
| Appointment | Displayable, StatusManageable | Display appointment; manage status |
| TimeSlot | Displayable, Bookable | Display slot; manage booking |
| Schedule | Displayable, Bookable | Display schedule; manage availability |
| HospitalSystem | Searchable | Search and manage records |

---

## **Benefits of These Interfaces**

### **Consistency** ✅
- All objects with `display()` behave the same way
- All bookable objects have `book()` and `release()`

### **Polymorphism** ✅
```java
ArrayList<Displayable> items = new ArrayList<>();
items.add(patient);
items.add(doctor);
items.add(appointment);

for (Displayable item : items) {
    item.display();  // Each displays itself differently!
}
```

### **Flexibility** ✅
- Easy to add new classes implementing existing interfaces
- Easy to add new interfaces for new behaviors

### **Clarity** ✅
- Clear what methods available on each object
- Type-safe (compiler checks implementations)

---

## **Common Operations**

### **Display Everything**
```java
ArrayList<Displayable> objects = new ArrayList<>();
// Add various objects...

for (Displayable obj : objects) {
    obj.display();
    System.out.println("---");
}
```

### **Manage Bookings**
```java
TimeSlot slot = new TimeSlot("2024-12-15", "09:00", "10:00");

if (slot.isAvailable()) {
    slot.book();
    System.out.println("Successfully booked!");
}

// Later:
slot.release();  // Cancel and free up slot
```

### **Track Status**
```java
Appointment apt = new Appointment(...);

// Check status
if (apt.hasStatus(AppointmentStatus.BOOKED)) {
    apt.setStatus(AppointmentStatus.COMPLETED);
}
```

### **Search System**
```java
// Find someone
Object person = hospital.searchByName("John");

// Get statistics
System.out.println("Total records: " + hospital.getRecordCount());

// Get all records
List<?> all = hospital.getAllRecords();
```

---

## **Interview Questions You Should Know**

**Q: What's an interface?**
A: An interface is a contract that defines what methods a class must have.

**Q: Why use interfaces?**
A: Consistency, flexibility, and polymorphism.

**Q: Can you create an object from an interface?**
A: No. `new Displayable()` is an error. Must use implementing class like `new Patient(...)`.

**Q: Can a class implement multiple interfaces?**
A: Yes. `public class Appointment implements Displayable, StatusManageable`

**Q: What's the @Override annotation?**
A: It tells the compiler you're implementing an interface method. Helps catch mistakes.

**Q: How is interface different from abstract class?**
A: Interface: just method signatures. Abstract class: can have method bodies and constructors.

---

## **File Locations**

```
Hospital-Management-System-Project/
├── Model/
│   ├── Displayable.java           (Interface)
│   ├── Bookable.java               (Interface)
│   ├── StatusManageable.java        (Interface)
│   ├── Searchable.java              (Interface)
│   ├── Patient.java                 (implements Displayable)
│   ├── Doctor.java                  (implements Displayable)
│   ├── Appointment.java             (implements Displayable, StatusManageable)
│   ├── TimeSlot.java                (implements Displayable, Bookable)
│   ├── Schedule.java                (implements Displayable, Bookable)
│   ├── AppointmentStatus.java
│   └── ... (other model classes)
├── Main/
│   ├── HospitalSystem.java          (implements Searchable)
│   ├── Main.java
│   └── ...
└── JAVA_LEARNING_GUIDE.md           (Updated with interfaces section)
```

---

## **Week 4 Feedback → Week 5 Solutions**

| Week 4 Feedback | Week 5 Solution | Interface |
|-----------------|-----------------|-----------|
| Too much logic spread | Clear boundaries | All interfaces |
| Status flow unclear | Explicit methods | StatusManageable |
| Display methods scattered | Consistent naming | Displayable |
| System searching unclear | Standard interface | Searchable |
| Booking logic complex | Clear availability | Bookable |

---

## **Cheat Sheet - What Each Interface Does**

```
Displayable:
├─ display()     ← Show full details
└─ getSummary()  ← Show brief summary

Bookable:
├─ isAvailable() ← Check if available
├─ book()        ← Mark as booked
└─ release()     ← Make available again

StatusManageable:
├─ getStatus()   ← Get current status
├─ setStatus()   ← Change status
└─ hasStatus()   ← Check specific status

Searchable:
├─ searchByName()  ← Find by name
├─ getAllRecords() ← Get all items
└─ getRecordCount() ← Count all items
```

---

## **Tips for Presentation**

1. **Explain what interfaces are** - Contracts/agreements
2. **Show why they matter** - Consistency and flexibility
3. **Demonstrate usage** - Code examples
4. **Show polymorphism** - Same method, different results
5. **Explain benefits** - Maintenance, extensibility
6. **Connect to hospital** - How it improves the system
7. **Future possibilities** - What interfaces could be added next

---

## **Success Criteria** ✅

- [x] At least two interfaces created (4 created)
- [x] At least three classes implement interface (6 implement)
- [x] Display behavior organized in interface
- [x] Booking/scheduling interface added
- [x] Status management interface added
- [x] Search interface added
- [x] All code compiles without errors
- [x] Interfaces properly documented

---

**You're ready for Week 5! 🚀**
