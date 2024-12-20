package io.medsys.opteamer.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="ASSESSMENTS")
@NoArgsConstructor
@Getter
@Setter
public class Assessment {

    @EmbeddedId
    private AssessmentId assessmentId;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "pre_op_a_id")
    private PreOperativeAssessment preOperativeAssessment;

    @Column(name = "start_date")
    private LocalDateTime startDate;

}
