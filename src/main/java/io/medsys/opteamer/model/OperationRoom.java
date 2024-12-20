package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.OperationRoomState;
import io.medsys.opteamer.model.enums.OperationRoomType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "OPERATION_ROOMS")
@Data
public class OperationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_nr")
    private Integer roomNr;

    @Column(name = "building_block")
    private String buildingBlock;

    @Column(name = "floor")
    private String floor;

    @Column(name = "type")
    private OperationRoomType type;

    @Column(name = "state")
    private OperationRoomState state;
}
