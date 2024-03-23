package cgm.book.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Visit extends PanacheEntityBase {
    @Id
    @Column(name = "visit_id")
    public UUID visitId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    public Patient patient;

    @Column(name = "visit_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public LocalDateTime visitDatetime;

    @Column(name = "home_visit")
    public Boolean homeVisit;

    @Column(name = "visit_reason")
    public String visitReason;

    @Column(name = "family_history")
    public String familyHistory;

    public static List<Visit> findAllByPatientId(UUID patientId){
        return find("patient.patientId", patientId).list();
    }

    public static Visit findByVisitId(UUID visitId){
        return find("visitId", visitId).firstResult();
    }

    // Getter method for patientId
    @Transient
    @JsonProperty("patientId")
    public UUID getPatientId() {
        return (patient != null) ? patient.patientId : null;
    }

}
