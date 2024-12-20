package io.medsys.opteamer.dto;

import io.medsys.opteamer.model.enums.OperationProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 16.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperationProviderDTO {
    private OperationProviderType type;
}
