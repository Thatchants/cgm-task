package cgm.book.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record VisitListDto(
    String visitReason, 
    LocalDateTime time, 
    UUID visitId) {
}
