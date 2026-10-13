package ni.edu.uam.gestion_productos.service;

import ni.edu.uam.gestion_productos.dto.EtiquetaRequestDTO;
import ni.edu.uam.gestion_productos.entity.Etiqueta;
import ni.edu.uam.gestion_productos.entity.Producto;
import ni.edu.uam.gestion_productos.repository.EtiquetaRepository;
import ni.edu.uam.gestion_productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;
    private final ProductoRepository productoRepository;

    public EtiquetaService(EtiquetaRepository etiquetaRepository,
                           ProductoRepository productoRepository) {
        this.etiquetaRepository = etiquetaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Etiqueta> listar() {
        return etiquetaRepository.findAll();
    }

    public Etiqueta buscarPorId(Integer id) {
        return etiquetaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Etiqueta no encontrada"));
    }

    public Etiqueta guardar(EtiquetaRequestDTO dto) {

        Etiqueta etiqueta = new Etiqueta();

        etiqueta.setNombre(dto.getNombre());

        return etiquetaRepository.save(etiqueta);
    }

    public Etiqueta actualizar(Integer id, EtiquetaRequestDTO dto) {

        Etiqueta etiqueta = buscarPorId(id);

        etiqueta.setNombre(dto.getNombre());

        return etiquetaRepository.save(etiqueta);
    }

    public void eliminar(Integer id) {

        Etiqueta etiqueta = buscarPorId(id);

        // Producto es el lado propietario de la relación Muchos a Muchos:
        // primero se quitan las asociaciones en producto_etiqueta
        List<Producto> productos = productoRepository.findByEtiquetasId(id);
        for (Producto producto : productos) {
            producto.getEtiquetas()
                    .removeIf(e -> e.getId().equals(id));
            productoRepository.save(producto);
        }

        etiquetaRepository.delete(etiqueta);
    }
}
