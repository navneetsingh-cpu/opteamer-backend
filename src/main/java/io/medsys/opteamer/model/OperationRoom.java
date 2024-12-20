package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.OperationRoomState;
import io.medsys.opteamer.model.enums.OperationRoomType;
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
@Table(name = "OPERATION_ROOMS")
@NoArgsConstructor
@Getter
@Setter
public class OperationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_nr")
    private Integer roomNr;

    @Column(name = "building_block")
    private String buildingBlock;

    @Column(name = "floor")
    private String floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OperationRoomType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OperationRoomState state;
}
