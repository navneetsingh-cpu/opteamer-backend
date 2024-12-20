package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.PreOperativeAssessmentDTO;
import io.medsys.opteamer.model.PreOperativeAssessment;
import io.medsys.opteamer.repositories.PreOperativeAssessmentRepository;
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
public class PreOperativeAssessmentService {

    PreOperativeAssessmentRepository preOperativeAssessmentRepository;

    @Autowired
    public PreOperativeAssessmentService(PreOperativeAssessmentRepository preOperativeAssessmentRepository) {
        this.preOperativeAssessmentRepository = preOperativeAssessmentRepository;
    }

    public Optional<PreOperativeAssessmentDTO> getPreOperativeAssessmentById(String id) {
        try {
            PreOperativeAssessment preOperativeAssessment = preOperativeAssessmentRepository.findByName(id)
                    .orElseThrow();
            return Optional.of(mapEntityToDTO(preOperativeAssessment));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<PreOperativeAssessmentDTO> getAllPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> list = new ArrayList<>();
        Iterable<PreOperativeAssessment> allPreOperativeAssessments = preOperativeAssessmentRepository.findAll();
        allPreOperativeAssessments.forEach(preOperativeAssessment -> list.add(mapEntityToDTO(preOperativeAssessment)));
        return list;
    }

    public PreOperativeAssessmentDTO createPreOperativeAssessment(PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        PreOperativeAssessment preOperativeAssessment = modelMapper.map(preOperativeAssessmentDTO,
                PreOperativeAssessment.class);
        preOperativeAssessment = preOperativeAssessmentRepository.save(preOperativeAssessment);
        return modelMapper.map(preOperativeAssessment, PreOperativeAssessmentDTO.class);
    }

    public Optional<PreOperativeAssessmentDTO> updatePreOperativeAssessment(String name,
            PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        return preOperativeAssessmentRepository.findByName(name).map(preOperativeAssessment -> {
            preOperativeAssessment.setName(preOperativeAssessmentDTO.getName());
            preOperativeAssessmentRepository.save(preOperativeAssessment);
            return mapEntityToDTO(preOperativeAssessment);
        });
    }

    public boolean deletePreOperativeAssessment(String name) {
        return preOperativeAssessmentRepository.findByName(name).map(preOperativeAssessment -> {
            preOperativeAssessmentRepository.delete(preOperativeAssessment);
            return true;
        }).orElse(false);
    }

    private PreOperativeAssessmentDTO mapEntityToDTO(PreOperativeAssessment preOperativeAssessment) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(preOperativeAssessment, PreOperativeAssessmentDTO.class);
    }
}
