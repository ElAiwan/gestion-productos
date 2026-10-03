package ni.edu.uam.gestion_productos.repository;

import ni.edu.uam.gestion_productos.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository
        extends JpaRepository<Proveedor, Integer> {
}
