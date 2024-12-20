package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ASSETS")
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    private String name;

}
