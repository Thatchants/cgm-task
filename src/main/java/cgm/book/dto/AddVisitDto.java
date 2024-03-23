package cgm.book.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record AddVisitDto(
    Boolean homeVisit, 
    String visitReason, 
    String familyHistory,
    LocalDateTime visitTime) {

}
