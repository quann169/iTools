package com.iToolsV2.dao;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.Assessor;
 
@Transactional
@Repository
public class AssessorDAO {
 
    @Autowired
	private SessionFactory sessionFactory;
 
    public Assessor findAccount(String userName) {
    	Session session = this.sessionFactory.getCurrentSession();
        String sql = "from " + Assessor.class.getName() + " assessor " //
                + " Where assessor.userName = :userName ";
        Query<Assessor> query = session.createQuery(sql, Assessor.class);
        query.setParameter("userName", userName);
        return query.getSingleResult();
    }
 
}