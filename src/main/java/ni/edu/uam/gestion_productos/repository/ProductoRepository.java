package ni.edu.uam.gestion_productos.repository;

import ni.edu.uam.gestion_productos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository
        extends JpaRepository<Producto, Integer> {

    List<Producto> findByCategoriaId(Integer categoriaId);
}
