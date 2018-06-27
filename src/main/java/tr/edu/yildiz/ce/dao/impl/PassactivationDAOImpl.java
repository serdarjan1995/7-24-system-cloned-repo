package tr.edu.yildiz.ce.dao.impl;

import tr.edu.yildiz.ce.dao.PassactivationDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Passactivation;
import tr.edu.yildiz.ce.model.PassactivationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;


public class PassactivationDAOImpl implements PassactivationDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	@Override
	public Passactivation findPassactivation(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Passactivation.class);
        crit.add(Restrictions.eq("id", id));
        return (Passactivation) crit.uniqueResult();
	}
	
	@Override
	public Passactivation findPassactivationWithCode(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Passactivation.class);
        crit.add(Restrictions.eq("code", code));
        return (Passactivation) crit.uniqueResult();
	}
	
	@Override
	public Passactivation findPassactivationWithUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Passactivation.class);
        crit.add(Restrictions.eq("userId", id));
        return (Passactivation) crit.uniqueResult();
	}
	
	@Override
	public void savePassactivation(Passactivation act) {
		Integer id = act.getId();
		Passactivation actv = null;
		if( id!=null ){
			actv = this.findPassactivation(id);
		}
		boolean isNew = false;
		if( actv == null ){
			isNew = true;
			actv = new Passactivation();
		}
		actv.setId(act.getId());
	    actv.setCode(act.getCode());
	    actv.setUserId(act.getUserId());
	    if(isNew){
	    	Session session=this.sessionFactory.getCurrentSession();
	    	session.persist(actv);
	    }
		
	}

	@Override
	public void deletePassactivation(Integer id) {
		Passactivation act = this.findPassactivation(id);
		if(act!=null){
			this.sessionFactory.getCurrentSession().delete(act);
		}
	}
	
	@Override
	public void deletePassactivationWithUser(Integer id) {
		Passactivation act = this.findPassactivationWithUser(id);
		if(act!=null){
			this.sessionFactory.getCurrentSession().delete(act);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PassactivationInfo> listPassactivations() {
	    Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(Passactivation.class);
	    List<Passactivation> activations =(List<Passactivation>) crit.list();
	    List<PassactivationInfo> activationInfos = new ArrayList<PassactivationInfo>();
	    for(Passactivation b : activations){
	    	activationInfos.add(new PassactivationInfo(b.getId(), b.getUserId(), b.getCode()));
	    }
	    return activationInfos;
	}
}
