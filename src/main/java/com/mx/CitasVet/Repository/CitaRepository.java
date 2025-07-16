package com.mx.CitasVet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.mx.CitasVet.Dominio.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer>{
	
	
	@Procedure(procedureName = "PR_RESPALDO_CITAS")
	public void prRespaldoCitas();

}
