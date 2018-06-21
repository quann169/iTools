package com.iToolsV2.dao;
 
import java.util.Date;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
 
    public PaginationResult<MachineInfo> queryMachine(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + MachineInfo.class.getName() //
                + "(m.machineCode, m.machineName, m.active) " + " from "//
                + Machine.class.getName() + " m ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(m.machineName) like :likeName ";
        }
        sql += " order by m.createdDate desc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<MachineInfo> query = session.createQuery(sql, MachineInfo.class);
 
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<MachineInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<MachineInfo> queryMachine(int page, int maxResult, int maxNavigationPage) {
        return queryMachine(page, maxResult, maxNavigationPage, null);
    }
 
}