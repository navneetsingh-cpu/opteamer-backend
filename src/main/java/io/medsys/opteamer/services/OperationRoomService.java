package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationRoomDTO;
import io.medsys.opteamer.model.OperationRoom;
import io.medsys.opteamer.repositories.OperationRoomRepository;
import io.medsys.opteamer.utils.ModelMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OperationRoomService {

    OperationRoomRepository operationRoomRepository;

    @Autowired
    public OperationRoomService(OperationRoomRepository operationRoomRepository) {
        this.operationRoomRepository = operationRoomRepository;
    }

    public Optional<OperationRoomDTO> getOperationRoomById(Long id) {
        try {
            OperationRoom operationRoom = operationRoomRepository.findById(id).orElseThrow();
            return Optional.of(ModelMapperUtils.mapOperationRoomEntityToDTO(operationRoom));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<OperationRoomDTO> getAllOperationRooms() {
        List<OperationRoomDTO> list = new ArrayList<>();
        Iterable<OperationRoom> allOperationRooms = operationRoomRepository.findAll();
        allOperationRooms
                .forEach(operationRoom -> list.add(ModelMapperUtils.mapOperationRoomEntityToDTO(operationRoom)));
        return list;
    }

    public OperationRoomDTO createOperationRoom(OperationRoomDTO operationRoomDTO) {
        ModelMapper modelMapper = new ModelMapper();
        OperationRoom operationRoom = modelMapper.map(operationRoomDTO, OperationRoom.class);
        operationRoom = operationRoomRepository.save(operationRoom);
        return modelMapper.map(operationRoom, OperationRoomDTO.class);
    }

    public Optional<OperationRoomDTO> updateOperationRoom(Long id, OperationRoomDTO operationRoomDTO) {
        return operationRoomRepository.findById(id).map(operationRoom -> {

            operationRoom.setRoomNr(operationRoomDTO.getRoomNr());
            operationRoom.setBuildingBlock(operationRoomDTO.getBuildingBlock());
            operationRoom.setFloor(operationRoomDTO.getFloor());
            operationRoom.setType(operationRoomDTO.getType());
            operationRoom.setState(operationRoomDTO.getState());

            operationRoomRepository.save(operationRoom);
            return ModelMapperUtils.mapOperationRoomEntityToDTO(operationRoom);
        });
    }

    public boolean deleteOperationRoom(Long id) {
        return operationRoomRepository.findById(id).map(operationRoom -> {
            operationRoomRepository.delete(operationRoom);
            return true;
        }).orElse(false);
    }
}
