package ni.edu.uam.gestion_productos.controller;

import ni.edu.uam.gestion_productos.dto.ProveedorRequestDTO;
import ni.edu.uam.gestion_productos.entity.Proveedor;
import ni.edu.uam.gestion_productos.service.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public List<Proveedor> listar() {
        return proveedorService.listar();
    }

    @GetMapping("/{id}")
    public Proveedor buscar(@PathVariable Integer id) {
        return proveedorService.buscarPorId(id);
    }

    @PostMapping
    public Proveedor guardar(@RequestBody ProveedorRequestDTO dto) {
        return proveedorService.guardar(dto);
    }

    @PutMapping("/{id}")
    public Proveedor actualizar(
            @PathVariable Integer id,
            @RequestBody ProveedorRequestDTO dto) {

        return proveedorService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        proveedorService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
