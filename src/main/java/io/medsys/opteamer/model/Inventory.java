package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "INVENTORY")
@Data
public class Inventory {

    @Id
    @Column(name = "asset_id")
    private Long assetId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private Integer count;

}
