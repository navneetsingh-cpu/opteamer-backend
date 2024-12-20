package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationReportDTO;
import io.medsys.opteamer.mapper.OperationReportMapper;
import io.medsys.opteamer.model.Operation;
import io.medsys.opteamer.model.OperationReport;
import io.medsys.opteamer.model.TeamMember;
import io.medsys.opteamer.model.embeddedids.OperationReportId;
import io.medsys.opteamer.repositories.OperationReportRepository;
import io.medsys.opteamer.repositories.OperationRepository;
import io.medsys.opteamer.repositories.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OperationReportService {

    OperationReportRepository operationReportRepository;
    TeamMemberRepository teamMemberRepository;
    OperationRepository operationRepository;

    @Autowired
    public OperationReportService(io.medsys.opteamer.repositories.OperationReportRepository operationReportRepository,
            TeamMemberRepository teamMemberRepository, OperationRepository operationRepository) {
        this.operationReportRepository = operationReportRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.operationRepository = operationRepository;
    }

    public Optional<OperationReportDTO> getOperationReportById(Long teamMemberId, Long operationId) {
        try {
            OperationReportId OperationReportId = new OperationReportId(teamMemberId, operationId);
            OperationReport operationReport = operationReportRepository.findById(OperationReportId).orElseThrow();
            return Optional.of(getOperationReportDTO(operationReport));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<OperationReportDTO> getAllOperationReports() {
        List<OperationReportDTO> list = new ArrayList<>();
        Iterable<OperationReport> allOperationReports = operationReportRepository.findAll();

        allOperationReports.forEach(operationReport -> list.add(getOperationReportDTO(operationReport)));
        return list;
    }

    public OperationReportDTO createOperationReport(OperationReportDTO operationReportDTO)
            throws NoSuchElementException {
        OperationReport operationReport = new OperationReport();
        TeamMember teamMember = teamMemberRepository.findById(operationReportDTO.getTeamMemberId()).get();
        Operation operation = operationRepository.findById(operationReportDTO.getOperationId()).get();

        if (teamMember == null || operation == null)
            throw new NoSuchElementException();

        operationReport.setOperationReportId(new OperationReportId(teamMember.getId(), operation.getId()));
        operationReport.setTeamMember(teamMember);
        operationReport.setOperation(operation);
        operationReport.setReport(operationReportDTO.getReport());
        operationReport = operationReportRepository.save(operationReport);

        return getOperationReportDTO(operationReport);
    }

    public Optional<OperationReportDTO> updateOperationReport(Long teamMemberId, Long operationId,
            OperationReportDTO operationReportDTO) {
        OperationReportId operationReportId = new OperationReportId(teamMemberId, operationId);
        return operationReportRepository.findById(operationReportId).map(operationReport -> {
            operationReport.setReport(operationReportDTO.getReport());
            operationReportRepository.save(operationReport);
            return getOperationReportDTO(operationReport);
        });
    }

    public boolean deleteOperationReport(Long teamMemberId, Long operationId) {
        OperationReportId operationReportId = new OperationReportId(teamMemberId, operationId);
        return operationReportRepository.findById(operationReportId).map(operationReport -> {
            operationReportRepository.delete(operationReport);
            return true;
        }).orElse(false);
    }

    private static OperationReportDTO getOperationReportDTO(OperationReport operationReport) {
        OperationReportDTO returnOperationReportDTO = OperationReportMapper.INSTANCE
                .toOperationReportDTO(operationReport);
        returnOperationReportDTO.setTeamMemberId(operationReport.getTeamMember().getId());
        returnOperationReportDTO.setOperationId(operationReport.getOperation().getId());
        return returnOperationReportDTO;
    }
}
