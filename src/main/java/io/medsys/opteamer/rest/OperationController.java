package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.AuthenticationResponseDTO;
import io.medsys.opteamer.dto.ErrorResponseDTO;
import io.medsys.opteamer.dto.OperationDTO;
import io.medsys.opteamer.dto.RoomInventoryDTO;
import io.medsys.opteamer.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperationById(@PathVariable(name = "id") Long id) {
        Optional<OperationDTO> operationDTOOptional = operationService.getOperationById(id);
        return operationDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        List<OperationDTO> list = operationService.getAllOperations();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /*
     * @PostMapping
     * public ResponseEntity<OperationDTO> createOperation(@RequestBody OperationDTO
     * operationDTO) {
     * OperationDTO createdOperationDTO =
     * operationService.createOperation(operationDTO);
     * return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationDTO);
     * }
     */

    @PostMapping
    public ResponseEntity<?> createOperation(@RequestBody OperationDTO operationDTO) {
        try {
            OperationDTO createdOperationDTO = operationService.createOperation(operationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationDTO);
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrorResponseDTO(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationDTO> updateOperation(@PathVariable(name = "id") Long id,
            @RequestBody OperationDTO operationDTO) {
        Optional<OperationDTO> operationDTOOptional = operationService.updateOperation(id, operationDTO);
        return operationDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable(name = "id") Long id) {
        boolean deleted = operationService.deleteOperation(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
