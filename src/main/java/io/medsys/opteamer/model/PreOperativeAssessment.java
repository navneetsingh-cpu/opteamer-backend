package io.medsys.opteamer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PRE_OPERATIVE_ASSESSMENTS")
@Getter
@Setter
public class PreOperativeAssessment {

    @Id
    private String name;
}
