package cgm.book.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Patient extends PanacheEntityBase {
    @Id
    @Column(name = "patient_id")
    public UUID patientId;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate dateOfBirth;

    @Column(name = "social_security_number")
    public String socialSecurityNumber;

    public static Patient findByPatientId(UUID patientId){
        return find("patientId", patientId).firstResult();
    }
}
