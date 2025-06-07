package org.fsierra.spring.pagos.models.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Entidad que representa un pago realizado por un usuario")
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del pago", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "El id del usuario no puede ser nulo")
    @Schema(description = "Identificador del usuario que realiza el pago", example = "5")
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Schema(description = "Monto pagado por el usuario", example = "150.75")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @NotNull(message = "La fecha de pago no puede ser nula")
    @Schema(description = "Fecha en que se realizó el pago", example = "2025-06-01")
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @NotNull(message = "El estado del pago no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado del pago (PENDIENTE, COMPLETADO, CANCELADO)", example = "PENDIENTE")
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }
}
