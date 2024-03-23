package cgm.book.controller;

import java.util.UUID;

import cgm.book.model.Patient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/patient")
public class PatientController {

    @GET
    public String patientName() {
        Patient patient = Patient.findByPatientId(UUID.fromString("6179f94f-eb56-4e63-a00d-7c45d78c1ff2"));

        return String.format("%s %s", patient.firstName, patient.lastName);
    }
    
}
