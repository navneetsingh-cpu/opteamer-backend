package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.*;
import io.medsys.opteamer.model.*;
import io.medsys.opteamer.model.embeddedids.AssessmentId;
import io.medsys.opteamer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AssessmentService {

    AssessmentRepository assessmentRepository;
    TeamMemberRepository teamMemberRepository;
    PreOperativeAssessmentRepository preOperativeAssessmentRepository;
    PatientRepository patientRepository;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository, TeamMemberRepository teamMemberRepository,
            PreOperativeAssessmentRepository preOperativeAssessmentRepository, PatientRepository patientRepository) {
        this.assessmentRepository = assessmentRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.preOperativeAssessmentRepository = preOperativeAssessmentRepository;
        this.patientRepository = patientRepository;
    }

    public Optional<AssessmentDTO> getAssessmentById(Long teamMemberId, String preOpAId, Long patientId) {
        try {
            AssessmentId AssessmentId = new AssessmentId(teamMemberId, patientId, preOpAId);
            Assessment assessment = assessmentRepository.findById(AssessmentId).orElseThrow();
            return Optional.of(getAssessmentDTO(assessment));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<AssessmentDTO> getAllAssessments() {
        List<AssessmentDTO> list = new ArrayList<>();
        Iterable<Assessment> allAssessments = assessmentRepository.findAll();
        allAssessments.forEach(assessment -> list.add(getAssessmentDTO(assessment)));
        return list;
    }

    public AssessmentDTO createAssessment(AssessmentDTO assessmentDTO) throws NoSuchElementException {
        Assessment assessment = new Assessment();
        TeamMember teamMember = teamMemberRepository.findById(assessmentDTO.getTeamMemberId()).get();
        PreOperativeAssessment preOperativeAssessment = preOperativeAssessmentRepository
                .findByName(assessmentDTO.getPreOpAssessmentId()).get();
        Patient patient = patientRepository.findById(assessmentDTO.getPatientId()).get();

        if (teamMember == null || preOperativeAssessment == null || patient == null)
            throw new NoSuchElementException();

        assessment.setAssessmentId(
                new AssessmentId(teamMember.getId(), patient.getId(), preOperativeAssessment.getName()));
        assessment.setTeamMember(teamMember);
        assessment.setPreOperativeAssessment(preOperativeAssessment);
        assessment.setPatient(patient);
        assessment.setStartDate(assessmentDTO.getStartDate());
        assessment = assessmentRepository.save(assessment);

        return getAssessmentDTO(assessment);
    }

    public Optional<AssessmentDTO> updateAssessment(Long teamMemberId, Long patientId, String preOpAName,
            AssessmentDTO assessmentDTO) {
        AssessmentId assessmentId = new AssessmentId(teamMemberId, patientId, preOpAName);
        return assessmentRepository.findById(assessmentId).map(assessment -> {
            assessment.setStartDate(assessmentDTO.getStartDate());
            assessmentRepository.save(assessment);
            return getAssessmentDTO(assessment);
        });
    }

    public boolean deleteAssessment(Long teamMemberId, Long patientId, String preOpAName) {
        AssessmentId AssessmentId = new AssessmentId(teamMemberId, patientId, preOpAName);
        return assessmentRepository.findById(AssessmentId).map(assessment -> {
            assessmentRepository.delete(assessment);
            return true;
        }).orElse(false);
    }

    private static AssessmentDTO getAssessmentDTO(Assessment assessment) {
        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(assessment.getTeamMember().getId(),
                assessment.getTeamMember().getName(),
                new OperationProviderDTO(assessment.getTeamMember().getOperationProvider().getType()));
        PreOperativeAssessmentDTO preOperativeAssessmentDTO = new PreOperativeAssessmentDTO(
                assessment.getPreOperativeAssessment().getName());
        PatientDTO patientDTO = new PatientDTO(assessment.getPatient().getId(),
                assessment.getPatient().getName(),
                assessment.getPatient().getNin());

        return new AssessmentDTO(assessment.getTeamMember().getId(),
                assessment.getPreOperativeAssessment().getName(),
                assessment.getPatient().getId(),
                teamMemberDTO,
                preOperativeAssessmentDTO,
                patientDTO,
                assessment.getStartDate());
    }
}
