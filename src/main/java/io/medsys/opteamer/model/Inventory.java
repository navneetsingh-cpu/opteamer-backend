package io.medsys.opteamer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "INVENTORY")
@NoArgsConstructor
@Getter
@Setter
public class Inventory {

    @Id
    private Long assetId;

    @OneToOne
    @MapsId
    @PrimaryKeyJoinColumn(name = "asset_id")
    private Asset asset;

    private Integer count;
}
