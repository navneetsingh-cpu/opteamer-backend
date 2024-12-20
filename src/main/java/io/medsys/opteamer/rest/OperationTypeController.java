package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.OperationTypeDTO;
import io.medsys.opteamer.services.OperationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operationtypes")
public class OperationTypeController {

    OperationTypeService operationTypeService;

    @Autowired
    public OperationTypeController(OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> getOperationTypeById(@PathVariable(name = "name") String name) {
        Optional<OperationTypeDTO> operationTypeDTOOptional = operationTypeService.getOperationTypeById(name);
        return operationTypeDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationTypeDTO>> getAllOperationTypes() {
        List<OperationTypeDTO> list = operationTypeService.getAllOperationTypes();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping
    public ResponseEntity<OperationTypeDTO> createOperationType(@RequestBody OperationTypeDTO operationTypeDTO) {
        OperationTypeDTO createdOperationTypeDTO = operationTypeService.createOperationType(operationTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationTypeDTO);
    }

    @PutMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> updateOperationType(@PathVariable(name = "name") String name,
            @RequestBody OperationTypeDTO operationTypeDTO) {
        Optional<OperationTypeDTO> operationTypeDTOOptional = operationTypeService.updateOperationType(name,
                operationTypeDTO);
        return operationTypeDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteOperationType(@PathVariable(name = "name") String name) {
        boolean deleted = operationTypeService.deleteOperationType(name);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
