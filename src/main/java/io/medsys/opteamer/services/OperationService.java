package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationDTO;
import io.medsys.opteamer.mapper.OperationMapper;
import io.medsys.opteamer.model.*;
import io.medsys.opteamer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperationService {

    OperationRepository operationRepository;
    OperationTypeRepository operationTypeRepository;
    OperationRoomRepository operationRoomRepository;
    PatientRepository patientRepository;
    TeamMemberRepository teamMemberRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository, OperationTypeRepository operationTypeRepository,
            OperationRoomRepository operationRoomRepository, PatientRepository patientRepository,
            TeamMemberRepository teamMemberRepository) {
        this.operationRepository = operationRepository;
        this.operationTypeRepository = operationTypeRepository;
        this.operationRoomRepository = operationRoomRepository;
        this.patientRepository = patientRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public Optional<OperationDTO> getOperationById(Long id) {
        try {
            Operation operation = operationRepository.findById(id).orElseThrow();
            return Optional.of(OperationMapper.INSTANCE.toOperationDTO(operation));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<OperationDTO> getAllOperations() {
        List<OperationDTO> list = new ArrayList<>();
        Iterable<Operation> allOperations = operationRepository.findAll();
        allOperations.forEach(operation -> list.add(OperationMapper.INSTANCE.toOperationDTO(operation)));
        return list;
    }

    public OperationDTO createOperation(OperationDTO operationDTO) throws Exception {
        Operation operation = OperationMapper.INSTANCE.toOperation(operationDTO);
        setChildEntites(operationDTO, operation);

        // validation ------------
        boolean isValidOpTypeOperationProvider = operation.getTeamMembers().stream()
                .map(teamMember -> teamMember.getOperationProvider().getType().toString()).collect(Collectors.toList())
                .containsAll(
                        operation.getOperationType().getOperationProviders().stream()
                                .map(op -> op.getType().toString()).collect(Collectors.toList()));

        if (!isValidOpTypeOperationProvider) {
            throw new Exception("Based on the operation type, the operation team is incomplete");
        }
        // ------------------------

        operation = operationRepository.save(operation);
        return OperationMapper.INSTANCE.toOperationDTO(operation);
    }

    public Optional<OperationDTO> updateOperation(Long id, OperationDTO operationDTO) {
        return operationRepository.findById(id).map(operation -> {
            operation.setState(operationDTO.getState());
            operation.setStartDate(operationDTO.getStartDate());
            setChildEntites(operationDTO, operation);
            operationRepository.save(operation);
            return OperationMapper.INSTANCE.toOperationDTO(operation);
        });
    }

    public boolean deleteOperation(Long id) {
        return operationRepository.findById(id).map(operation -> {
            operationRepository.delete(operation);
            return true;
        }).orElse(false);
    }

    private void setChildEntites(OperationDTO operationDTO, Operation operation) {

        OperationType operationType = operationTypeRepository.findByName(operationDTO.getOperationType().getName())
                .get();
        OperationRoom operationRoom = operationRoomRepository.findById(operationDTO.getOperationRoom().getId()).get();
        Patient patient = patientRepository.findById(operationDTO.getPatient().getId()).get();

        Set<TeamMember> teamMembers = operationDTO.getTeamMembers().stream()
                .map(teamMemberDTO -> teamMemberRepository.findById(teamMemberDTO.getId())
                        .orElseThrow(() -> new NoSuchElementException()))
                .collect(Collectors.toSet());

        operation.setOperationType(operationType);
        operation.setOperationRoom(operationRoom);
        operation.setPatient(patient);
        operation.setTeamMembers(teamMembers);
    }
}
