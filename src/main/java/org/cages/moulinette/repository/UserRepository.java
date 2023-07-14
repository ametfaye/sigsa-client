package org.cages.moulinette.repository;

import java.util.List;

import org.cages.moulinette.model.UserInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserInfos, Long> {
	
	public UserInfos findOneByIdentifiant(String identifiant);

	@Query(value = "SELECT * FROM user_infos WHERE deleted = 0 and user_id != ?1 limit 200", nativeQuery = true)
	public List<UserInfos> findAllNotDeleted(Long idUser);

	@Query(value = "SELECT count(*) FROM user_infos WHERE deleted = 0 and active = 1", nativeQuery = true)
	public int countActifsUsers();

	@Query(value = "SELECT count(*) FROM user_infos WHERE deleted = 0 and active = 0", nativeQuery = true)
	public int countInactifsUsers();
	
	@Query(value = "SELECT * FROM user_infos u "
			+ " join agent a on a.a_id = u.a_id "
			+ " join personne_physique pph on a.pph_id = pph.pph_id "
			+ " WHERE pph.email = ?1 limit 1", nativeQuery = true)
	public UserInfos findOneByEmail(String email);
}
