package com.jorgeroberto.park_api.web.dto;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CustomerResponseDto {

    private Long id;

    private String name;

    private String cpf;
}
