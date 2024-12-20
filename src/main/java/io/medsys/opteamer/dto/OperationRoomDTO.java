package io.medsys.opteamer.dto;

import io.medsys.opteamer.model.enums.OperationRoomState;
import io.medsys.opteamer.model.enums.OperationRoomType;
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
public class OperationRoomDTO {
    private Long id;
    private Integer roomNr;
    private String buildingBlock;
    private String floor;
    private OperationRoomType type;
    private OperationRoomState state;
}
