package cgm.book.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Visit extends PanacheEntityBase {
    @Id
    @Column(name = "visit_id")
    public UUID visitId;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    public Patient patient;

    @Column(name = "visit_datetime")
    public LocalDateTime visitDatetime;

    @Column(name = "home_visit")
    public Boolean homeVisit;

    @Column(name = "visit_reason")
    public String visitReason;

    @Column(name = "family_history")
    public String familyHistory;

    public static List<Visit> findAllByPatientId(UUID patientId){
        return find("patientId", patientId).list();
    }

    public static Visit findByVisitId(UUID visitId){
        return find("visitId", visitId).firstResult();
    }

}
