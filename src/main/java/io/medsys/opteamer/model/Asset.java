package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.AssetType;
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
@Table(name = "ASSETS")
@NoArgsConstructor
@Getter
@Setter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    private String name;
}
