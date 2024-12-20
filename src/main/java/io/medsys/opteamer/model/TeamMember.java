package io.medsys.opteamer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author degijanos
 * @version 1.0
 * @since 2024. 06. 13.
 */

@Entity
@Table(name = "TEAM_MEMBERS")
@Getter
@Setter
@NoArgsConstructor
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "opprovider_id")
    private OperationProvider operationProvider;
}
