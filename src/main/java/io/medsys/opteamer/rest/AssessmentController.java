package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.AssessmentDTO;
import io.medsys.opteamer.services.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<AssessmentDTO> getAssessmentById(@PathVariable(name = "teamMemberId") Long teamMemberId,
            @PathVariable(name = "patientId") Long patientId, @PathVariable(name = "preOpAName") String preOpAName) {
        Optional<AssessmentDTO> assessmentDTOOptional = assessmentService.getAssessmentById(teamMemberId, preOpAName,
                patientId);
        return assessmentDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<AssessmentDTO>> getAllAssessments() {
        List<AssessmentDTO> assessmentList = assessmentService.getAllAssessments();
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentList);
    }

    @PostMapping
    public ResponseEntity<AssessmentDTO> createAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        AssessmentDTO createdAssessmentDTO = assessmentService.createAssessment(assessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssessmentDTO);
    }

    @PutMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<AssessmentDTO> updateAssessment(
            @PathVariable(name = "teamMemberId") Long teamMemberId, @PathVariable(name = "patientId") Long patientId,
            @PathVariable(name = "preOpAName") String preOpAName, @RequestBody AssessmentDTO assessmentDTO) {
        Optional<AssessmentDTO> AssessmentDTOOptional = assessmentService.updateAssessment(teamMemberId, patientId,
                preOpAName, assessmentDTO);
        return AssessmentDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{teamMemberId}/{patientId}/{preOpAName}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable(name = "teamMemberId") Long teamMemberId,
            @PathVariable(name = "patientId") Long patientId,
            @PathVariable(name = "preOpAName") String preOpAName) {
        boolean deleted = assessmentService.deleteAssessment(teamMemberId, patientId, preOpAName);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
