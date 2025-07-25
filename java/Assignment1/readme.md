# 📚 Student Report Card - CLI Application

A simple Java-based Command Line Interface (CLI) application for managing student records. Users can:

- Add students
- Enter subject-wise marks
- Calculate results (percentage, average, pass/fail)
- Export student report as a text file

---

## 🚀 Features

- Add student by name
- Record marks for Java, JavaScript, SQL, and DevOps
- Compute average, percentage, and pass/fail status
- Save student report to a `.txt` file
- Input validation and user-friendly prompts

---

## 📁 Folder Structure
student-report-cli/
├── Main.java
├── studentReports/
│   ├── Student.java
│   ├── StudentService.java
│   └── ReportUtil.java


| File | Description |
|------|-------------|
| `Main.java` | Entry point with interactive CLI menu |
| `Student.java` | Model class for student data |
| `StudentService.java` | Handles student management and logic |
| `ReportUtil.java` | Utility to export reports as text files |

---

## 🧠 Code Logic Overview

### `Main.java`

Handles user input and presents a menu with options:
1. **Add Student** – Creates and stores a new student
2. **Enter Marks** – Accepts subject-wise marks for an existing student
3. **Calculate Result** – Computes total, average, percentage, and pass/fail status
4. **Save Report** – Saves the student's report to a text file
5. **Exit** – Terminates the application

### `Student.java`

Data model containing:
- Student name
- Map of subject-wise marks
- Calculated average, percentage, and pass/fail flag

### `StudentService.java`

Core service logic:
- `addStudent(name)` – Adds a new student to the list
- `getStudentByName(name)` – Fetches student object
- `registerMarks(student, marks)` – Adds validated marks
- `calculateResult(student)` – Computes:
    - Total marks out of 400
    - Average
    - Percentage
    - Pass if percentage ≥ 35%

### `ReportUtil.java`

Utility class to:
- Write student data to a file in human-readable format
- Handles file I/O exceptions gracefully

---

## 🧪 Sample CLI Usage

```bash
-----------------------Student Report Menu-----------------------
1. Add Student
2. Enter Marks
3. Calculate Result
4. Exit
5. Save Report
Enter your choice: 1
Enter Student's Name: John Doe
Student John Doe added successfully!
