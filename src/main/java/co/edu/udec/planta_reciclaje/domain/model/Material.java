package co.edu.udec.planta_reciclaje.domain.model;

import co.edu.udec.planta_reciclaje.domain.valueobjects.idMaterial;
import co.edu.udec.planta_reciclaje.domain.exceptions.Exception;
import co.edu.udec.planta_reciclaje.domain.enums.clasificacionMaterial;
import co.edu.udec.planta_reciclaje.domain.enums.unidadMedida;
import co.edu.udec.planta_reciclaje.domain.valueobjects.tiempoEstimado;
import co.edu.udec.planta_reciclaje.domain.valueobjects.Precio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Material {
    private final idMaterial id; //Identidad única
    
    //Atributos
    private String nombreMaterial;
    private clasificacionMaterial clasificacion;
    private unidadMedida unidadMedida;
    private Precio precioUnidad;
    private tiempoEstimado tiempo;
    private final List<EtapaProceso> etapa = new ArrayList<>();
    private int indiceEtapaActual = -1;
    
    public Material(idMaterial id,  String nombreMaterial, clasificacionMaterial clasificacion, unidadMedida unidadMedida, Precio precioUnidad, tiempoEstimado tiempo) {
        if (id == null) throw new Exception("Id requerido");
        if (nombreMaterial == null) throw new Exception("Nombre requerido");
        if (clasificacion == null) throw new Exception("Clasificación requerida");
        if (unidadMedida == null) throw new Exception("Unidad de medida requerida");
        if (precioUnidad == null) throw new Exception("Precio por unidad requerido");
        if (tiempo == null) throw new Exception("Tiempo estimado requerido");
        this.id = id;
        this.nombreMaterial = nombreMaterial;
        this.clasificacion = clasificacion;
        this.unidadMedida = unidadMedida;
        this.precioUnidad = precioUnidad;
        this.tiempo = tiempo;
    }
    
    idMaterial id() { return id; }
    public String nombre() { return nombreMaterial; }
    public clasificacionMaterial clasificacion() { return clasificacion; }
    public unidadMedida unidadMedida() { return unidadMedida; }
    public Precio precioUnidad() { return precioUnidad; }
    public tiempoEstimado tiempo() { return tiempo; }
    
    public void agregarEtapa(EtapaProceso etapa) {
        if (etapa == null) throw new Exception("Etapa inválida");
        this.etapa.add(etapa);
        if (indiceEtapaActual == -1) indiceEtapaActual = 0;
}

    public List<EtapaProceso> etapas() { return Collections.unmodifiableList(etapa); }

    public Optional<EtapaProceso> etapaActual() {
        if (indiceEtapaActual == -1 || etapa.isEmpty()) return Optional.empty();
        return Optional.of(etapa.get(indiceEtapaActual));
    }

    public void avanzarEtapa() {
        if (etapa.isEmpty()) throw new Exception("No hay etapas definidas");
        if (indiceEtapaActual >= etapa.size() - 1) throw new Exception("Ya está en la última etapa");
        indiceEtapaActual++;
    }

    public BigDecimal calcularCostoporCantidad(BigDecimal cantidad) {
        if (cantidad == null) throw new Exception("Cantidad requerida");
        if (cantidad.compareTo(BigDecimal.ZERO) <= 0) throw new Exception("Cantidad debe ser mayor que cero");
        return precioUnidad.monto().multiply(cantidad);
    }

    public idMaterial getId() {return id;}

}