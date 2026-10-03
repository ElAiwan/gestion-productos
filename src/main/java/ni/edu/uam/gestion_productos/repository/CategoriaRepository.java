package ni.edu.uam.gestion_productos.repository;

import ni.edu.uam.gestion_productos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository
        extends JpaRepository<Categoria, Integer> {
}
