package io.medsys.opteamer.model;

import io.medsys.opteamer.model.embeddedids.RoomInventoryId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
