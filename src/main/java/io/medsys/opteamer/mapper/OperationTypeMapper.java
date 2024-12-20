package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.OperationTypeDTO;
import io.medsys.opteamer.model.OperationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 19.
 */

@Mapper(uses = { AssetMapper.class, OperationTypeMapper.class, PreOperativeAssessmentMapper.class })
public interface OperationTypeMapper {

    OperationTypeMapper INSTANCE = Mappers.getMapper(OperationTypeMapper.class);

    @Mapping(source = "assets", target = "assets")
    @Mapping(source = "operationProviders", target = "operationProviders")
    @Mapping(source = "preOperativeAssessments", target = "preOperativeAssessments")
    OperationTypeDTO toOperationTypeDTO(OperationType operationType);

    OperationType toOperationType(OperationTypeDTO operationTypeDTO);

}
