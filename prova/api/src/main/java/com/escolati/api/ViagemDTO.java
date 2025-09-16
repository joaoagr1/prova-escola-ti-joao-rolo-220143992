package com.escolati.api;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ViagemDTO(
    String nome,
    LocalDate dataSaida,
    LocalDate dataChegada,
    BigDecimal valor
) {
}
