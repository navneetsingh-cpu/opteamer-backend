package io.medsys.opteamer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomInventoryDTO {
    private Long assetId;
    private Long operationRoomId;
    private AssetDTO asset;
    private OperationRoomDTO operationRoom;
    private Integer count;
}
