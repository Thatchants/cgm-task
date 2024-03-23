# Quarkus Project CGM - Andrew Book 🚀

Quarkus and Panache allow for a quick development experience!

Changes made are immediately reflected in the development server

Components are accessed statically so there is no need for reflection like in spring boot 🛠️

I'm running the postgres db on a docker container connected to a volume to persist information through application restarts.

The postgresql database was initialized with the script at `/scripts/load-database.sql`

<details>
<summary><strong>Structure:</strong></summary>

### Model
- **Patient** and **Visit**
- Each represent a table in a PostgreSQL database
- There is a one **Patient** to many **Visit** relationship
- They take advantage of the PanacheEntity class
  - Queries are defined at this level. (Example: find all visits by patient id. Defined in Visits.java):
    ```java
    public static List<Visit> findAllByPatientId(UUID patientId){
        return find("patient.patientId", patientId).list();
    }
    ```

### Controller
- REST endpoint definitions
- PatientController.java and VisitController.java

### DTO
- In this project, I use Jackson to convert Java objects to JSON
- If an object (Visit or Patient) is accessed directly by ID, the entire object is returned.
  When looking for many objects (a list of all Patients), not all information is important.
  For this reason, I created some ListDto classes. These classes don't have all the information about an object.
  Only the important parts. Such as name and ID. The ID can be used to access the full object if desired.
</details>

<details>
<summary><strong>Endpoints:</strong></summary>

#### 1. GET /patient
**Response 200:**
```json
[
    {
        "firstName": "John",
        "lastName": "Doe",
        "patientId": "6179f94f-eb56-4e63-a00d-7c45d78c1ff2"
    },
    {
        "firstName": "Jane",
        "lastName": "Smith",
        "patientId": "756c3467-6950-449c-9287-e2b788378a53"
    },
    {
        "firstName": "Alice",
        "lastName": "Johnson",
        "patientId": "c6e67075-b22c-4ad9-9804-15b3dc2a3d3c"
    }
]
```

#### 2. GET /patient/{patientId}
**Response 200:**
```json
{
    "patientId": "6179f94f-eb56-4e63-a00d-7c45d78c1ff2",
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "1990-05-15",
    "socialSecurityNumber": "123-45-6789"
}
```

#### 3. POST /visits/patient/{patientId} (create a visit for a patient)
**Body:**
```json
{
    "homeVisit": false,
    "visitReason": "RECURRING_VISIT",
    "familyHistory": "Diabetes",
    "visitTime": "2023-02-23T10:05:30"
}
```
**Response 201:**
```json
{
    "visitId": "6a895300-ec2e-46d6-a3ab-4ad921019fdf",
    "visitDatetime": "2023-02-23T10:05:30",
    "homeVisit": false,
    "visitReason": "RECURRING_VISIT",
    "familyHistory": "Diabetes",
    "patientId": "c6e67075-b22c-4ad9-9804-15b3dc2a3d3c"
}
```

#### 4. GET /visits/patient/{patientId} (all visits for a patient)
**Response 200:**
```json
[
    {
        "visitReason": "RECURRING_VISIT",
        "visitTime": "2023-02-23T10:05:30",
        "visitId": "6a895300-ec2e-46d6-a3ab-4ad921019fdf"
    },
    {
        "visitReason": "RECURRING_VISIT",
        "visitTime": "2023-01-15T10:05:30",
        "visitId": "d2166f9e-ff99-47c5-84d6-e5d2ba5c9811"
    }
]
```

#### 5. PATCH /visits/{visitId} (update an existinig visit)
**Body:**
```json
{
    "homeVisit": false,
    "visitReason": "RECURRING_VISIT",
    "familyHistory": "Diabetes and Arthritis",
    "visitTime": "2024-09-23T10:05:32"
}
```
**Response 200:**
```json
{
    "visitId": "6a895300-ec2e-46d6-a3ab-4ad921019fdf",
    "visitDatetime": "2024-09-23T10:05:32",
    "homeVisit": false,
    "visitReason": "RECURRING_VISIT",
    "familyHistory": "Diabetes and Arthritis",
    "patientId": "c6e67075-b22c-4ad9-9804-15b3dc2a3d3c"
}
```

#### 6. GET /visits/{visitId}
**Body:**
```json
{
    "visitId": "d9bea0ca-e7be-4ca6-a333-c8e0234405f6",
    "visitDatetime": "2024-09-23T10:05:32",
    "homeVisit": false,
    "visitReason": "RECURRING_VISIT",
    "familyHistory": "No relevant family history",
    "patientId": "6179f94f-eb56-4e63-a00d-7c45d78c1ff2"
}
```
</details>

<details>
<summary><strong>Additional Ideas:</strong></summary>
If I were to spend more time on this project, here are some improvements I would make.

##### SSN encryption
- It doesn't feel right to store social security numbers in plain text.
The SSNs should be encrypted with some key before they are stored in the db.
First, the endpoints should require some sort of authentication.
If the user is authenticated to see a Patienit's data, the SSN for that user could be decrypted.

##### Improvement to Family History
- Depending on the reason for the "Family History" field, there could be some improvements.
It is likely that information from Family History could be useful for more than one Visit.
Perhaps the family history could just be bound to the user.
- Family history could also have its own table. This could make it easier to diagnose Patients if their
family history could be automatically flagged for having certain values beyond plain text.

##### Code Design
- I am not a fan of putting so much logic at the controller/endpoitn level. If this application were to grow,
I would move object mapping and any business logic to a service level.

##### Unit Tests
- Due to time, I am skipping unit tests here. I have only tested the application with Postman. QuarkusTest seems to have great shortcuts for endpoint tests that may be more tedious with Spring.
- The application is not robust and deals with only the happy path and basic 404s right now.

</details>
