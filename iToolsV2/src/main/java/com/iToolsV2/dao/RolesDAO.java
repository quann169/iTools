package com.iToolsV2.dao;
 
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.RoleAssessor;
import com.iToolsV2.entity.Roles;
import com.iToolsV2.model.AssessorInfo;
 
@Transactional
@Repository
public class RolesDAO {
 
    @Autowired
    //private EntityManager entityManager;
    private SessionFactory sessionFactory;
 
	public List<String> getRoleNames(int assessorID) {
		try {
    	String sql = "Select r.roleName from " + RoleAssessor.class.getName() + " ra " //
    			+ " left join " + Roles.class.getName() + " r on r.roleID = ra.roleID "
                + " Where ra.assessorID = :assessorID ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<String> query = session.createQuery(sql, String.class);
        query.setParameter("assessorID", assessorID);
        return query.getResultList();
		} catch (NoResultException e) {
    		return null;
    	}
        /*try {
            String sql = "Select ra.roles.roleName from " + RoleAssessor.class.getName() + " ra " //
                    + " Where ra.assessor.assessorID = :assessorID ";

            Query query = this.entityManager.createQuery(sql, String.class);
            query.setParameter("assessorID", assessorID); 

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }*/
    }
	
	public String getRoleNamesByID(int roleID) {
		try {
	    	String sql = "Select r.roleName from " + Roles.class.getName() + " r "
	                + " Where r.roleID = :roleID ";
	        Session session = this.sessionFactory.getCurrentSession();
	        Query<String> query = session.createQuery(sql, String.class);
	        query.setParameter("roleID", roleID);
	        return query.getSingleResult();
		} catch (NoResultException e) {
    		return null;
    	}
    }
	
	public List<Roles> findAllRoles() {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Roles.class.getName() + " roles " //	
	        		+ " Where roles.isRole = 1 and roles.roleName in ('Admin','SubAdmin','Accounting') ";
	        Query<Roles> query = session.createQuery(sql, Roles.class);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
	
	public List<Roles> findAllRoles(int assessorID) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	    	
	    	String sql = "from " + Roles.class.getName() + " roles " //	
	        		+ " Where roles.isRole = 1 and roles.roleName in ('Admin','SubAdmin','Accounting') "
	        		+ " and roles.roleID not in "
	        		+ " ( select ra.roleID from " + RoleAssessor.class.getName() + " ra "
	        		+ " Where ra.assessorID = :assessorID ) ";
	        Query<Roles> query = session.createQuery(sql, Roles.class);
	        query.setParameter("assessorID", assessorID);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
	
	public List<Roles> findRolesNotAdmin() {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Roles.class.getName() + " roles " //	
	        		+ " Where roles.isRole = 1 and roles.roleName = 'Emp'";
	        Query<Roles> query = session.createQuery(sql, Roles.class);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
	
	public List<Roles> findRolesNotAdmin(int assessorID) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	    	
	    	String sql = "from " + Roles.class.getName() + " roles " //	
	        		+ " Where roles.isRole = 1 and roles.roleName = 'Emp' "
	        		+ " and roles.roleID not in "
	        		+ " ( select ra.roleID from " + RoleAssessor.class.getName() + " ra "
	        		+ " Where ra.assessorID = :assessorID ) ";
	        Query<Roles> query = session.createQuery(sql, Roles.class);
	        query.setParameter("assessorID", assessorID);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
	
	public List<Roles> findCurrentRoles(int assessorID) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	    	
	    	String sql = "from " + Roles.class.getName() + " roles " //	
	        		+ " Where roles.isRole = 1 and roles.roleName in ('Admin','SubAdmin','Accounting') "
	        		+ " and roles.roleID in "
	        		+ " ( select ra.roleID from " + RoleAssessor.class.getName() + " ra "
	        		+ " Where ra.assessorID = :assessorID ) ";
	        Query<Roles> query = session.createQuery(sql, Roles.class);
	        query.setParameter("assessorID", assessorID);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
	
	public boolean removeCurrentRoles(int assessorID) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	    	boolean returnDelete = false;
	    	String sql = " from " + RoleAssessor.class.getName() + " ra "
	        		+ " Where ra.assessorID = :assessorID ";
	        Query<RoleAssessor> query = session.createQuery(sql, RoleAssessor.class);
	        query.setParameter("assessorID", assessorID);
	        List<RoleAssessor> rolesAssessor = null;
	        try {
	        	rolesAssessor = query.getResultList();
	        } catch (NoResultException e) {
	        	rolesAssessor = null;
	    	}	       
	        if(rolesAssessor != null) {
		        for (RoleAssessor roleAssessor : rolesAssessor) {
		        	try {
		        		session.delete(roleAssessor);
		        	} catch (Exception e) {
		        		returnDelete = false;
		        	}
		        	returnDelete = true;
		        }
	        }
	        return returnDelete;
    	} catch (NoResultException e) {
    		return false;
    	}
    }
 
}