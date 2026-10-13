package ni.edu.uam.gestion_productos.service;

import ni.edu.uam.gestion_productos.dto.ProductoRequestDTO;
import ni.edu.uam.gestion_productos.entity.Categoria;
import ni.edu.uam.gestion_productos.entity.Etiqueta;
import ni.edu.uam.gestion_productos.entity.Producto;
import ni.edu.uam.gestion_productos.entity.Proveedor;
import ni.edu.uam.gestion_productos.repository.CategoriaRepository;
import ni.edu.uam.gestion_productos.repository.EtiquetaRepository;
import ni.edu.uam.gestion_productos.repository.ProductoRepository;
import ni.edu.uam.gestion_productos.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final EtiquetaRepository etiquetaRepository;
    private final ProveedorRepository proveedorRepository;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository,
                           EtiquetaRepository etiquetaRepository,
                           ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.etiquetaRepository = etiquetaRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto guardar(ProductoRequestDTO dto) {

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new RuntimeException("Categoria no encontrada"));

        Producto producto = new Producto();

        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setExistencia(dto.getExistencia());
        producto.setCategoria(categoria);
        producto.setProveedor(buscarProveedor(dto.getProveedorId()));

        return productoRepository.save(producto);
    }

    public Producto actualizar(
            Integer id,
            ProductoRequestDTO dto) {

        Producto producto = buscarPorId(id);

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new RuntimeException("Categoria no encontrada"));

        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setExistencia(dto.getExistencia());
        producto.setCategoria(categoria);
        producto.setProveedor(buscarProveedor(dto.getProveedorId()));

        return productoRepository.save(producto);
    }

    // proveedorId es opcional: si no se envía, el producto queda sin proveedor
    private Proveedor buscarProveedor(Integer proveedorId) {

        if (proveedorId == null) {
            return null;
        }

        return proveedorRepository.findById(proveedorId)
                .orElseThrow(() ->
                        new RuntimeException("Proveedor no encontrado"));
    }

    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> listarPorCategoria(
            Integer categoriaId) {

        return productoRepository
                .findByCategoriaId(categoriaId);
    }

    public Producto agregarEtiqueta(
            Integer productoId,
            Integer etiquetaId) {

        Producto producto =
                buscarPorId(productoId);

        Etiqueta etiqueta =
                etiquetaRepository.findById(etiquetaId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Etiqueta no encontrada"));

        producto.getEtiquetas().add(etiqueta);

        return productoRepository.save(producto);
    }

    // Reto 1: elimina solo la asociación en producto_etiqueta;
    // el producto y la etiqueta siguen existiendo.
    public void quitarEtiqueta(
            Integer productoId,
            Integer etiquetaId) {

        Producto producto = buscarPorId(productoId);

        if (!etiquetaRepository.existsById(etiquetaId)) {
            throw new RuntimeException("Etiqueta no encontrada");
        }

        producto.getEtiquetas()
                .removeIf(etiqueta -> etiqueta.getId().equals(etiquetaId));

        productoRepository.save(producto);
    }

    // Reto 2: productos que tienen asociada una etiqueta.
    public List<Producto> listarPorEtiqueta(Integer etiquetaId) {

        if (!etiquetaRepository.existsById(etiquetaId)) {
            throw new RuntimeException("Etiqueta no encontrada");
        }

        return productoRepository.findByEtiquetasId(etiquetaId);
    }
}
