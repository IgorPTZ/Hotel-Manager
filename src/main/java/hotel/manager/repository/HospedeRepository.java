package hotel.manager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hotel.manager.model.Hospede;

@Transactional
@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long>{
	
	@Query(value = "SELECT H.ID, H.NOME, H.DOCUMENTO, H.TELEFONE, H.VALOR_GASTO FROM hospede H JOIN checkin C ON H.ID = C.HOSPEDE_ID WHERE C.DATA_SAIDA IS NULL" , nativeQuery = true)
	List<Hospede> obterHospedes();
	
	@Query(value = "SELECT H.ID, H.NOME, H.DOCUMENTO, H.TELEFONE, H.VALOR_GASTO FROM hospede H JOIN checkin C ON H.ID = C.HOSPEDE_ID WHERE C.DATA_SAIDA IS NULL" , nativeQuery = true)
	Page<Hospede> obterHospedes(Pageable paginacao);
	
	@Query(value = "SELECT H.ID, H.NOME, H.DOCUMENTO, H.TELEFONE, H.VALOR_GASTO FROM hospede H JOIN checkin C ON H.ID = C.HOSPEDE_ID WHERE C.DATA_SAIDA IS NOT NULL" , nativeQuery = true)
	Page<Hospede> obterExHospedes(Pageable paginacao);
}
