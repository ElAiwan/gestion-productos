package ni.edu.uam.gestion_productos.controller;

import ni.edu.uam.gestion_productos.dto.ProductoRequestDTO;
import ni.edu.uam.gestion_productos.entity.Producto;
import ni.edu.uam.gestion_productos.service.ProductoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Integer id) {
        return productoService.buscarPorId(id);
    }

    @PostMapping
    public Producto guardar(
            @RequestBody ProductoRequestDTO dto) {

        return productoService.guardar(dto);
    }
}
