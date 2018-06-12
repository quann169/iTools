package vn.com.fwd.domain.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.com.fwd.domain.model.Register;

public interface RegisterRepository extends JpaRepository<Register, Integer> {
	
    @Query(value = "SELECT x FROM Register x WHERE x.fullName LIKE :name% ORDER BY x.id", 
    		countQuery = "SELECT COUNT(x) FROM Register x WHERE x.fullName LIKE :name%")
    Page<Register> findByNameLike(@Param("name") String name, Pageable page);
    
    @Query(value="select x from Register x where upper(x.fullName) = (:userName)")
    Register findUserbyUsername(@Param("userName") String userName);
    
}
