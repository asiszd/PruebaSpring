package com.mx.CitasVet.Service;


import java.util.List;

import com.mx.CitasVet.Dominio.Cita;

public interface CitaService {
	
	Cita guardarCita(Cita cita);
	
    Cita obtenerCita(int id);
    
    List<Cita> listarCitas();
        
    void eliminarCita(int id);
}
