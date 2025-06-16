package com.jorgeroberto.park_api.web.controllers;

import com.jorgeroberto.park_api.entities.Customer;
import com.jorgeroberto.park_api.jwt.JwtUserDetails;
import com.jorgeroberto.park_api.repositories.projection.CustomerProjection;
import com.jorgeroberto.park_api.services.CustomerService;
import com.jorgeroberto.park_api.services.UserService;
import com.jorgeroberto.park_api.web.dto.CustomerCreateDto;
import com.jorgeroberto.park_api.web.dto.CustomerResponseDto;
import com.jorgeroberto.park_api.web.dto.PageableDto;
import com.jorgeroberto.park_api.web.dto.mapper.CustomerMapper;
import com.jorgeroberto.park_api.web.dto.mapper.PageableMapper;
import com.jorgeroberto.park_api.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final UserService userService;

    @Operation(summary = "Criar um novo cliente", description = "Recurso para criação de novo cliente, que esteja " +
            "vinculado há um usuário cadastrado. A Requisição exige o uso de Bearer Token e o Acesso é restrito ao role = CUSTOMER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil ADMIN",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "CPF de cliente já cadastrado no sistema",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDto> save(@RequestBody @Valid CustomerCreateDto dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails)
    {
        Customer customer = CustomerMapper.toCustomer(dto);
        customer.setUser(userService.findById(userDetails.getId()));
        customerService.save(customer);
        return ResponseEntity.status(201).body(CustomerMapper.toDto(customer));
    }

    @Operation(summary = "Localizar um cliente", description = "Recurso para localizar um cliente por um id. " +
            "A Requisição exige o uso de Bearer Token e o Acesso é restrito ao role = ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil CUSTOMER",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return ResponseEntity.status(200).body(CustomerMapper.toDto(customer));
    }

    @Operation(summary = "Recuperar uma lista de clientes", description = "A Requisição exige o uso de Bearer Token " +
            "e o Acesso é restrito ao role = ADMIN",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                @Parameter(in = QUERY, name = "page",
                    content = @Content (schema = @Schema(type = "integer", defaultValue = "0")),
                    description = "Representa a página retornada"
                ),
                @Parameter(in = QUERY, name = "size",
                        content = @Content (schema = @Schema(type = "integer", defaultValue = "20")),
                        description = "Representa o total de elementos por página"
                ),
                @Parameter(in = QUERY, name = "sort", hidden = true,
                        content = @Content (schema = @Schema(type = "string", defaultValue = "id,asc")),
                        description = "Representa a ordenação dos resultados. " +
                                "Aceita múltiplos critérios de ordenação são suportados."
                )
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = CustomerResponseDto.class))),
                @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil CUSTOMER",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> findAll(@Parameter(hidden = true) @PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        Page<CustomerProjection> customers = customerService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(customers));
    }

    @Operation(summary = "Recuperar dados de um cliente autenticado", description = "A Requisição exige o uso de Bearer Token " +
            "e o Acesso é restrito ao role = CUSTOMER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil ADMIN",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/details")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDto> getDetails(@AuthenticationPrincipal JwtUserDetails userDetails) { //pega o id do logado
        Customer customer = customerService.findByUserId(userDetails.getId());
        return ResponseEntity.ok(CustomerMapper.toDto(customer));
    }
}