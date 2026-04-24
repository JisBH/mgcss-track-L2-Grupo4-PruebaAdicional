package com.mgcss.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSolicitudRepository extends JpaRepository<SolicitudEntity, Long>{

}
