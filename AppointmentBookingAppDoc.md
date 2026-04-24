# Appointment Booking System

## Overview
The **Appointment Booking System** is a web-based application designed to facilitate scheduling appointments between clients and specialists. The system ensures efficient booking management, availability validation, and appointment status tracking.

## Use Cases

### 1️⃣ Retrieve List of Available Treatments
- **Actor:** Client
- **Description:** A client can retrieve a list of available treatments, optionally filtering by specialist or treatment name.
- **Flow:**
  1. The client requests a list of treatments.
  2. The system retrieves available treatments from the database.
  3. The client receives the list of treatments, optionally filtered.

### 2️⃣ Retrieve Treatment Details
- **Actor:** Client
- **Description:** A client can view the details of a specific treatment, including information about the assigned specialist.
- **Flow:**
  1. The client selects a treatment.
  2. The system retrieves detailed information about the treatment, including the specialist assigned to it.
  3. The client receives the treatment details.

### 3️⃣ Specialist Creates a New Treatment
- **Actor:** Specialist
- **Description:** A specialist can create a new treatment, defining its name and duration.
- **Flow:**
  1. The specialist submits a request to create a treatment.
  2. The system stores the treatment in the database.
  3. The specialist receives a confirmation that the treatment was created.

### 4️⃣ Client Books an Appointment
- **Actor:** Client
- **Description:** A client selects a treatment and schedules an appointment.
- **Flow:**
  1. The client chooses a treatment and provides a date and time.
  2. The system checks if the specialist is available.
  3. If available, the system saves the appointment and confirms the booking.
  4. If not available, the client receives an error message.

### 5️⃣ Validate Scheduling Conflicts
- **Actor:** Client
- **Description:** The system ensures no two appointments overlap for a specialist.
- **Flow:**
  1. The client requests an appointment.
  2. The system checks for existing appointments at the same time.
  3. If a conflict exists, the client is notified.
  4. If no conflict exists, the appointment proceeds to booking.

### 6️⃣ Client Checks Their Appointments
- **Actor:** Client
- **Description:** A client can retrieve a list of their scheduled appointments.
- **Flow:**
  1. The client requests a list of their appointments.
  2. The system retrieves and returns the appointments.

### 7️⃣ Client Cancels an Appointment
- **Actor:** Client
- **Description:** A client can cancel a previously scheduled appointment.
- **Flow:**
  1. The client selects an appointment to cancel.
  2. The system updates the appointment status to "CANCELLED."
  3. The client receives confirmation.

### 8️⃣ Specialist Marks an Appointment as Completed
- **Actor:** Specialist
- **Description:** A specialist can mark an appointment as completed after the service has been provided.
- **Flow:**
  1. The specialist selects an appointment to mark as completed.
  2. The system updates the appointment status to "COMPLETED."
  3. The specialist receives confirmation.

### 9️⃣ Client Retrieves Appointment History
- **Actor:** Client
- **Description:** A client can view past appointments (both completed and cancelled).
- **Flow:**
  1. The client requests a list of past appointments.
  2. The system retrieves and displays the history.

### 🔟 Specialist Retrieves Their Appointment History
- **Actor:** Specialist
- **Description:** A specialist can view a list of their past appointments.
- **Flow:**
  1. The specialist requests a list of past appointments.
  2. The system retrieves and displays the history.

### 1️⃣1️⃣ Checking Appointment Availability
- **Actor:** Client
- **Description:** The system checks if a specialist is available for a given time slot.
- **Flow:**
  1. The client requests availability for a specific specialist and time.
  2. The system checks for existing appointments at the given time.
  3. If no conflicting appointments exist, the system returns "Available."
  4. If a conflict exists, the system returns "Not Available."

## Sequence Diagram
#### For a visual representation of the use cases, see the [sequence diagram](appointment_booking_diagram.puml).

## Conclusion
This document outlines the key functionalities and user interactions of the Appointment Booking System. The system provides an efficient way to manage appointments between clients and specialists while ensuring scheduling conflicts are avoided.

