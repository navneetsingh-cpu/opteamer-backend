package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.OperationRoomDTO;
import io.medsys.opteamer.services.OperationRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/operationrooms")
public class OperationRoomController {

    OperationRoomService operationRoomService;

    @Autowired
    public OperationRoomController(OperationRoomService operationRoomService) {
        this.operationRoomService = operationRoomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> getOperationRoomById(@PathVariable(name = "id") Long id) {
        Optional<OperationRoomDTO> operationRoomDTOOptional = operationRoomService.getOperationRoomById(id);
        return operationRoomDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<OperationRoomDTO>> getAllOperationRooms() {
        List<OperationRoomDTO> list = operationRoomService.getAllOperationRooms();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping
    public ResponseEntity<OperationRoomDTO> createOperationRoom(@RequestBody OperationRoomDTO OperationRoomDTO) {
        OperationRoomDTO createdOperationRoomDTO = operationRoomService.createOperationRoom(OperationRoomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperationRoomDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> updateOperationRoom(@PathVariable(name = "id") Long id,
            @RequestBody OperationRoomDTO operationRoomDTO) {
        Optional<OperationRoomDTO> operationRoomDTOOptional = operationRoomService.updateOperationRoom(id,
                operationRoomDTO);
        return operationRoomDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperationRoom(@PathVariable(name = "id") Long id) {
        boolean deleted = operationRoomService.deleteOperationRoom(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
