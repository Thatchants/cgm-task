package cgm.book.controller;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;

import cgm.book.dto.AddVisitDto;
import cgm.book.dto.VisitListDto;
import cgm.book.model.Patient;
import cgm.book.model.Visit;

@Path("/visits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitController {

    @POST
    @Transactional
    @Path("/patient/{patientId}")
    public Response createVisit(@PathParam("patientId") UUID patientId, AddVisitDto visitDto) throws JsonProcessingException {
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
                .entity(JsonUtil.convert(visit))
                .build();
    }

    @GET
    @Path("/patient/{patientId}")
    public Response getVisitsForUser(@PathParam("patientId") UUID patientId) throws JsonProcessingException{
        List<Visit> visits = Visit.findAllByPatientId(patientId);

        List<VisitListDto> visitListDtos = visits.stream()
            .map(visit -> new VisitListDto(
                visit.visitReason, 
                visit.visitDatetime, 
                visit.visitId))
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK)
                .entity(JsonUtil.convert(visitListDtos))
                .build();
    }

    @PATCH
    @Path("/{visitId}")
    @Transactional
    public Response updateVisit(@PathParam("visitId") UUID visitId, AddVisitDto visitDto) throws JsonProcessingException {
        Visit visit = Visit.findByVisitId(visitId);
        if (visit == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Visit not found with ID: " + visitId)
                    .build();
        }

        visit.visitDatetime = visitDto.visitTime();
        visit.homeVisit = visitDto.homeVisit();
        visit.visitReason = visitDto.visitReason();
        visit.familyHistory = visitDto.familyHistory();
        visit.persist();

        return Response.status(Response.Status.OK)
                .entity(JsonUtil.convert(visit))
                .build();
    }

    @GET
    @Path("/{visitId}")
    public Response getVisitById(@PathParam("visitId") UUID visitId) throws JsonProcessingException {
        Visit visit = Visit.findByVisitId(visitId);
        if (visit == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Visit not found with ID: " + visitId)
                    .build();
        }

        return Response.status(Response.Status.OK)
            .entity(JsonUtil.convert(visit))
            .build();
    }
}
