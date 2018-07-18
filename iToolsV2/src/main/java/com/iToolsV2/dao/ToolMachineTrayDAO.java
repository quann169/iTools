package com.iToolsV2.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iToolsV2.entity.ToolMachineTray;
import com.iToolsV2.entity.ToolsMachine;
import com.iToolsV2.form.TrayForm;

@Transactional
@Repository
public class ToolMachineTrayDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ToolMachineDAO tmDAO;
	
	public ToolMachineTray findTMTByMachineCodeAndTrayIndex(String machineCode, String trayIndex) {
		ToolMachineTray tmt = null;
        try {
        	Session session = this.sessionFactory.getCurrentSession();
        	 String sql = "select tmt from " + ToolMachineTray.class.getName() + " tmt " //
        			 + " left join " + ToolsMachine.class.getName() + " tm on tm.toolMachineID = tmt.toolsMachineID "
                     + " Where tm.machineCode = :machineCode and tmt.trayIndex = :trayIndex";
             Query<ToolMachineTray> query = session.createQuery(sql, ToolMachineTray.class);
             query.setParameter("machineCode", machineCode);
             query.setParameter("trayIndex", trayIndex);
             List<ToolMachineTray> lstTMT = query.getResultList();
             if(lstTMT.size() > 0)
            	 tmt = lstTMT.get(0);
        } catch (Exception e) {
           e.printStackTrace();
        } 
        return tmt;
    }

	@Transactional(rollbackFor = Exception.class)
    public void updateTray(TrayForm form) {    	
    	Session session = this.sessionFactory.getCurrentSession();
    	if(form.getMachineCode() != null) {
	    	if(form.getTray01() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_01");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray01(), form.getMachineCode());
	    		System.out.println(form.getTray01());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_01");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray02() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_02");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray02(), form.getMachineCode());
	    		System.out.println(form.getTray02());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_02");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray03() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_03");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray03(), form.getMachineCode());
	    		System.out.println(form.getTray03());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_03");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray04() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_04");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray04(), form.getMachineCode());
	    		System.out.println(form.getTray04());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_04");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray05() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_05");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray05(), form.getMachineCode());
	    		System.out.println(form.getTray05());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_05");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray06() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_06");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray06(), form.getMachineCode());
	    		System.out.println(form.getTray06());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_06");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray07() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_07");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray07(), form.getMachineCode());
	    		System.out.println(form.getTray07());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_07");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray08() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_08");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray08(), form.getMachineCode());
	    		System.out.println(form.getTray08());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_08");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray09() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_09");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray09(), form.getMachineCode());
	    		System.out.println(form.getTray09());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_09");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray10() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_10");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray10(), form.getMachineCode());
	    		System.out.println(form.getTray10());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_10");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray11() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_11");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray11(), form.getMachineCode());
	    		System.out.println(form.getTray11());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_11");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray12() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_12");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray12(), form.getMachineCode());
	    		System.out.println(form.getTray12());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_12");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray13() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_13");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray13(), form.getMachineCode());
	    		System.out.println(form.getTray13());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_13");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray14() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_14");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray14(), form.getMachineCode());
	    		System.out.println(form.getTray14());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_14");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray15() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_15");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray15(), form.getMachineCode());
	    		System.out.println(form.getTray15());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_15");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray16() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_16");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray16(), form.getMachineCode());
	    		System.out.println(form.getTray16());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_16");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray17() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_17");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray17(), form.getMachineCode());
	    		System.out.println(form.getTray17());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_17");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray18() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_18");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray18(), form.getMachineCode());
	    		System.out.println(form.getTray18());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_18");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray19() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_19");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray19(), form.getMachineCode());
	    		System.out.println(form.getTray19());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_19");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray20() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_20");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray20(), form.getMachineCode());
	    		System.out.println(form.getTray20());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_20");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
			
			if(form.getTray21() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_21");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray21(), form.getMachineCode());
	    		System.out.println(form.getTray21());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_21");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray22() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_22");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray22(), form.getMachineCode());
	    		System.out.println(form.getTray22());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_22");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray23() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_23");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray23(), form.getMachineCode());
	    		System.out.println(form.getTray23());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_23");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray24() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_24");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray24(), form.getMachineCode());
	    		System.out.println(form.getTray24());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_24");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray25() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_25");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray25(), form.getMachineCode());
	    		System.out.println(form.getTray25());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_25");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray26() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_26");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray26(), form.getMachineCode());
	    		System.out.println(form.getTray26());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_26");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray27() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_27");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray27(), form.getMachineCode());
	    		System.out.println(form.getTray27());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_27");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray28() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_28");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray28(), form.getMachineCode());
	    		System.out.println(form.getTray28());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_28");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray29() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_29");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray29(), form.getMachineCode());
	    		System.out.println(form.getTray29());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_29");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray30() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_30");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray30(), form.getMachineCode());
	    		System.out.println(form.getTray30());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_30");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
			
			if(form.getTray31() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_31");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray31(), form.getMachineCode());
	    		System.out.println(form.getTray31());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_31");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray32() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_32");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray32(), form.getMachineCode());
	    		System.out.println(form.getTray32());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_32");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray33() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_33");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray33(), form.getMachineCode());
	    		System.out.println(form.getTray33());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_33");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray34() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_34");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray34(), form.getMachineCode());
	    		System.out.println(form.getTray34());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_34");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray35() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_35");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray35(), form.getMachineCode());
	    		System.out.println(form.getTray35());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_35");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray36() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_36");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray36(), form.getMachineCode());
	    		System.out.println(form.getTray36());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_36");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray37() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_37");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray37(), form.getMachineCode());
	    		System.out.println(form.getTray37());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_37");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray38() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_38");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray38(), form.getMachineCode());
	    		System.out.println(form.getTray38());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_38");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray39() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_39");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray39(), form.getMachineCode());
	    		System.out.println(form.getTray39());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_39");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray40() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_40");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray40(), form.getMachineCode());
	    		System.out.println(form.getTray40());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_40");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
			
			if(form.getTray41() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_41");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray41(), form.getMachineCode());
	    		System.out.println(form.getTray41());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_41");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray42() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_42");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray42(), form.getMachineCode());
	    		System.out.println(form.getTray42());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_42");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray43() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_43");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray43(), form.getMachineCode());
	    		System.out.println(form.getTray43());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_43");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray44() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_44");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray44(), form.getMachineCode());
	    		System.out.println(form.getTray44());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_44");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray45() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_45");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray45(), form.getMachineCode());
	    		System.out.println(form.getTray45());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_45");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray46() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_46");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray46(), form.getMachineCode());
	    		System.out.println(form.getTray46());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_46");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray47() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_47");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray47(), form.getMachineCode());
	    		System.out.println(form.getTray47());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_47");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray48() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_48");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray48(), form.getMachineCode());
	    		System.out.println(form.getTray48());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_48");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray49() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_49");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray49(), form.getMachineCode());
	    		System.out.println(form.getTray49());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_49");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray50() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_50");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray50(), form.getMachineCode());
	    		System.out.println(form.getTray50());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_50");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
			
			if(form.getTray51() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_51");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray51(), form.getMachineCode());
	    		System.out.println(form.getTray51());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_51");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray52() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_52");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray52(), form.getMachineCode());
	    		System.out.println(form.getTray52());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_52");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray53() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_53");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray53(), form.getMachineCode());
	    		System.out.println(form.getTray53());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_53");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray54() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_54");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray54(), form.getMachineCode());
	    		System.out.println(form.getTray54());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_54");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray55() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_55");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray55(), form.getMachineCode());
	    		System.out.println(form.getTray55());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_55");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray56() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_56");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray56(), form.getMachineCode());
	    		System.out.println(form.getTray56());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_56");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray57() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_57");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray57(), form.getMachineCode());
	    		System.out.println(form.getTray57());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_57");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray58() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_58");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray58(), form.getMachineCode());
	    		System.out.println(form.getTray58());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_58");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray59() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_59");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray59(), form.getMachineCode());
	    		System.out.println(form.getTray59());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_59");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
	    	
	    	if(form.getTray60() != null) {
	    		ToolMachineTray tmt = this.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(), "TRAY_60");
	    		ToolsMachine tm = tmDAO.findToolMachine(form.getTray60(), form.getMachineCode());
	    		System.out.println(form.getTray60());
	    		System.out.println(form.getMachineCode());
	    		if(tmt == null) {
	    			tmt = new ToolMachineTray();
	    			if(tm != null) {
		    			tmt.setToolsMachineID(tm.getToolMachineID());
		    			tmt.setActive(true);
		    			tmt.setTrayIndex("TRAY_60");
	    				tmt.setCreatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		} else {
	    			if(tm != null) {
	    				tmt.setToolsMachineID(tm.getToolMachineID());
	    				tmt.setUpdatedDate(new Date());
	    		        session.persist(tmt);
	    		        session.flush();
	    			}
	    		}
	    	}
    	}
    }
}