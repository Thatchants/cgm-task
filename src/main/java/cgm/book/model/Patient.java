package cgm.book.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.UUID;

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
    public Date dateOfBirth;

    @Column(name = "social_security_number")
    public String socialSecurityNumber;

    public static Patient findByPatientId(UUID patientId){
        return find("patientId", patientId).firstResult();
    }
}
