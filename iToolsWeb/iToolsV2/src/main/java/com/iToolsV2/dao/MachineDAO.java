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

import com.iToolsV2.entity.CompanyMachine;
import com.iToolsV2.entity.Machine;
import com.iToolsV2.form.MachineForm;
import com.iToolsV2.model.MachineInfo;
import com.iToolsV2.pagination.PaginationResult;
 
@Transactional
@Repository
public class MachineDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public Machine findMachine(String machineCode) {
        try {
            String sql = "Select e from " + Machine.class.getName() + " e Where e.machineCode =:machineCode ";
 
            Session session = this.sessionFactory.getCurrentSession();
            Query<Machine> query = session.createQuery(sql, Machine.class);
            query.setParameter("machineCode", machineCode);
            return (Machine) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Machine findMachineById(int machineID) {
        try {
            String sql = "Select e from " + Machine.class.getName() + " e Where e.machineID =:machineID ";
 
            Session session = this.sessionFactory.getCurrentSession();
            Query<Machine> query = session.createQuery(sql, Machine.class);
            query.setParameter("machineID", machineID);
            return (Machine) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Machine findMachineByName(String machineName) {
        try {
            String sql = "Select e from " + Machine.class.getName() + " e Where e.machineName =:machineName ";
 
            Session session = this.sessionFactory.getCurrentSession();
            Query<Machine> query = session.createQuery(sql, Machine.class);
            query.setParameter("machineName", machineName);
            return (Machine) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public MachineForm findMachineFormByID(int machineID) {
    	Machine machine = this.findMachineById(machineID);
    	MachineForm machineForm = new MachineForm();
    	if (machine != null) {
    		machineForm.setMachineID(machineID);
    		machineForm.setMachineCode(machine.getMachineCode());
    		machineForm.setMachineName(machine.getMachineName());
    		machineForm.setModel(machine.getModel());
    		machineForm.setLocation(machine.getLocation());
    		machineForm.setDescription(machine.getDescription());
    		machineForm.setCreatedDate(machine.getCreatedDate());
    		machineForm.setUpdatedDate(machine.getUpdatedDate());
    		machineForm.setActive(machine.isActive());
    		machineForm.setCompanyCode(this.findCompanyCodeByMachineCode(machine.getMachineCode()));
        }
    	return machineForm;
    }
    
    public MachineForm findMachineFormByCode(String machineCode) {
    	Machine machine = this.findMachine(machineCode);
    	MachineForm machineForm = new MachineForm();
    	if (machine != null) {
    		machineForm.setMachineID(machine.getMachineID());
    		machineForm.setMachineCode(machineCode);
    		machineForm.setMachineName(machine.getMachineName());
    		machineForm.setModel(machine.getModel());
    		machineForm.setLocation(machine.getLocation());
    		machineForm.setDescription(machine.getDescription());
    		machineForm.setCreatedDate(machine.getCreatedDate());
    		machineForm.setUpdatedDate(machine.getUpdatedDate());
    		machineForm.setActive(machine.isActive());
    		machineForm.setCompanyCode(this.findCompanyCodeByMachineCode(machineCode));
        }
    	return machineForm;
    }
    
    public String findCompanyCodeByMachineCode(String machineCode) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + CompanyMachine.class.getName() + " cm " //
	                + " Where cm.machineCode = :machineCode ";
	        Query<CompanyMachine> query = session.createQuery(sql, CompanyMachine.class);
	        query.setParameter("machineCode", machineCode);
	        List<CompanyMachine> lstCM = query.getResultList(); 
	        String returnString = "";
	        CompanyMachine thisCM = null;
	        if(lstCM != null && lstCM.size() > 0) {
	        	thisCM = lstCM.get(0);
	        	if(thisCM != null) 
	        		returnString = thisCM.getCompanyCode();
	        }
	        return returnString;
    	} catch (NoResultException e) {
    		return "";
    	}
    }
    
    public CompanyMachine findCompanyByMachineCode(String machineCode) {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + CompanyMachine.class.getName() + " cm " //
	                + " Where cm.machineCode = :machineCode ";
	        Query<CompanyMachine> query = session.createQuery(sql, CompanyMachine.class);
	        query.setParameter("machineCode", machineCode);
	        List<CompanyMachine> lstCM = query.getResultList(); 
	        if(lstCM.size() > 0) {
	        	return lstCM.get(0);
	        } else 
	        	return null;
    	} catch (NoResultException e) {
    		return null;
    	}
    }
 
    public MachineInfo findMachineInfo(String code) {
        Machine machine = this.findMachine(code);
        if (machine == null) {
            return null;
        }
        return new MachineInfo(machine.getMachineCode(), machine.getMachineName(), machine.isActive());
    }
 
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(MachineForm machineForm) {
 
        Session session = this.sessionFactory.getCurrentSession();
        String machineCode = machineForm.getMachineCode();
 
        Machine machine = null;
 
        boolean isNew = false;
        if (machineCode != null) {
        	machine = this.findMachine(machineCode);
        }
        if (machine == null) {
            isNew = true;
            machine = new Machine();
            machine.setCreatedDate(new Date());
        }
        machine.setMachineCode(machineCode);
        machine.setMachineName(machineForm.getMachineName());
 
        if (isNew) {
            session.persist(machine);
        }
        session.flush();
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Machine createMachine(MachineForm form) {
    	
    	Session session = this.sessionFactory.getCurrentSession();
    	Machine machine = null;
    	machine = new Machine();
    	//machine.setMachineID(form.getMachineID());
    	machine.setMachineCode(form.getMachineCode());
    	machine.setMachineName(form.getMachineName());
    	if(form.getModel().equals("")) 
    		machine.setModel(null);
        else
        	machine.setModel(form.getModel());
        if(form.getLocation().equals("")) 
        	machine.setLocation(null);
        else
        	machine.setLocation(form.getLocation());
        if(form.getDescription().equals("")) 
        	machine.setDescription(null);
        else
        	machine.setDescription(form.getDescription());
        machine.setActive(form.isActive());
        machine.setCreatedDate(new Date());
        session.persist(machine);
        session.flush();
        return machine;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Machine saveMachine(MachineForm form) {
    	
    	Session session = this.sessionFactory.getCurrentSession();
    	Machine machine = null;
    	machine = this.findMachineById(form.getMachineID());
        if(machine != null) {
        	machine.setMachineID(form.getMachineID());
        	machine.setMachineCode(form.getMachineCode());
        	machine.setMachineName(form.getMachineName());
        	if(form.getModel().equals("")) 
        		machine.setModel(null);
            else
            	machine.setModel(form.getModel());
            if(form.getLocation().equals("")) 
            	machine.setLocation(null);
            else
            	machine.setLocation(form.getLocation());
            if(form.getDescription().equals("")) 
            	machine.setDescription(null);
            else
            	machine.setDescription(form.getDescription());
            machine.setActive(form.isActive());
            machine.setUpdatedDate(new Date());	        
	        
	        CompanyMachine cm = this.findCompanyByMachineCode(form.getMachineCode());
	        if(cm != null) {
		        cm.setCompanyCode(form.getCompanyCode());
		        cm.setUpdatedDate(new Date());
	        } else {
	        	cm = new CompanyMachine();
	        	cm.setCompanyCode(form.getCompanyCode());
	        	cm.setMachineCode(form.getMachineCode());
		        cm.setCreatedDate(new Date());
	        }
	        
	        session.persist(machine);
	        session.persist(cm);
	        session.flush();
        }
        
        return machine;
    }
    
    public List<Machine> findAll() {
    	try {
	    	Session session = this.sessionFactory.getCurrentSession();
	        String sql = "from " + Machine.class.getName() + " machine ";
	        Query<Machine> query = session.createQuery(sql, Machine.class);
	        return query.getResultList();
    	} catch (NoResultException e) {
    		return null;
    	}
    }
 
    public PaginationResult<MachineInfo> queryMachine(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + MachineInfo.class.getName() //
                + "(m.machineID, m.machineCode, m.machineName, m.active) " + " from "//
                + Machine.class.getName() + " m ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(m.machineName) like :likeName ";
        }
        sql += " order by m.machineID asc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<MachineInfo> query = session.createQuery(sql, MachineInfo.class);
 
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<MachineInfo>(query, page, maxResult, maxNavigationPage);
    }
    
    public PaginationResult<MachineInfo> queryMachine(int page, int maxResult, int maxNavigationPage,
            String likeName, String companyCode) {
        String sql = "Select new " + MachineInfo.class.getName() //
                + "(m.machineID, m.machineCode, m.machineName, m.active) " + " from "//
                + Machine.class.getName() + " m "
                + " left join " + CompanyMachine.class.getName() + " cm on cm.machineCode = m.machineCode ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(m.machineName) like :likeName ";
            if (companyCode != null && companyCode.length() > 0) {
        		sql += " and cm.companyCode = :companyCode ";
        	}
        } else {
        	if (companyCode != null && companyCode.length() > 0) {
        		sql += " Where cm.companyCode = :companyCode ";
        	}
        }
        sql += " order by m.machineID asc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<MachineInfo> query = session.createQuery(sql, MachineInfo.class);
 
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        if (companyCode != null && companyCode.length() > 0) {
        	query.setParameter("companyCode", companyCode);
    	}
        return new PaginationResult<MachineInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<MachineInfo> queryMachine(int page, int maxResult, int maxNavigationPage) {
        return queryMachine(page, maxResult, maxNavigationPage, null);
    }
 
}