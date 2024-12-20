package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.PatientDTO;
import io.medsys.opteamer.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 19.
 */

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDTO toPatientDTO(Patient patient);

    Patient toPatient(PatientDTO patientDTO);

}
