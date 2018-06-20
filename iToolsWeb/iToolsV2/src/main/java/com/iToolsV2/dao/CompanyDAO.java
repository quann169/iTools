package com.iToolsV2.dao;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.Company;
import com.iToolsV2.form.CompanyForm;
import com.iToolsV2.model.CompanyInfo;
import com.iToolsV2.pagination.PaginationResult;
 
@Transactional
@Repository
public class CompanyDAO {
 
    @Autowired
	private SessionFactory sessionFactory;
 
    public Company findCompany(String companyName) {
    	Session session = this.sessionFactory.getCurrentSession();
        String sql = "from " + Company.class.getName() + " company " //
                + " Where company.companyName = :companyName ";
        Query<Company> query = session.createQuery(sql, Company.class);
        query.setParameter("companyName", companyName);
        return query.getSingleResult();
    }
    
    public CompanyInfo findCompanyInfo(String name) {
    	Company company = this.findCompany(name);
        if (company == null) {
            return null;
        }
        return new CompanyInfo(company.getCompanyID(), company.getCompanyName(), company.getCompanyCode(), company.getLocation(), company.getAddress());
    }
 
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(CompanyForm companyForm) {
 
        Session session = this.sessionFactory.getCurrentSession();
        String name = companyForm.getCompanyName();
 
        Company company = null;
 
        boolean isNew = false;
        if (name != null) {
        	company = this.findCompany(name);
        }
        if (company == null) {
            isNew = true;
            company = new Company();
            //assessor.setCreatedDate(new Date());
        }
        company.setCompanyName(name);
 
        if (isNew) {
            session.persist(company);
        }
        session.flush();
    }
 
    public PaginationResult<CompanyInfo> queryCompany(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + CompanyInfo.class.getName() //
                + "(c.companyID, c.companyName, c.companyCode, c.address, c.location) " + " from "//
                + Company.class.getName() + " c ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(c.companyName) like :likeName ";
        }
        //sql += " order by m.createdDate desc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<CompanyInfo> query = session.createQuery(sql, CompanyInfo.class);
 
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<CompanyInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<CompanyInfo> queryCompany(int page, int maxResult, int maxNavigationPage) {
        return queryCompany(page, maxResult, maxNavigationPage, null);
    }
 
}