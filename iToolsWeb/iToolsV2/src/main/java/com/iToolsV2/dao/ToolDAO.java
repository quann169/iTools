package com.iToolsV2.dao;
 
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.Company;
import com.iToolsV2.entity.Tools;
import com.iToolsV2.form.CompanyForm;
import com.iToolsV2.form.ToolForm;
import com.iToolsV2.model.CompanyInfo;
import com.iToolsV2.model.ToolInfo;
import com.iToolsV2.pagination.PaginationResult;
 
@Transactional
@Repository
public class ToolDAO {
 
    @Autowired
	private SessionFactory sessionFactory;
 
    public Tools findTool(String toolCode) {
    	Session session = this.sessionFactory.getCurrentSession();
        String sql = "from " + Tools.class.getName() + " tool " //
                + " Where tool.toolCode = :toolCode ";
        Query<Tools> query = session.createQuery(sql, Tools.class);
        query.setParameter("toolCode", toolCode);
        return query.getSingleResult();
    }
    
    public ToolInfo findToolInfo(String name) {
    	Tools tool = this.findTool(name);
        if (tool == null) {
            return null;
        }
        return new ToolInfo(tool.getToolCode(), tool.getDescription(), tool.isActive());
    }
 
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ToolForm toolForm) {
 
        Session session = this.sessionFactory.getCurrentSession();
        String name = toolForm.getToolCode();
 
        Tools tool = null;
 
        boolean isNew = false;
        if (name != null) {
        	tool = this.findTool(name);
        }
        if (tool == null) {
            isNew = true;
            tool = new Tools();
            tool.setCreatedDate(new Date());
        }
        tool.setToolCode(name);
 
        if (isNew) {
            session.persist(tool);
        }
        session.flush();
    }
 
    public PaginationResult<ToolInfo> queryTool(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + ToolInfo.class.getName() //
                + "(t.toolCode, t.description, t.active) " + " from "//
                + Tools.class.getName() + " t ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(t.toolCode) like :likeName ";
        }
        //sql += " order by m.createdDate desc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<ToolInfo> query = session.createQuery(sql, ToolInfo.class);
 
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<ToolInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<ToolInfo> queryTool(int page, int maxResult, int maxNavigationPage) {
        return queryTool(page, maxResult, maxNavigationPage, null);
    }
 
}