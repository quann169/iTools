package com.iToolsV2.dao;
 
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.Assessor;
import com.iToolsV2.entity.RoleAssessor;
import com.iToolsV2.entity.Roles;
import com.iToolsV2.form.AssessorForm;
import com.iToolsV2.model.AssessorInfo;
import com.iToolsV2.pagination.PaginationResult;
import lombok.extern.slf4j.Slf4j;
 
@Transactional
@Component("assessorDao")
@Slf4j
public class AssessorDAO {
	
	 // Config in WebSecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Autowired
	private SessionFactory sessionFactory;
 
    public Assessor findAccount(String userName) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Assessor.class.getName() + " assessor " //
	                + " Where assessor.userName = :userName ";
	        Query<Assessor> query = session.createQuery(sql, Assessor.class);
	        query.setParameter("userName", userName);
	        return query.getSingleResult();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
    
    public Assessor findAccountByID(int assessorID) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Assessor.class.getName() + " assessor " //
	                + " Where assessor.assessorID = :assessorID ";
	        Query<Assessor> query = session.createQuery(sql, Assessor.class);
	        query.setParameter("assessorID", assessorID);
	        return query.getSingleResult();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
    
    public List<Assessor> findAllAccountID() {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Assessor.class.getName() + " assessor ";
	        Query<Assessor> query = session.createQuery(sql, Assessor.class);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
    
    private int getMaxAccountID() {
    	List<Assessor> listAssesor = this.findAllAccountID();
    	int maxID = 0; 
    	for(Assessor cAssessor : listAssesor) {
    		int cID = cAssessor.getAssessorID();
    		if(maxID < cID)	maxID = cID;
    	}
    	return maxID;
    }
    
    public Assessor findAccountByEmail(String emailAddress) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Assessor.class.getName() + " assessor " //
	                + " Where assessor.emailAddress = :emailAddress ";
	        Query<Assessor> query = session.createQuery(sql, Assessor.class);
	        query.setParameter("emailAddress", emailAddress);
	        List<Assessor> lstAssessor = query.getResultList();
	        if (lstAssessor != null && lstAssessor.size() > 0)
	        	return lstAssessor.get(0);
	        else
	        	return null;
	        //return query.getSingleResult();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
    
    public AssessorForm findAssessorFormByID(int assessorID) {
    	Assessor assessor = this.findAccountByID(assessorID);
    	AssessorForm assessorForm = new AssessorForm();
    	if (assessor != null) {
    		assessorForm.setAssessorId(assessorID);
    		assessorForm.setName(assessor.getUserName());
    		//assessorForm.setPassword(assessor.getEncrytedPassword());
    		assessorForm.setFirstName(assessor.getFirstName());
    		assessorForm.setLastName(assessor.getLastName());
    		assessorForm.setEmailAddress(assessor.getEmailAddress());
    		assessorForm.setAddress(assessor.getAddress());
    		assessorForm.setPhone(assessor.getPhone());
    		assessorForm.setActive(assessor.isActive());
    		assessorForm.setLocked(assessor.isLocked());
    		assessorForm.setCompanyCode(assessor.getCompanyCode());
        }
    	return assessorForm;
    }
    
    public AssessorInfo findAssessorInfo(String name) {
    	Assessor assessor = this.findAccount(name);
        if (assessor == null) {
            return null;
        }
        return new AssessorInfo(assessor.getAssessorID(), assessor.getUserName(), assessor.getEncrytedPassword(), assessor.isActive());
    }
 
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(AssessorForm assessorForm) {
 
        Session session = this.sessionFactory.getCurrentSession();
        String name = assessorForm.getName().toLowerCase();
 
        Assessor assessor = null;
 
        boolean isNew = false;
        if (name != null) {
        	assessor = this.findAccount(name);
        }
        if (assessor == null) {
            isNew = true;
            assessor = new Assessor();
            //assessor.setCreatedDate(new Date());
        }
        assessor.setUserName(name);
 
        if (isNew) {
            session.persist(assessor);
        }
        session.flush();
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Assessor createAssessor(AssessorForm form) {
    	
    	Session session = this.sessionFactory.getCurrentSession();
    	Assessor assessor = null;
        String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
        int maxID = this.getMaxAccountID() + 1;
        assessor = new Assessor();
        assessor.setAssessorID(maxID);
        assessor.setUserName(form.getName().toLowerCase());
        assessor.setFirstName(form.getFirstName());
        assessor.setLastName(form.getLastName());
        assessor.setEmailAddress(form.getEmailAddress());
        assessor.setEncrytedPassword(encrytedPassword);
        if(form.getAddress().equals("")) 
        	assessor.setAddress(null);
        else
        	assessor.setAddress(form.getAddress());

        assessor.setActive(form.isActive());
        assessor.setLocked(form.isLocked());
        assessor.setPhone(form.getPhone());
        if(form.getCompanyCode().equals("")) 
        	assessor.setCompanyCode(null);
        else
        	assessor.setCompanyCode(form.getCompanyCode());
        assessor.setUpdatedDate(new Date());
        session.persist(assessor);
        session.flush();
        return assessor;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Assessor saveAssessor(AssessorForm form) {
    	
    	Session session = this.sessionFactory.getCurrentSession();
    	Assessor assessor = null;
        //String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
        assessor = this.findAccountByID(form.getAssessorId());
        if(assessor != null) {
	        assessor.setAssessorID(form.getAssessorId());
	        assessor.setUserName(form.getName().toLowerCase());
	        assessor.setFirstName(form.getFirstName());
	        assessor.setLastName(form.getLastName());
	        assessor.setEmailAddress(form.getEmailAddress());
	        //assessor.setEncrytedPassword(encrytedPassword);

	        if(form.getAddress().equals("")) 
	        	assessor.setAddress(null);
	        else
	        	assessor.setAddress(form.getAddress());
	        	//assessor.setAddress("nguyễn vẳn bễ");
	        System.out.println(form.getAddress());	 
	        System.out.println(System.getProperty("file.encoding"));
	        assessor.setActive(form.isActive());
	        assessor.setLocked(form.isLocked());
	        assessor.setPhone(form.getPhone());
	        if(form.getCompanyCode().equals("")) 
	        	assessor.setCompanyCode(null);
	        else
	        	assessor.setCompanyCode(form.getCompanyCode());
	        assessor.setUpdatedDate(new Date());
	        session.persist(assessor);
	        session.flush();
        }
        return assessor;
    }
    
    /*private String encodeUTF8(String input) {    	
    	String value = "";
    	try {
    		ByteBuffer byteBuff = StandardCharsets.UTF_8.encode(input);
    		value = new String(byteBuff, StandardCharsets.UTF_8);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	return value;
    }*/
 
    public PaginationResult<AssessorInfo> queryAssessor(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + AssessorInfo.class.getName() //
                + "(a.assessorID, a.userName, a.encrytedPassword, a.active, a.firstName, a.lastName,"
                + " a.emailAddress, a.address, a.phone, a.companyCode, a.locked, r.roleName) " + " from "//
                + Assessor.class.getName() + " a "
        		+ " left join " + RoleAssessor.class.getName() + " ra on ra.assessorID = a.assessorID "
        		+ " left join " + Roles.class.getName() + " r on r.roleID = ra.roleID ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(a.userName) like :likeName ";
        }
        //sql += " order by m.createdDate desc ";
        //        
        sql += " group by a.assessorID ";
        
        Session session = this.sessionFactory.getCurrentSession();
        Query<AssessorInfo> query = session.createQuery(sql, AssessorInfo.class);
        
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<AssessorInfo>(query, page, maxResult, maxNavigationPage);
    }
    
    public PaginationResult<AssessorInfo> queryAssessor(int page, int maxResult, int maxNavigationPage,
            String likeName, String companyCode) {
        String sql = "Select new " + AssessorInfo.class.getName() //
                + "(a.assessorID, a.userName, a.encrytedPassword, a.active, a.firstName, a.lastName,"
                + " a.emailAddress, a.address, a.phone, a.companyCode, a.locked, r.roleName) " + " from "//
                + Assessor.class.getName() + " a "
		        + " left join " + RoleAssessor.class.getName() + " ra on ra.assessorID = a.assessorID "
				+ " left join " + Roles.class.getName() + " r on r.roleID = ra.roleID ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(a.userName) like :likeName ";
            if (companyCode != null && companyCode.length() > 0) {
        		sql += " and a.companyCode = :companyCode ";
        	}
        } else {
        	if (companyCode != null && companyCode.length() > 0) {
        		sql += " Where a.companyCode = :companyCode ";
        	}
        }
        //sql += " order by m.createdDate desc ";
        // 
        sql += " group by a.assessorID ";
        
        Session session = this.sessionFactory.getCurrentSession();
        Query<AssessorInfo> query = session.createQuery(sql, AssessorInfo.class);
 
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        if (companyCode != null && companyCode.length() > 0) {
        	query.setParameter("companyCode", companyCode);
    	}
        return new PaginationResult<AssessorInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<AssessorInfo> queryAssessor(int page, int maxResult, int maxNavigationPage) {
        return queryAssessor(page, maxResult, maxNavigationPage, null);
    }
 
	public Assessor findByUserNameAndActiveAndLocked(String userName, boolean active, boolean locked) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Assessor.class.getName() + " assessor " //
	                + " Where assessor.userName = :userName "
	                + " and assessor.active = :active "
	                + " and assessor.locked = :locked";
	        Query<Assessor> query = session.createQuery(sql, Assessor.class);
	        query.setParameter("userName", userName);
	        query.setParameter("active", active);
	        query.setParameter("locked", locked);
	        return query.getSingleResult();
    	} catch (NoResultException e) {
    		//log.error(e.getMessage());
    		return null;
    	}
    }
    
    public void update(Assessor assessor) {
    	Session session = this.sessionFactory.getCurrentSession();
    	session.update(assessor);
        session.flush();
    }
}