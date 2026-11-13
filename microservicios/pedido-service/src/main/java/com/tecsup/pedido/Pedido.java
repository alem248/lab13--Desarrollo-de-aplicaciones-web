package com.tecsup.pedido;

import java.math.BigDecimal;

public record Pedido(Long id, Long clienteId, String estado, BigDecimal total) {}
