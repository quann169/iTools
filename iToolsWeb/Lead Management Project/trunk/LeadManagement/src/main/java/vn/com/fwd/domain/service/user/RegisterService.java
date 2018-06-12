package vn.com.fwd.domain.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.com.fwd.domain.model.Register;

public interface RegisterService {
	int register(Register reg);
	
	void save(Register reg, String rawPassword);

	Register findOne(Integer id);
	
	Register findUser(String username);

	Page<Register> findAll(Pageable pageable);

	Page<Register> findByNameLike(String name, Pageable pageable);

	void delete(Register user);
}
