package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.OperationDTO;
import io.medsys.opteamer.dto.OperationReportDTO;
import io.medsys.opteamer.model.Operation;
import io.medsys.opteamer.model.OperationReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 19.
 */

@Mapper(uses = { Operation.class, TeamMemberMapper.class })
public interface OperationReportMapper {

    OperationReportMapper INSTANCE = Mappers.getMapper(OperationReportMapper.class);

    @Mapping(source = "operation", target = "operation")
    @Mapping(source = "teamMember", target = "teamMember")
    OperationReportDTO toOperationReportDTO(OperationReport operationReport);

    Operation toOperation(OperationDTO operationDTO);

}
