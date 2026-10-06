package ni.edu.uam.gestion_productos.repository;

import ni.edu.uam.gestion_productos.entity.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtiquetaRepository
        extends JpaRepository<Etiqueta, Integer> {
}
