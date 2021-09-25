package hotel.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hotel.manager.model.CheckIn;

@Transactional
@Repository
public interface CheckInRepository extends JpaRepository<CheckIn,Long>{

	@Query(value = "SELECT * FROM checkin WHERE hospede_id = :hospedeId AND data_saida IS NULL" , nativeQuery = true)
	CheckIn obterCheckIn(@Param("hospedeId") Long hospedeId);
}
