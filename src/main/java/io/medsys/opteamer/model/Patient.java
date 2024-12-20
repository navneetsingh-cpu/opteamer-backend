package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.OperationRoomState;
import io.medsys.opteamer.model.enums.OperationRoomType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PATIENTS")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    private String name;

    private String nin;

    @Entity
    @Table(name = "OPERATION_ROOMS")
    @Data
    public static class OperationRoom {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Integer roomNr;

        private String buildingBlock;

        private String floor;

        private OperationRoomType type;

        private OperationRoomState state;

    }
}
