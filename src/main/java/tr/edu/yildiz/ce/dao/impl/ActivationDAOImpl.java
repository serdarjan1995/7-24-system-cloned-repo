package tr.edu.yildiz.ce.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ActivationDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Activation;
import tr.edu.yildiz.ce.entity.Ban;
import tr.edu.yildiz.ce.entity.User;
import tr.edu.yildiz.ce.model.ActivationInfo;
import tr.edu.yildiz.ce.model.BanInfo;

public class ActivationDAOImpl implements ActivationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Activation findActivation(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Activation.class);
        crit.add(Restrictions.eq("id", id));
        return (Activation) crit.uniqueResult();
	}
	
	@Override
	public Activation findActivationWithCode(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Activation.class);
        crit.add(Restrictions.eq("code", code));
        return (Activation) crit.uniqueResult();
	}
	
	@Override
	public Activation findActivationWithUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Activation.class);
        crit.add(Restrictions.eq("userId", id));
        return (Activation) crit.uniqueResult();
	}
	
	@Override
	public void saveActivation(Activation act) {
		Integer id = act.getId();
		Activation actv = null;
		if( id!=null ){
			actv = this.findActivation(id);
		}
		boolean isNew = false;
		if( actv == null ){
			isNew = true;
			actv = new Activation();
		}
		actv.setId(act.getId());
	    actv.setUsername(act.getUsername());
	    actv.setCode(act.getCode());
	    actv.setRecordDate(act.getRecordDate());
	    if(isNew){
	    	Session session=this.sessionFactory.getCurrentSession();
	    	session.persist(actv);
	    }
		
	}

	@Override
	public void deleteActivation(Integer id) {
		Activation act = this.findActivation(id);
		if(act!=null){
			this.sessionFactory.getCurrentSession().delete(act);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActivationInfo> listActivations() {
	    Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(Activation.class);
	    List<Activation> activations =(List<Activation>) crit.list();
	    List<ActivationInfo> activationInfos = new ArrayList<ActivationInfo>();
	    for(Activation b : activations){
	    	activationInfos.add(new ActivationInfo(b.getId(), b.getUsername(), b.getCode(), b.getRecordDate()));
	    }
	    return activationInfos;
	}
	
	@Override
	public void deleteUnusedAccs() {
		List<ActivationInfo> list = this.listActivations();
		for (ActivationInfo a : list) {
			if (userDAO.findLoginUserInfo(a.getUsername()) == null) {
				deleteActivation(a.getId());
				System.out.println("User data not found: "+a.getUsername());
			}
			DateTime now = new DateTime(new Date());
			DateTime rec = new DateTime(a.getRecordDate());
			int days = Days.daysBetween(rec, now).getDays();
			if (days > 5) {
				deleteActivation(a.getId());
				userDAO.deleteUser(userDAO.findLoginUserInfo(a.getUsername()).getId());
				System.out.println("Delete activation for "+days+" days - User : "+a.getUsername());
			}
			
		}
	}

}
