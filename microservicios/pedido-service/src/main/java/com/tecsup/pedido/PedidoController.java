package com.tecsup.pedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final AtomicLong ids = new AtomicLong(1);
    private final ConcurrentHashMap<Long, Pedido> pedidos = new ConcurrentHashMap<>();

    public PedidoController() {
        pedidos.put(1L, new Pedido(1L, 1L, "RECIBIDO", new BigDecimal("49.90")));
    }

    @GetMapping
    public List<Pedido> listar() { return pedidos.values().stream().sorted((a,b) -> a.id().compareTo(b.id())).toList(); }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Long id) {
        Pedido pedido = pedidos.get(id);
        if (pedido == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado");
        return pedido;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido crear(@RequestBody Pedido entrada) {
        long id = ids.incrementAndGet();
        Pedido pedido = new Pedido(id, entrada.clienteId(), entrada.estado(), entrada.total());
        pedidos.put(id, pedido);
        return pedido;
    }
}
