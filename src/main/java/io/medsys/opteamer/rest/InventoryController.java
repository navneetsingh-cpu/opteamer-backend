package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.InventoryDTO;
import io.medsys.opteamer.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService InventoryService) {
        this.inventoryService = InventoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable(name = "id") Long id) {
        Optional<InventoryDTO> inventoryDTOOptional = inventoryService.getInventoryById(id);
        return inventoryDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventorys() {
        List<InventoryDTO> inventoryList = inventoryService.getAllInventories();
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryList);
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        try {
            InventoryDTO createdInventoryDTO = inventoryService.createInventory(inventoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInventoryDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable(name = "id") Long id,
            @RequestBody InventoryDTO InventoryDTO) {
        Optional<InventoryDTO> inventoryDTOOptional = inventoryService.updateInventory(id, InventoryDTO);
        return inventoryDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable(name = "id") Long id) {
        boolean deleted = inventoryService.deleteInventory(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}