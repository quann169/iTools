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

import com.iToolsV2.entity.Machine;
import com.iToolsV2.entity.Tools;
import com.iToolsV2.entity.ToolsMachine;
import com.iToolsV2.form.ToolMachineForm;
import com.iToolsV2.model.ToolInfo;
import com.iToolsV2.pagination.PaginationResult;

@Transactional
@Repository
public class ToolMachineDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private MachineDAO machineDAO;

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
	
	public ToolsMachine findByID(int toolMachineID) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "from " + ToolsMachine.class.getName() + " toolMachine " //
					+ " Where toolMachine.toolMachineID = :toolMachineID";
			Query<ToolsMachine> query = session.createQuery(sql, ToolsMachine.class);
			query.setParameter("toolMachineID", toolMachineID);
			return query.getSingleResult();
		} catch (NoResultException e) {
    		return null;
    	}
	}
	
	public ToolsMachine findByToolCode(String toolCode) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "from " + ToolsMachine.class.getName() + " toolMachine " //
					+ " Where toolMachine.toolCode = :toolCode";
			Query<ToolsMachine> query = session.createQuery(sql, ToolsMachine.class);
			query.setParameter("toolCode", toolCode);
			return query.getSingleResult();
		} catch (NoResultException e) {
    		return null;
    	}
	}
	
	public ToolsMachine findToolMachine(String toolCode, String machineCode) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "from " + ToolsMachine.class.getName() + " toolMachine " //
					+ " Where toolMachine.toolCode = :toolCode and toolMachine.machineCode = :machineCode";
			Query<ToolsMachine> query = session.createQuery(sql, ToolsMachine.class);
			query.setParameter("toolCode", toolCode);
			query.setParameter("machineCode", machineCode);
			List<ToolsMachine> lstToolsMachine = query.getResultList();
	        if (lstToolsMachine != null && lstToolsMachine.size() > 0)
	        	return lstToolsMachine.get(0);
	        else
	        	return null;
		} catch (NoResultException e) {
    		return null;
    	}
	}
	
    @Transactional(rollbackFor = Exception.class)
    public List<String> saveToolMachine(ToolMachineForm form) {
    	List<String> returnList = new ArrayList<String>();
    	Session session = this.sessionFactory.getCurrentSession();
    	ToolsMachine toolMachine = null;
    	Tools tool = null;
    	tool = this.findTool(form.getToolCode());    	
    	String machineCode = form.getMachineCode();
    	String[] lstMachineCode = null;
    	if(machineCode != null) {
    		if(machineCode.contains(",")) {
    			lstMachineCode = machineCode.split(",");
    		} else {
    			lstMachineCode = new String[1];
    			lstMachineCode[0] = machineCode;
    		}
    	}
    	if(lstMachineCode != null && tool != null) {
	    	for(int i=0; i< lstMachineCode.length;i++) {
	    		toolMachine = findToolMachine(tool.getToolCode(), lstMachineCode[i]);
	    		if(toolMachine == null) {
	    			toolMachine = new ToolsMachine();
	    			toolMachine.setToolCode(tool.getToolCode());
	    			toolMachine.setMachineCode(lstMachineCode[i]);
	    			toolMachine.setCreatedDate(new Date());
	    			toolMachine.setActive(true);
	    			session.persist(toolMachine);
	    	        session.flush();
	    	        Machine returnMachine = machineDAO.findMachine(lstMachineCode[i]);
	    	        if(returnMachine != null)
	    	        	returnList.add(returnMachine.getMachineName());
	    		}
	    	}
    	}
    	        
        return returnList;
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
}