package com.iToolsV2.dao;
 
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.Company;
import com.iToolsV2.entity.CompanyMachine;
import com.iToolsV2.form.CompanyForm;
import com.iToolsV2.model.CompanyInfo;
import com.iToolsV2.pagination.PaginationResult;
 
@Transactional
@Repository
public class CompanyDAO {
 
    @Autowired
	private SessionFactory sessionFactory;
 
    public Company findCompany(String companyName) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Company.class.getName() + " company " //
	                + " Where company.companyName = :companyName ";
	        Query<Company> query = session.createQuery(sql, Company.class);
	        query.setParameter("companyName", companyName);
	        return query.getSingleResult();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
    
    public CompanyInfo findCompanyInfo(String name) {
    	Company company = this.findCompany(name);
        if (company == null) {
            return null;
        }
        return new CompanyInfo(company.getCompanyID(), company.getCompanyName(), company.getCompanyCode(), company.getLocation(), company.getAddress());
    }
    
    public List<Company> findAllCompany() {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Company.class.getName() + " company ";
	        Query<Company> query = session.createQuery(sql, Company.class);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
    
    public Company findCompanyByCode(String companyCode) {
    	Company company = null;
        try {
        	Session session = this.sessionFactory.getCurrentSession();
        	 String sql = "from " + Company.class.getName() + " company " //
                     + " Where company.companyCode = :companyCode ";
             Query<Company> query = session.createQuery(sql, Company.class);
             query.setParameter("companyCode", companyCode);
             company = query.getSingleResult();
        } catch (Exception e) {
           e.printStackTrace();
        } 
        return company;
    }
    
    public Company findCompanyById(int companyId) {
    	Company company = null;
        try {
        	Session session = this.sessionFactory.getCurrentSession();
        	 String sql = "from " + Company.class.getName() + " company " //
                     + " Where company.companyID = :companyId ";
             Query<Company> query = session.createQuery(sql, Company.class);
             query.setParameter("companyId", companyId);
             company = query.getSingleResult();
        } catch (Exception e) {
           e.printStackTrace();
        } 
        return company;
    }
    
    public CompanyForm findCompanyFormByID(int companyId) {
    	Company company = this.findCompanyById(companyId);
    	CompanyForm companyForm = new CompanyForm();
    	if (company != null) {
    		companyForm.setCompanyId(companyId);
    		companyForm.setCompanyCode(company.getCompanyCode());
    		companyForm.setCompanyName(company.getCompanyName());
    		companyForm.setAddress(company.getAddress());
    		companyForm.setLocation(company.getLocation());
        }
    	return companyForm;
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
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Company createCompany(CompanyForm form) {
    	
    	Session session = this.sessionFactory.getCurrentSession();
    	Company company = null;
    	company = new Company();
    	company.setCompanyCode(form.getCompanyCode());
    	company.setCompanyName(form.getCompanyName());
        if(form.getAddress().equals("")) 
        	company.setAddress(null);
        else
        	company.setAddress(form.getAddress());
        if(form.getLocation().equals("")) 
        	company.setLocation(null);
        else
        	company.setLocation(form.getLocation());
        session.persist(company);
        session.flush();
        return company;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Company saveCompany(CompanyForm form) {
    	
    	Session session = this.sessionFactory.getCurrentSession();
    	Company company = null;
    	company = this.findCompanyById(form.getCompanyId());
        if(company != null) {
        	company.setCompanyID(form.getCompanyId());
	        company.setCompanyCode(form.getCompanyCode());
	    	company.setCompanyName(form.getCompanyName());
	        if(form.getAddress().equals("")) 
	        	company.setAddress(null);
	        else
	        	company.setAddress(form.getAddress());
	        if(form.getLocation().equals("")) 
	        	company.setLocation(null);
	        else
	        	company.setLocation(form.getLocation());
	        session.persist(company);
	        session.flush();
        }
        return company;
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