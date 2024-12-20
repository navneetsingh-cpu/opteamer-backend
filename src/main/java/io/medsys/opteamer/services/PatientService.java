package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.PatientDTO;
import io.medsys.opteamer.model.Patient;
import io.medsys.opteamer.repositories.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatientService {

    PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<PatientDTO> getPatientById(Long id) {
        try {
            Patient patient = patientRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(patient));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<PatientDTO> getAllPatients() {
        List<PatientDTO> list = new ArrayList<>();
        Iterable<Patient> allPatients = patientRepository.findAll();
        allPatients.forEach(patient -> list.add(mapEntityToDTO(patient)));
        return list;
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient = patientRepository.save(patient);
        return modelMapper.map(patient, PatientDTO.class);
    }

    public Optional<PatientDTO> updatePatient(Long id, PatientDTO patientDTO) {
        return patientRepository.findById(id).map(patient -> {
            patient.setName(patientDTO.getName());
            patient.setNin(patientDTO.getNin());
            patientRepository.save(patient);
            return mapEntityToDTO(patient);
        });
    }

    public boolean deletePatient(Long id) {
        return patientRepository.findById(id).map(patient -> {
            patientRepository.delete(patient);
            return true;
        }).orElse(false);
    }

    private PatientDTO mapEntityToDTO(Patient patient) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(patient, PatientDTO.class);
    }
}
