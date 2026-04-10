/**
*Clase FormacionEmpresaRepository.java
*
*@author Oscar Mateos Ramos
*@version
*/
package com.oscar.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscar.proyecto.modelo.FormacionEmpresa;

@Repository
public interface FormacionEmpresaRepository extends JpaRepository<FormacionEmpresa, Long>  {

}
