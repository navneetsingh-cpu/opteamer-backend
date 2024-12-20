package io.medsys.opteamer.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PRE_OPERATIVE_ASSESSMENTS")
@Data
public class PreOperativeAssessment {

    @Id
    private String name;
}
