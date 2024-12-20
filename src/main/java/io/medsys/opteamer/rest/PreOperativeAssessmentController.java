package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.PreOperativeAssessmentDTO;
import io.medsys.opteamer.services.PreOperativeAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 17.
 */
@RestController
@RequestMapping("/api/preoperativeassessments")
public class PreOperativeAssessmentController {

    PreOperativeAssessmentService preOperativeAssessmentService;

    @Autowired
    public PreOperativeAssessmentController(PreOperativeAssessmentService preOperativeAssessmentService) {
        this.preOperativeAssessmentService = preOperativeAssessmentService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> getPreOperativeAssessmentById(
            @PathVariable(name = "name") String name) {
        Optional<PreOperativeAssessmentDTO> preOperativeAssessmentOptional = preOperativeAssessmentService
                .getPreOperativeAssessmentById(name);
        return preOperativeAssessmentOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<PreOperativeAssessmentDTO>> getAllPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> list = preOperativeAssessmentService.getAllPreOperativeAssessments();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping
    public ResponseEntity<PreOperativeAssessmentDTO> createPreOperativeAssessment(
            @RequestBody PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessmentDTO createdPreOperativeAssessmentDTO = preOperativeAssessmentService
                .createPreOperativeAssessment(preOperativeAssessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPreOperativeAssessmentDTO);
    }

    @PutMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> updatePreOperativeAssessment(
            @PathVariable(name = "name") String name,
            @RequestBody PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        Optional<PreOperativeAssessmentDTO> preOperativeAssessmentDTOOptional = preOperativeAssessmentService
                .updatePreOperativeAssessment(name, preOperativeAssessmentDTO);
        return preOperativeAssessmentDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePreOperativeAssessment(@PathVariable(name = "name") String name) {
        boolean deleted = preOperativeAssessmentService.deletePreOperativeAssessment(name);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}