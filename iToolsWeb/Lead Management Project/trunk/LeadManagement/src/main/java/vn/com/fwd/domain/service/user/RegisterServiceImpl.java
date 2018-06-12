package vn.com.fwd.domain.service.user;

import java.util.Date;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.fwd.domain.common.exception.ResourceNotFoundException;
import vn.com.fwd.domain.model.Register;
import vn.com.fwd.domain.repository.user.RegisterRepository;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
	
	@Inject
    protected RegisterRepository registerRepository;

	@Override
	public int register(Register reg) {
		Date now = new DateTime().toDate();
        if (reg.getRegisterDate() == null) {
        	reg.setRegisterDate(now);
        }
        
        try {
        	registerRepository.save(reg);
        	return reg.getId();
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return 0;
        }
	}
	
	@Override
    public void save(Register reg, String rawPassword) {

        Date now = new DateTime().toDate();
        if (reg.getRegisterDate() == null) {
        	reg.setRegisterDate(now);
        }

        registerRepository.save(reg);
    }

    @Override
    @Transactional(readOnly = true)
    public Register findOne(Integer id) {
    	Register user = registerRepository.findOne(id);
        if (user == null) {
            throw new ResourceNotFoundException("User [id=" + id
                    + "] is not found.");
        }
        return user;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Register findUser(String userName) {
    	Register user = registerRepository.findUserbyUsername(userName);       
        return user;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Register> findAll(Pageable pageable) {
        Page<Register> page = registerRepository.findAll(pageable);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Register> findByNameLike(String name, Pageable pageable) {
        String query = name; // TODO escape
        Page<Register> page = registerRepository.findByNameLike(query, pageable);
        return page;
    }

    @Override
    public void delete(Register user) {
    	registerRepository.delete(user);
    }

}
