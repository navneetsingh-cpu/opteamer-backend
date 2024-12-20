package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.OperationDTO;
import io.medsys.opteamer.dto.OperationTypeDTO;
import io.medsys.opteamer.model.Operation;
import io.medsys.opteamer.model.OperationType;
import io.medsys.opteamer.model.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 19.
 */

@Mapper(uses = { OperationTypeMapper.class, OperationRoomMapper.class, PatientMapper.class, TeamMemberMapper.class })
public interface OperationMapper {

    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    @Mapping(source = "operationType", target = "operationType")
    @Mapping(source = "operationRoom", target = "operationRoom")
    @Mapping(source = "patient", target = "patient")
    @Mapping(source = "teamMembers", target = "teamMembers")
    OperationDTO toOperationDTO(Operation operation);

    Operation toOperation(OperationDTO operationDTO);

}
