package cgm.book.dto;

import java.time.LocalDateTime;

public record VisitDto(
    Boolean homeVisit, 
    String visitReason, 
    String familyHistory,
    LocalDateTime visitTime) {

}
