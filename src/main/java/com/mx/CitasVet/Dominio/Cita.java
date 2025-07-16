package com.mx.CitasVet.Dominio;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cita_seq")
	@SequenceGenerator(name="cita_seq", sequenceName = "S_ID_CITA", allocationSize = 1)
	private int id;
	
	// DATOS DE LA CITA
	@Column
    private Date fecha;
	@Column
	private String motivo;
	@Column
    private String duenio;
	@Column
    private String telefono;

    // DATOS DE LA MASCOTA
	@Column
    private String mascotaNombre;
	@Column
    private String mascotaTipo;
	@Column
    private String mascotaRaza;
	@Column
    private Integer mascotaEdad;
	@Column
    private Double mascotaPeso;
	
	


}
