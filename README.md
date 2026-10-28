# MiniVMS

**MiniVMS** is a lightweight, minimalistic Vendor Management System (VMS) built with **Spring Boot** and **Thymeleaf**. It is designed to demonstrate the core workflow of managing contract labor, from job creation to time sheet approval, using clean role-based permissions.

---

## 🛠 Tech Stack

* **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security
* **Frontend:** Thymeleaf, HTML5, CSS3
* **Database:** MySQL
* **Build Tool:** Maven

---

## 👥 User Roles & Responsibilities

MiniVMS operates on three distinct user roles:

| Role | Purpose | Key Responsibilities |
| :--- | :--- | :--- |
| **Buyer** | Client / Organization needing external labor. | Creates job postings, selects candidates, issues Work Orders, and approves/rejects time sheets. |
| **Supplier** | Staffing agency / Vendor supplying talent. | Manages candidate (Job Seeker) profiles and submits candidates to open Job Postings. |
| **Worker** | Contractor performing the work. | Linked directly to an active Work Order to log and submit weekly time sheets for approval. |

---

## 🚀 Getting Started

### Prerequisites
* Java JDK 17+
* Maven 3.x
* Relational Database (MySQL or PostgreSQL)

### Installation & Setup

## 1. Clone the repository: 
```bash
 git clone https://github.com/VictorAngelov1993/MiniVMS.git
```

## 2. Configure Database Connection

Update your database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/minivms_db?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD
```
## 3. Build and Run the Application

```bash
mvn spring-boot:run
```
## 4. Access the Application
```bash
http://localhost:8080
```

## 📖 Complete Workflow Guide (Testing the App)

While in production each role is handled by different users, you can easily test the entire lifecycle locally by creating accounts for each role.

### Phase 1: Buyer Creates a Job Posting
1. Navigate to the **Register** page and select the **Buyer** role and Register.
2. Log in with your **Buyer** account.
3. Go to **Job Postings** and click **Create Job Posting**.
4. Fill out the details (e.g., *Title: Java Backend Developer*, Description, Start/End Dates, Pay Rate) and submit.

### Phase 2: Supplier Submits a Candidate
1. Log out and **Register** a new account selecting the **Supplier** role.
2. Log in with your **Supplier** account.
3. Go to **Job Seekers** -> **Create Job Seeker** (add candidate details such as name, contact info, and skill set).
4. Go to **Job Postings**, select the Buyer’s active job posting, and click **Submit Candidate**.
5. Choose the newly created Job Seeker and submit.

### Phase 3: Buyer Issues a Work Order
1. Log out and log back in as the **Buyer**.
2. Open the **Job Posting** and select the submitted candidate.
3. Click **Create Work Order** (adjust pay rate or notes if needed) and confirm.
4. **Note down the generated Work Order ID** (e.g., `WO-001`).

### Phase 4: Worker Registration & Time Sheet Submission
1. Log out and navigate to **Register**.
2. Select the **Worker** role. An additional **Work Order ID** field will appear.
3. Enter the Work Order ID (`WO-001`) and complete registration.
4. Log in as the **Worker** and go to **Time Sheets**.
5. Select the current time sheet, log your worked hours, and click **Submit for Approval**.

### Phase 5: Buyer Approves the Time Sheet
1. Log back in as the **Buyer**.
2. Navigate to **Time Sheets** to view all pending submissions.
3. Click **View/Edit** on the Worker's submitted time sheet and click **Approve** (or **Reject**).

