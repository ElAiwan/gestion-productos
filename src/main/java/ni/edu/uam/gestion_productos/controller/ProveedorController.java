package ni.edu.uam.gestion_productos.controller;

import ni.edu.uam.gestion_productos.entity.Proveedor;
import ni.edu.uam.gestion_productos.repository.ProveedorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorRepository repository;

    public ProveedorController(ProveedorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Proveedor> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return repository.save(proveedor);
    }
}
