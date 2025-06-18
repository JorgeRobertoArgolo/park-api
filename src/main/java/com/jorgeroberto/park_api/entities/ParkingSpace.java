package com.jorgeroberto.park_api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_space")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ParkingSpace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParkingStatus status;

    // Data/hora em que o usuário foi criado
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Data/hora da última atualização no usuário
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Usuário responsável pela criação
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    // Usuário responsável pela última atualização
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    public enum ParkingStatus {
        FREE,
        BUSY
    }

}
