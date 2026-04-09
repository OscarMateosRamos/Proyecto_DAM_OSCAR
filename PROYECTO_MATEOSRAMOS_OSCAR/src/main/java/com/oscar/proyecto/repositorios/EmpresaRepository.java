/**
*Clase EmpresaRepository.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscar.proyecto.modelo.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
