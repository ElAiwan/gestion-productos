package ni.edu.uam.gestion_productos.repository;

import ni.edu.uam.gestion_productos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Integer> {
}
