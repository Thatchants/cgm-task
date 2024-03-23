package cgm.book.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public record VisitListDto(
    String visitReason, 
    

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    LocalDateTime visitTime, 
    UUID visitId) {
}
