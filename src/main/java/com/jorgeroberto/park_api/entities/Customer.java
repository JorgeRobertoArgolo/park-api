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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString(onlyExplicitlyIncluded = true)
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include @ToString.Include
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

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
}
