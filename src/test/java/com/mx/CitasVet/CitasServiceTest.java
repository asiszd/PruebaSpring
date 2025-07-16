package com.mx.CitasVet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mx.CitasVet.Dominio.Cita;
import com.mx.CitasVet.Repository.CitaRepository;
import com.mx.CitasVet.Service.CitaService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitasServiceTest {

	@Autowired
	private CitaService citaservice;
	
	@Autowired
	private CitaRepository citaRepository;
	
	private Cita cita;
	
	@BeforeAll
	void datos() {
		cita = new Cita();
		cita.setMotivo("Consulta");
		cita.setMascotaNombre("BRUNO");
		cita.setDuenio("ASIS");
		cita = citaRepository.save(cita);
	}
	
	
	@Test
	void nuevaCita() {
		Cita nuevaCita = new Cita();
		nuevaCita.setDuenio("MAFER");
		nuevaCita.setMascotaNombre("CANELA");
		nuevaCita.setMotivo("BAÑO");
		
		Cita guardada = citaservice.guardarCita(nuevaCita);
		
		assertNotNull(guardada.getId());
		assertEquals("BAÑO", guardada.getMotivo());
		assertNotEquals(cita.getId(), guardada.getId());
	}
	
	@Test
	void actualizarCita() {
		 Cita cita = new Cita();
		 cita.setDuenio("ASIS");
		 cita.setMotivo("Chequeo");
		 cita.setMascotaNombre("Bruno");
		 Cita guardada = citaRepository.save(cita);

		 Cita datosActualizados = new Cita();
		 datosActualizados.setId(guardada.getId());
		 datosActualizados.setDuenio("JUAN");
		 datosActualizados.setMotivo("Esterilización");
		 datosActualizados.setMascotaNombre("Koko");
		 
		 Cita actualizada = citaservice.guardarCita(datosActualizados);
		 
		 assertEquals("Esterilización", actualizada.getMotivo());
	     assertEquals("Koko", actualizada.getMascotaNombre());
	}
	
	@Test
	void actualizarCitaFail() {
		 Cita cita = new Cita();
		 cita.setDuenio("ASIS");
		 cita.setMotivo("Chequeo");
		 cita.setMascotaNombre("Bruno");
		 Cita guardada = citaRepository.save(cita);

		 Cita datosActualizados = new Cita();
		 datosActualizados.setId(guardada.getId());
		 datosActualizados.setDuenio("JUAN");
		 datosActualizados.setMotivo("Esterilización");
		 datosActualizados.setMascotaNombre("Koko");
		 
		 Cita actualizada = citaservice.guardarCita(datosActualizados);
		 
		 assertEquals("Esterilización", actualizada.getMotivo());
	     assertEquals("Bruno", actualizada.getMascotaNombre());
	}

}
