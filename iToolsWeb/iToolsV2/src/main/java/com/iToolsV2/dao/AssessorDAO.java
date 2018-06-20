package com.iToolsV2.dao;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.Assessor;
import com.iToolsV2.form.AssessorForm;
import com.iToolsV2.model.AssessorInfo;
import com.iToolsV2.pagination.PaginationResult;
 
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
        String name = assessorForm.getName();
 
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
 
    public PaginationResult<AssessorInfo> queryAssessor(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + AssessorInfo.class.getName() //
                + "(a.assessorID, a.userName, a.encrytedPassword, a.active) " + " from "//
                + Assessor.class.getName() + " a ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(a.userName) like :likeName ";
        }
        //sql += " order by m.createdDate desc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<AssessorInfo> query = session.createQuery(sql, AssessorInfo.class);
 
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<AssessorInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<AssessorInfo> queryMachine(int page, int maxResult, int maxNavigationPage) {
        return queryAssessor(page, maxResult, maxNavigationPage, null);
    }
 
}