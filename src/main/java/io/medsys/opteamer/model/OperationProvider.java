package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.OperationProviderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OPERATION_PROVIDERS")
@Getter
@Setter
@NoArgsConstructor
public class OperationProvider {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OperationProviderType type;
}