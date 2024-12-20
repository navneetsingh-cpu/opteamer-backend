package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.AssetDTO;
import io.medsys.opteamer.dto.OperationRoomDTO;
import io.medsys.opteamer.dto.RoomInventoryDTO;
import io.medsys.opteamer.model.Asset;
import io.medsys.opteamer.model.OperationRoom;
import io.medsys.opteamer.model.RoomInventory;
import io.medsys.opteamer.model.embeddedids.RoomInventoryId;
import io.medsys.opteamer.repositories.AssetRepository;
import io.medsys.opteamer.repositories.OperationRoomRepository;
import io.medsys.opteamer.repositories.RoomInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoomInventoryService {

    RoomInventoryRepository roomInventoryRepository;
    AssetRepository assetRepository;
    OperationRoomRepository operationRoomRepository;

    @Autowired
    public RoomInventoryService(RoomInventoryRepository roomInventoryRepository,
            AssetRepository assetRepository, OperationRoomRepository operationRoomRepository) {
        this.roomInventoryRepository = roomInventoryRepository;
        this.assetRepository = assetRepository;
        this.operationRoomRepository = operationRoomRepository;
    }

    public Optional<RoomInventoryDTO> getRoomInventoryById(Long assetId, Long roomId) {
        try {
            RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
            RoomInventory roomInventory = roomInventoryRepository.findById(roomInventoryId).orElseThrow();
            return Optional.of(getRoomInventoryDTO(roomInventory,
                    roomInventory.getAsset(),
                    roomInventory.getOperationRoom()));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<RoomInventoryDTO> getAllRoomInventories() {
        List<RoomInventoryDTO> list = new ArrayList<>();
        Iterable<RoomInventory> allRoomInventories = roomInventoryRepository.findAll();
        allRoomInventories.forEach(roomInventory -> list.add(getRoomInventoryDTO(roomInventory,
                roomInventory.getAsset(),
                roomInventory.getOperationRoom())));
        return list;
    }

    public RoomInventoryDTO createRoomInventory(RoomInventoryDTO roomInventoryDTO) throws NoSuchElementException {
        RoomInventory roomInventory = new RoomInventory();
        Asset asset = assetRepository.findById(roomInventoryDTO.getAssetId()).get();
        OperationRoom operationRoom = operationRoomRepository.findById(roomInventoryDTO.getOperationRoomId()).get();
        if (asset == null || operationRoom == null)
            throw new NoSuchElementException();
        roomInventory.setRoomInventoryId(new RoomInventoryId(asset.getId(), operationRoom.getId()));
        roomInventory.setAsset(asset);
        roomInventory.setOperationRoom(operationRoom);
        roomInventory.setCount(roomInventoryDTO.getCount());
        roomInventory = roomInventoryRepository.save(roomInventory);
        return getRoomInventoryDTO(roomInventory,
                roomInventory.getAsset(),
                roomInventory.getOperationRoom());
    }

    public Optional<RoomInventoryDTO> updateRoomInventory(Long assetId, Long roomId,
            RoomInventoryDTO roomInventoryDTO) {
        RoomInventoryId roomInventoryId = new RoomInventoryId(roomId, assetId);
        return roomInventoryRepository.findById(roomInventoryId).map(roomInventory -> {
            roomInventory.setCount(roomInventoryDTO.getCount());
            roomInventoryRepository.save(roomInventory);
            return getRoomInventoryDTO(roomInventory,
                    roomInventory.getAsset(),
                    roomInventory.getOperationRoom());
        });
    }

    public boolean deleteRoomInventory(Long assetId, Long roomId) {
        RoomInventoryId roomInventoryId = new RoomInventoryId(roomId, assetId);
        return roomInventoryRepository.findById(roomInventoryId).map(roomInventory -> {
            roomInventoryRepository.delete(roomInventory);
            return true;
        }).orElse(false);
    }

    private static RoomInventoryDTO getRoomInventoryDTO(RoomInventory roomInventory, Asset asset,
            OperationRoom operationRoom) {
        return new RoomInventoryDTO(roomInventory.getAsset().getId(),
                roomInventory.getOperationRoom().getId(),
                new AssetDTO(asset.getId(), asset.getType(), asset.getName()),
                new OperationRoomDTO(operationRoom.getId(),
                        operationRoom.getRoomNr(),
                        operationRoom.getBuildingBlock(),
                        operationRoom.getFloor(),
                        operationRoom.getType(),
                        operationRoom.getState()),
                roomInventory.getCount());
    }
}
