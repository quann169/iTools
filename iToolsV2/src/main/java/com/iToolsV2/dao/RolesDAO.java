package com.iToolsV2.dao;
 
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.RoleAssessor;
 
@Transactional
@Repository
public class RolesDAO {
 
    @Autowired
    //private EntityManager entityManager;
    private SessionFactory sessionFactory;
 
	public List<String> getRoleNames(int assessorID) {
    	String sql = "Select ra.roles.roleName from " + RoleAssessor.class.getName() + " ra " //
                + " Where ra.assessor.assessorID = :assessorID ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<String> query = session.createQuery(sql, String.class);
        query.setParameter("assessorID", assessorID);
        return query.getResultList();
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
 
}