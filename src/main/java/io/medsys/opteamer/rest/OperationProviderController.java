package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.OperationProviderDTO;
import io.medsys.opteamer.services.OperationProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operationproviders")
public class OperationProviderController {

    OperationProviderService operationProviderService;

    @Autowired
    public OperationProviderController(OperationProviderService operationProviderService) {
        this.operationProviderService = operationProviderService;
    }

    @GetMapping("/{type}")
    public ResponseEntity<OperationProviderDTO> getOperationProviderById(@PathVariable(name = "type") String type) {
        Optional<OperationProviderDTO> operationProviderDTOOptional = operationProviderService
                .getOperationProviderById(type);
        return operationProviderDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationProviderDTO>> getAllOperationProviders() {
        List<OperationProviderDTO> list = operationProviderService.getAllOperationProviders();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping
    public ResponseEntity<OperationProviderDTO> createOperationProvider(
            @RequestBody OperationProviderDTO OperationProviderDTO) {
        OperationProviderDTO createdOperationProviderDTO = operationProviderService
                .createOperationProvider(OperationProviderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationProviderDTO);
    }

    @PutMapping("/{type}")
    public ResponseEntity<OperationProviderDTO> updateOperationProvider(@PathVariable(name = "type") String type,
            @RequestBody OperationProviderDTO operationProviderDTO) {
        Optional<OperationProviderDTO> operationProviderDTOOptional = operationProviderService
                .updateOperationProvider(type, operationProviderDTO);
        return operationProviderDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{type}")
    public ResponseEntity<Void> deleteOperationProvider(@PathVariable(name = "type") String type) {
        boolean deleted = operationProviderService.deleteOperationProvider(type);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
