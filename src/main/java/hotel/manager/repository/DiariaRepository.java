package hotel.manager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hotel.manager.model.Diaria;

@Transactional
@Repository
public interface DiariaRepository extends JpaRepository<Diaria, Long>{
	
	@Query(value = "SELECT * FROM diaria WHERE periodo = :periodo AND hospede_id = :hospedeId AND contabilizado = true" , nativeQuery = true)
	List<Diaria> obterDiariasContabilizadas(@Param("periodo") LocalDate periodo, @Param("hospedeId") Long hospedeId);
}
