package com.tecsup.cliente;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final AtomicLong ids = new AtomicLong(2);
    private final ConcurrentHashMap<Long, Cliente> clientes = new ConcurrentHashMap<>();

    public ClienteController() {
        clientes.put(1L, new Cliente(1L, "Ana Torres", "ana@example.com"));
        clientes.put(2L, new Cliente(2L, "Luis Rojas", "luis@example.com"));
    }

    @GetMapping
    public List<Cliente> listar() { return clientes.values().stream().sorted((a,b) -> a.id().compareTo(b.id())).toList(); }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Long id) {
        Cliente cliente = clientes.get(id);
        if (cliente == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        return cliente;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crear(@RequestBody Cliente entrada) {
        long id = ids.incrementAndGet();
        Cliente cliente = new Cliente(id, entrada.nombres(), entrada.correo());
        clientes.put(id, cliente);
        return cliente;
    }
}
