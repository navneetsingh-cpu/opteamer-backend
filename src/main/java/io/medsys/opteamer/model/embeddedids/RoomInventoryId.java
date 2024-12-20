package io.medsys.opteamer.model.embeddedids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * @author degijanos
 * @version 1.0
 * @since 2024. 06. 12.
 */

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomInventoryId implements Serializable {

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "asset_id")
    private Long assetId;
}
