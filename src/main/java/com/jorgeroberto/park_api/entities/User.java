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

/**
 * Representa a entidade {@code User} do sistema, responsável por armazenar
 * os dados dos usuários cadastrados, como nome de usuário, senha criptografada,
 * papel (admin ou cliente), e informações de auditoria.
 * <p>
 * Esta classe é mapeada para a tabela {@code users} no banco de dados.
 * </p>
 *
 * <p><strong>Campos principais:</strong></p>
 * <ul>
 *   <li>{@code id} - Identificador único do usuário (chave primária).</li>
 *   <li>{@code username} - Nome de login do usuário (único e obrigatório).</li>
 *   <li>{@code password} - Senha criptografada (obrigatória).</li>
 *   <li>{@code role} - Papel do usuário no sistema (ADMIN ou CUSTOMER).</li>
 *   <li>{@code createdAt / updatedAt} - Datas de criação e modificação.</li>
 *   <li>{@code createdBy / updatedBy} - Usuário responsável pelas ações.</li>
 * </ul>
 *
 * @author Jorge Roberto
 * @since 2025-05-22
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //Apenas o campo "id" será usado para equals/hashCode
@ToString(onlyExplicitlyIncluded = true) //Apenas o campo "id" será exibido no toString()
@EntityListeners(AuditingEntityListener.class) //auditoria
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include // Inclui o campo no equals/hashCode
    @ToString.Include // Inclui o campo no toString()
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    //Tem que levar em conta que a senha não será armazenada no banco de dados, e sim a criptografia
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    // Papel do usuário (enum). Armazenado como texto no banco.
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CUSTOMER;

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

    public enum Role {
        ROLE_ADMIN,
        ROLE_CUSTOMER
    }
}
