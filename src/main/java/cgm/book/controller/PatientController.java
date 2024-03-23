package cgm.book.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import cgm.book.dto.PatientListDto;
import cgm.book.model.Patient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/patient")
@Produces(MediaType.APPLICATION_JSON)
public class PatientController {

    @GET
    @Path("/{patientId}")
    public Response patientName(@QueryParam("patientId") UUID patientId) {
        Patient patient = Patient.findByPatientId(patientId);
        if (patient == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Patient not found with ID: " + patientId)
                    .build();
        }

        return Response.status(Response.Status.OK).entity(patient).build();
    }

    @GET
    public Response getAllPatients() {
        List<Patient> patients = Patient.listAll();

        List<PatientListDto> patientListDtos = patients.stream()
                .map(patient -> new PatientListDto(
                        patient.firstName,
                        patient.lastName,
                        patient.patientId))
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(patientListDtos).build();
    }
    
}
