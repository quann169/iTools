package com.iToolsV2.dao;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.Assessor;
import com.iToolsV2.entity.RoleAssessor;
import com.iToolsV2.entity.Roles;
import com.iToolsV2.form.RolesAssessorForm;
 
@Transactional
@Repository
public class RolesAssessorDAO {
 
    @Autowired
    //private EntityManager entityManager;
    private SessionFactory sessionFactory;
    
    @Autowired
    private AssessorDAO assessorDAO;
    
    @Autowired
    private RolesDAO rolesDao;
 
	public List<Roles> getRoles(int assessorID) {
		try {
	    	String sql = "Select ra.roles from " + RoleAssessor.class.getName() + " ra " //
	                + " Where ra.assessor.assessorID = :assessorID ";
	        Session session = this.sessionFactory.getCurrentSession();
	        Query<Roles> query = session.createQuery(sql, Roles.class);
	        query.setParameter("assessorID", assessorID);
	        return query.getResultList();
		} catch (NoResultException e) {
            return null;
        }
    }
	
	public List<Roles> findAllRoles() {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Roles.class.getName() + " roles " //	
	        		+ " Where roles.isRole = 1 ";
	        Query<Roles> query = session.createQuery(sql, Roles.class);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
	
	public RoleAssessor findRoleAssessor(int roleID, int assessorID) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "from " + RoleAssessor.class.getName() + " ra " //
					+ " Where ra.roleID = :roleID and ra.assessorID = :assessorID";
			Query<RoleAssessor> query = session.createQuery(sql, RoleAssessor.class);
			query.setParameter("roleID", roleID);
			query.setParameter("assessorID", assessorID);
			List<RoleAssessor> lstRoleAssessor = query.getResultList();
	        if (lstRoleAssessor != null && lstRoleAssessor.size() > 0)
	        	return lstRoleAssessor.get(0);
	        else
	        	return null;
		} catch (NoResultException e) {
    		return null;
    	}
	}
	
    @Transactional(rollbackFor = Exception.class)
    public List<String> saveRoleAssessor(RolesAssessorForm form) {
    	List<String> returnList = new ArrayList<String>();
    	Session session = this.sessionFactory.getCurrentSession();
    	RoleAssessor roleAssessor = null;
    	Assessor assessor = null;
    	assessor = assessorDAO.findAccount(form.getUserName());    	
    	String rolesCode = form.getRolesID();
    	String[] lstRoleCode = null;
    	if(rolesCode != null) {
    		if(rolesCode.contains(",")) {
    			lstRoleCode = rolesCode.split(",");
    		} else {
    			lstRoleCode = new String[1];
    			lstRoleCode[0] = rolesCode;
    		}
    	}
    	if(lstRoleCode != null && assessor != null) {
	    	for(int i=0; i< lstRoleCode.length;i++) {
	    		roleAssessor = findRoleAssessor(Integer.parseInt(lstRoleCode[i]), assessor.getAssessorID());
	    		if(roleAssessor == null) {
	    			roleAssessor = new RoleAssessor();
	    			roleAssessor.setAssessorID(assessor.getAssessorID());
	    			roleAssessor.setRoleID(Integer.parseInt(lstRoleCode[i]));
	    			roleAssessor.setCreatedDate(new Date());
	    			roleAssessor.setActive(true);
	    			session.persist(roleAssessor);
	    	        session.flush();
	    	        returnList.add(rolesDao.getRoleNamesByID(Integer.parseInt(lstRoleCode[i])));
	    		}
	    	}
    	}
    	        
        return returnList;
    }
 
}