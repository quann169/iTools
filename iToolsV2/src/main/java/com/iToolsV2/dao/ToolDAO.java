package com.iToolsV2.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.Tools;
import com.iToolsV2.entity.ToolsMachine;
import com.iToolsV2.form.ToolForm;
import com.iToolsV2.model.ToolInfo;
import com.iToolsV2.pagination.PaginationResult;

@Transactional
@Repository
public class ToolDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Tools findTool(String toolCode) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "from " + Tools.class.getName() + " tool " //
					+ " Where tool.toolCode = :toolCode ";
			Query<Tools> query = session.createQuery(sql, Tools.class);
			query.setParameter("toolCode", toolCode);
			return query.getSingleResult();
		} catch (NoResultException e) {
    		return null;
    	}
	}

	public ToolInfo findToolInfo(String name) {
		Tools tool = this.findTool(name);
		if (tool == null) {
			return null;
		}
		return new ToolInfo(tool.getToolCode(), tool.getDescription(), tool.isActive());
	}
    
    public Tools findToolById(int toolID) {
    	Tools tool = null;
        try {
        	Session session = this.sessionFactory.getCurrentSession();
        	 String sql = "from " + Tools.class.getName() + " tool " //
                     + " Where tool.toolID = :toolID ";
             Query<Tools> query = session.createQuery(sql, Tools.class);
             query.setParameter("toolID", toolID);
             tool = query.getSingleResult();
        } catch (Exception e) {
           e.printStackTrace();
        } 
        return tool;
    }
    
    public ToolForm findToolFormByID(int toolID) {
    	Tools tool = this.findToolById(toolID);
    	ToolForm toolForm = new ToolForm();
    	if (tool != null) {
    		toolForm.setToolID(toolID);
    		toolForm.setToolCode(tool.getToolCode());
    		//toolForm.setBarcode(tool.getBarcode());
    		toolForm.setModel(tool.getModel());
    		toolForm.setDescription(tool.getDescription());
    		toolForm.setCreatedDate(tool.getCreatedDate());
    		toolForm.setUpdatedDate(tool.getUpdatedDate());
    		toolForm.setActive(tool.isActive());
        }
    	return toolForm;
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void save(ToolForm toolForm) {

		Session session = this.sessionFactory.getCurrentSession();
		String toolCode = toolForm.getToolCode();

		Tools tool = null;

		boolean isNew = false;
		if (toolCode != null) {
			tool = this.findTool(toolCode);
		}
		if (tool == null) {
			isNew = true;
			tool = new Tools();
			tool.setCreatedDate(new Date());
		}
		tool.setToolCode(toolCode);

		if (isNew) {
			session.persist(tool);
		}
		session.flush();
	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Tools creatTool(ToolForm form) {
    	
    	Session session = this.sessionFactory.getCurrentSession();
    	Tools tool = null;
    	tool = new Tools();
    	tool.setToolCode(form.getToolCode());
        if(form.getModel().equals("")) 
        	tool.setModel(null);
        else
        	tool.setModel(form.getModel());
        /*if(form.getBarcode().equals("")) 
        	tool.setBarcode(null);
        else
        	tool.setBarcode(form.getBarcode());*/
        if(form.getDescription().equals("")) 
        	tool.setDescription(null);
        else
        	tool.setDescription(form.getDescription());
        tool.setActive(form.isActive());
        tool.setCreatedDate(new Date());
        session.persist(tool);
        session.flush();
        return tool;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Tools saveTool(ToolForm form) {
    	
    	Session session = this.sessionFactory.getCurrentSession();
    	Tools tool = null;
    	tool = this.findToolById(form.getToolID());
        if(tool != null) {
        	tool.setToolID(form.getToolID());
        	if(form.getModel().equals("")) 
            	tool.setModel(null);
            else
            	tool.setModel(form.getModel());
            if(!form.getToolCode().equals("")) 
            	tool.setToolCode(form.getToolCode());
            if(form.getDescription().equals("")) 
            	tool.setDescription(null);
            else
            	tool.setDescription(form.getDescription());
            tool.setActive(form.isActive());
	        tool.setUpdatedDate(new Date());
	        session.persist(tool);
	        session.flush();
        }
        return tool;
    }

	public PaginationResult<ToolInfo> queryTool(int page, int maxResult, int maxNavigationPage, String likeName) {
		String sql = "Select new " + ToolInfo.class.getName() //
				+ "(t.toolID, t.toolCode, t.model, t.barcode, t.description, t.active, t.createdDate, t.updatedDate) " + " from "//
				+ Tools.class.getName() + " t ";
		if (likeName != null && likeName.length() > 0) {
			sql += " Where lower(t.toolCode) like :likeName ";
		}
		// sql += " order by m.createdDate desc ";
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

	public List<Tools> findAll() {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Tools.class.getName() + " tool ";
	        Query<Tools> query = session.createQuery(sql, Tools.class);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
	
	public List<Tools> findToolsByMachineCode(String machineCode) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "select tool from " + Tools.class.getName() + " tool " +
	        			" left join " + ToolsMachine.class.getName() + " tm on tool.toolCode = tm.toolCode " +
	        			" where tm.machineCode = :machineCode";
	        
	        Query<Tools> query = session.createQuery(sql, Tools.class);
	        query.setParameter("machineCode", machineCode);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
}