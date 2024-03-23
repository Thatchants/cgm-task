package cgm.book.controller;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import cgm.book.dto.VisitDto;
import cgm.book.dto.VisitListDto;
import cgm.book.model.Patient;
import cgm.book.model.Visit;

@Path("/visits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitController {

    @POST
    @Transactional
    public Response createVisit(@QueryParam("patientId") UUID patientId, VisitDto visitDto) {
        Patient patient = Patient.findByPatientId(patientId);
        if (patient == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Patient not found with ID: " + patientId)
                    .build();
        }

        Visit visit = new Visit();
        visit.visitId = UUID.randomUUID();
        visit.patient = patient;
        visit.visitDatetime = visitDto.visitTime();
        visit.homeVisit = visitDto.homeVisit();
        visit.visitReason = visitDto.visitReason();
        visit.familyHistory = visitDto.familyHistory();
        visit.persist();

        return Response.status(Response.Status.CREATED)
                .entity(visit)
                .build();
    }

    @GET
    public Response getVisitsForUser(@QueryParam("patientId") UUID patientId){
        List<Visit> visits = Visit.findAllByPatientId(patientId);

        List<VisitListDto> visitListDtos = visits.stream()
            .map(visit -> new VisitListDto(
                visit.visitReason, 
                visit.visitDatetime, 
                visit.visitId))
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(visitListDtos).build();
    }

    @PATCH
    @Path("/{visitId}")
    @Transactional
    public Response updateVisit(@PathParam("visitId") UUID visitId, VisitDto visitDto) {
        Visit visit = Visit.findByVisitId(visitId);
        if (visit == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Visit not found with ID: " + visitId)
                    .build();
        }

        // Update fields from VisitDto
        visit.visitDatetime = visitDto.visitTime();
        visit.homeVisit = visitDto.homeVisit();
        visit.visitReason = visitDto.visitReason();
        visit.familyHistory = visitDto.familyHistory();
        visit.persist();

        return Response.status(Response.Status.OK).entity(visit).build();
    }

    @GET
    @Path("/{visitId}")
    public Response getVisitById(@PathParam("visitId") UUID visitId) {
        Visit visit = Visit.findByVisitId(visitId);
        if (visit == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Visit not found with ID: " + visitId)
                    .build();
        }

        return Response.status(Response.Status.OK).entity(visit).build();
    }
}
