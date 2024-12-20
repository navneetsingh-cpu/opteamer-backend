package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.OperationReportDTO;
import io.medsys.opteamer.services.OperationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operationreports")
public class OperationReportController {

    OperationReportService operationReportService;

    @Autowired
    public OperationReportController(OperationReportService operationReportService) {
        this.operationReportService = operationReportService;
    }

    @GetMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<OperationReportDTO> getOperationReportById(
            @PathVariable(name = "teamMemberId") Long teamMemberId,
            @PathVariable(name = "operationId") Long operationId) {
        Optional<OperationReportDTO> operationReportDTOOptional = operationReportService
                .getOperationReportById(teamMemberId, operationId);
        return operationReportDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationReportDTO>> getAllOperationReports() {
        List<OperationReportDTO> list = operationReportService.getAllOperationReports();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping
    public ResponseEntity<OperationReportDTO> createOperationReport(
            @RequestBody OperationReportDTO operationReportDTO) {
        OperationReportDTO createdOperationReportDTO = operationReportService.createOperationReport(operationReportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationReportDTO);
    }

    @PutMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<OperationReportDTO> updateOperationReport(
            @PathVariable(name = "teamMemberId") Long teamMemberId,
            @PathVariable(name = "operationId") Long operationId,
            @RequestBody OperationReportDTO operationReportDTO) {
        Optional<OperationReportDTO> operationReportDTOOptional = operationReportService
                .updateOperationReport(teamMemberId, operationId, operationReportDTO);
        return operationReportDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<Void> deleteOperationReport(@PathVariable(name = "teamMemberId") Long teamMemberId,
            @PathVariable(name = "operationId") Long operationId) {
        boolean deleted = operationReportService.deleteOperationReport(teamMemberId, operationId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
