package ni.edu.uam.gestion_productos.service;

import ni.edu.uam.gestion_productos.dto.ProveedorRequestDTO;
import ni.edu.uam.gestion_productos.entity.Proveedor;
import ni.edu.uam.gestion_productos.repository.ProductoRepository;
import ni.edu.uam.gestion_productos.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;

    public ProveedorService(ProveedorRepository proveedorRepository,
                            ProductoRepository productoRepository) {
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
    }

    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    public Proveedor buscarPorId(Integer id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Proveedor no encontrado"));
    }

    public Proveedor guardar(ProveedorRequestDTO dto) {

        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(dto.getNombre());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setCorreo(dto.getCorreo());
        // Si no se envía "activo", el proveedor se crea activo
        proveedor.setActivo(dto.getActivo() == null || dto.getActivo());

        return proveedorRepository.save(proveedor);
    }

    public Proveedor actualizar(Integer id, ProveedorRequestDTO dto) {

        Proveedor proveedor = buscarPorId(id);

        proveedor.setNombre(dto.getNombre());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setCorreo(dto.getCorreo());
        if (dto.getActivo() != null) {
            proveedor.setActivo(dto.getActivo());
        }

        return proveedorRepository.save(proveedor);
    }

    public void eliminar(Integer id) {

        Proveedor proveedor = buscarPorId(id);

        if (productoRepository.existsByProveedorId(id)) {
            throw new RuntimeException(
                    "No se puede eliminar el proveedor porque tiene productos asociados");
        }

        proveedorRepository.delete(proveedor);
    }
}
