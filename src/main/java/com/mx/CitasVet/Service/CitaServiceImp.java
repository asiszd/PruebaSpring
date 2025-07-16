package com.mx.CitasVet.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mx.CitasVet.Dominio.Cita;
import com.mx.CitasVet.Dominio.RazasPerroResponse;
import com.mx.CitasVet.Repository.CitaRepository;

@Service
public class CitaServiceImp implements CitaService, RazaService{
	
	@Autowired
	CitaRepository repo;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public Cita guardarCita(Cita cita) {
		return repo.save(cita);
	}

	@Override
	public Cita obtenerCita(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public List<Cita> listarCitas() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}


	@Override
	public void eliminarCita(int id) {
		repo.deleteById(id);
	}
	
	public List<Cita> listarCitasPorTipoMascota(String tipo){
		return repo.findAll().stream().filter( c -> 
				c.getMascotaTipo().equalsIgnoreCase(tipo)).collect(Collectors.toList());
	}
	
	public List<String> obtenerRazas(){
		String url = "https://dog.ceo/api/breeds/list";
		ResponseEntity<RazasPerroResponse> response = restTemplate.getForEntity(url, RazasPerroResponse.class);
		
		if(response.getStatusCode() == HttpStatus.OK && "success".equals(response.getBody().getStatus())){
			return response.getBody().getMessage();
		}
		return Collections.emptyList();
	}
	
	public void respaldar() {
		repo.prRespaldoCitas();
	}

}
