/**
*Clase ServicioEmpresa.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.proyecto.modelo.Empresa;
import com.oscar.proyecto.repositorios.EmpresaRepository;

@Service
public class ServicioEmpresa {

    @Autowired
    private EmpresaRepository empresaRepo;

   
    public List<Empresa> listarEmpresas() {
        return empresaRepo.findAll();
    }

   
    public Empresa guardarEmpresa(Empresa empresa) {
        return empresaRepo.save(empresa);
    }

    
    public void eliminarEmpresa(Long id) {
        empresaRepo.deleteById(id);
    }

   
    public Empresa buscarPorId(Long id) {
        return empresaRepo.findById(id).orElse(null);
    }
}
