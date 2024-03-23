package cgm.book.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record PatientListDto(
    String firstName, 
    String lastName, 
    UUID patientId) {
}
