package io.medsys.opteamer.model;

import io.medsys.opteamer.model.embeddedids.RoomInventoryId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author degijanos
 * @version 1.0
 * @since 2024. 06. 12.
 */

@Entity
@Table(name = "ROOM_INVENTORY")
@NoArgsConstructor
@Getter
@Setter
public class RoomInventory {

    @EmbeddedId
    private RoomInventoryId roomInventoryId;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", columnDefinition = "BIGINT")
    private OperationRoom operationRoom;

    @ManyToOne
    @MapsId("assetId")
    @JoinColumn(name = "asset_id", columnDefinition = "BIGINT")
    private Asset asset;

    private Integer count;
}
