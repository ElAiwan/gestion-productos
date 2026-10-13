package ni.edu.uam.gestion_productos.service;

import ni.edu.uam.gestion_productos.dto.CategoriaRequestDTO;
import ni.edu.uam.gestion_productos.entity.Categoria;
import ni.edu.uam.gestion_productos.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categoria no encontrada"));
    }

    public Categoria guardar(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();

        categoria.setNombre(dto.getNombre());
        // Si no se envía "activa", la categoría se crea activa
        categoria.setActiva(dto.getActiva() == null || dto.getActiva());

        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Integer id, CategoriaRequestDTO dto) {

        Categoria categoria = buscarPorId(id);

        categoria.setNombre(dto.getNombre());
        if (dto.getActiva() != null) {
            categoria.setActiva(dto.getActiva());
        }

        return categoriaRepository.save(categoria);
    }

    public void eliminar(Integer id) {

        Categoria categoria = buscarPorId(id);

        // Usa el lado inverso de la relación Uno a Muchos (Categoria.productos)
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            throw new RuntimeException(
                    "No se puede eliminar la categoria porque tiene productos asociados");
        }

        categoriaRepository.delete(categoria);
    }
}
