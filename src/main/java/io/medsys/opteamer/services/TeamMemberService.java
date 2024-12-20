package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.TeamMemberDTO;
import io.medsys.opteamer.model.OperationProvider;
import io.medsys.opteamer.model.TeamMember;
import io.medsys.opteamer.repositories.TeamMemberRepository;
import io.medsys.opteamer.utils.ModelMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 17.
 */

@Service
public class TeamMemberService {

    TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public Optional<TeamMemberDTO> getTeamMemberById(Long id) {
        try {
            TeamMember teamMember = teamMemberRepository.findById(id).orElseThrow();
            return Optional.of(getMappedTeamMemberDTO(new ModelMapper(), teamMember));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<TeamMemberDTO> getAllTeamMembers() {
        List<TeamMemberDTO> list = new ArrayList<>();
        Iterable<TeamMember> allTeamMembers = teamMemberRepository.findAll();
        allTeamMembers.forEach(teamMember -> list.add(getMappedTeamMemberDTO(new ModelMapper(), teamMember)));
        return list;
    }

    public TeamMemberDTO createTeamMember(TeamMemberDTO teamMemberDTO) {
        ModelMapper modelMapper = new ModelMapper();
        TeamMember teamMember = modelMapper.map(teamMemberDTO, TeamMember.class);
        teamMember = teamMemberRepository.save(teamMember);
        return getMappedTeamMemberDTO(modelMapper, teamMember);
    }

    public Optional<TeamMemberDTO> updateTeamMember(Long id, TeamMemberDTO teamMemberDTO) {
        return teamMemberRepository.findById(id).map(teamMember -> {

            teamMember.setName(teamMemberDTO.getName());
            OperationProvider operationProvider = ModelMapperUtils
                    .mapOperationProviderDTOToEntity(teamMemberDTO.getOperationProvider());
            teamMember.setOperationProvider(operationProvider);

            teamMemberRepository.save(teamMember);
            return getMappedTeamMemberDTO(new ModelMapper(), teamMember);
        });
    }

    public boolean deleteTeamMember(Long id) {
        return teamMemberRepository.findById(id).map(teamMember -> {
            teamMemberRepository.delete(teamMember);
            return true;
        }).orElse(false);
    }

    private TeamMemberDTO mapEntityToDTO(TeamMember teamMember) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(teamMember, TeamMemberDTO.class);
    }

    private static TeamMemberDTO getMappedTeamMemberDTO(ModelMapper modelMapper, TeamMember teamMember) {
        TeamMemberDTO mappedTeamMemberDTO = modelMapper.map(teamMember, TeamMemberDTO.class);
        mappedTeamMemberDTO.setOperationProvider(
                ModelMapperUtils.mapOperationProviderEntityToDTO(teamMember.getOperationProvider()));
        return mappedTeamMemberDTO;
    }
}
