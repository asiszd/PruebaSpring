package com.mx.CitasVet.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mx.CitasVet.Dominio.Cita;
import com.mx.CitasVet.Service.CitaServiceImp;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

	@Autowired
	CitaServiceImp citaService;

	
	@GetMapping
	@Operation(summary = "LISTAR CITAS", description = "Trae un listado de todas las Citas registradas en la base de datos.")
	public ResponseEntity<?> listar() {
		List<Cita> citas = citaService.listarCitas();
		return citas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(citas);
	}
	 
	 
	@PostMapping("/guardar")
	@Operation(summary = "GUARDAR CITA", description = "Recibe como parametro un objeto de tipo Cita para almacenarla en la BD")
	public ResponseEntity<?> guardar(@RequestBody Cita c){
		return ResponseEntity.status(HttpStatus.CREATED).body(citaService.guardarCita(c));
	}
	 
	 
	@PutMapping("/editar")
	@Operation(summary = "EDITAR CITA", description = "Recibe como parametro un objeto de tipo Cita para sobreescribirla en la base de datos")
	public ResponseEntity<?> editar(@RequestBody Cita c){
		Cita existe = citaService.obtenerCita(c.getId());
		return (existe != null) ? ResponseEntity.ok(citaService.guardarCita(c))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"mensaje\":\"No existe una cita con el ID: " + c.getId() + ".\"}");			
	}
	 
	 
	@DeleteMapping("/eliminar/{id}")
	@Operation(summary = "ELIMINAR CITA POR ID", description = "Toma como parámetro un id por medio de la url para eliminar la cita de la base de datos.")
	public ResponseEntity<?> eliminar(@PathVariable int id){
		citaService.eliminarCita(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/{id}")
	@Operation(summary = "BUSCAR CITA POR ID", description = "Toma como parámetro un ID por medio de la url para buscar la cita.")
	public ResponseEntity<?> buscar(@PathVariable int id){
		return ResponseEntity.ok(citaService.obtenerCita(id));
	}
	
	@GetMapping("/tipoMascota/{tipo}")
	@Operation(summary = "BUSCAR CITA POR SU TIPO", description = "Toma como parámetro un STRING Tipo por medio de la url para buscar la cita.")
	public ResponseEntity<?> buscar(@PathVariable String tipo){
		return ResponseEntity.ok(citaService.listarCitasPorTipoMascota(tipo));
	}
	 
	
	// ENDPOINT PARA CONSUMO DE API EXTERNA
	@GetMapping("/RazasPerro")
	@Operation(summary = "OBTENER RAZAS DE PERRO", description = "Llama a una API externa para traer una lista de razas de perro")
	public ResponseEntity<?> obtenerRazas() {
        return ResponseEntity.ok(citaService.obtenerRazas());
    }
	
	
	@GetMapping("respaldar")
	@Operation(summary = "RESPALDAR CITAS", description = "Crea un respaldo de la tabla en la base de datos")
	public ResponseEntity<?> respaldar(){
		citaService.respaldar();
		return ResponseEntity.ok("{\"mensaje\":\"Se ha respaldado la información contenida en la tabla de Citas.\"}");
	}

}
