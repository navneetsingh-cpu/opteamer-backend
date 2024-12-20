package io.medsys.opteamer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssessmentDTO {
    private Long teamMemberId;
    private String preOpAssessmentId;
    private Long patientId;
    private TeamMemberDTO teamMember;
    private PreOperativeAssessmentDTO preOperativeAssessment;
    private PatientDTO patient;
    private LocalDateTime startDate;
}
