package com.mgcss.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTecnicoRepository extends JpaRepository<TecnicoEntity, Long> {

}
