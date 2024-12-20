package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.TeamMemberDTO;
import io.medsys.opteamer.model.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 19.
 */

@Mapper(uses = { OperationProviderMapper.class })
public interface TeamMemberMapper {

    TeamMemberMapper INSTANCE = Mappers.getMapper(TeamMemberMapper.class);

    @Mapping(source = "operationProvider", target = "operationProvider")
    TeamMemberDTO toTeamMemberDTO(TeamMember teamMember);

    TeamMember toTeamMember(TeamMemberDTO teamMemberDTO);

}
