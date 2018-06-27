package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.edu.yildiz.ce.dao.BanDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Ban;
import tr.edu.yildiz.ce.model.BanInfo;
import tr.edu.yildiz.ce.model.UserInfo;

@Service
@Transactional
public class BanDAOImpl implements BanDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Ban findBan(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Ban.class);
        crit.add(Restrictions.eq("id", id));
        return (Ban) crit.uniqueResult();
	}

	@Override
	public void saveBan(BanInfo banInfo) {
        Integer id = banInfo.getId();
        Ban ban = null;
        if (id != null) {
        	ban = this.findBan(id);
        }
        boolean isNew = false;
        if (ban == null) {
            isNew = true;
            ban = new Ban();
        }
        ban.setId(banInfo.getId());
        ban.setUserId(banInfo.getUserId());
        ban.setExplanation(banInfo.getExplanation());
        ban.setBanTime(banInfo.getBanTime());
        ban.setEndTime(banInfo.getEndTime());
        ban.setBanned(banInfo.isBanned());

        if (isNew){
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(ban);
        }

	}

	@Override
	public BanInfo findBanInfo(Integer id) {
		Ban ban=this.findBan(id);
		if( ban == null ){
			return null;
		}
		return new BanInfo(ban.getId(),ban.getUserId(),ban.getExplanation(),ban.getBanTime(),ban.getEndTime(),ban.isBanned());
	}

	@Override
	public void deleteBan(Integer id) {
		Ban ban = this.findBan(id);
        if (ban != null) {
            this.sessionFactory.getCurrentSession().delete(ban);
        }

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isBanned(Integer userId) {
		boolean banned = false;
		
	    Session session = this.sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(Ban.class);
	    crit.add(Restrictions.eq("userId", userId));
	    crit.add(Restrictions.eq("banned", true));
	    List<Ban> bans =(List<Ban>) crit.list();
	    
	    if(bans!=null){
	    	for(Ban b:bans){
	    		Date dateNow= new Date();
	    		if(dateNow.after(b.getEndTime())){
	    			b.setBanned(false);
	    			
	    		}else{
	    			banned = true;
	    		}
	    	}
	    }
	    return banned; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BanInfo> banHistory(Integer userId) {
	    Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(Ban.class);
	    crit.add(Restrictions.eq("userId", userId));
	    List<Ban> bans =(List<Ban>) crit.list();
	    List<BanInfo> banInfos = new ArrayList<BanInfo>();
	    for(Ban b:bans){
	    	banInfos.add(this.findBanInfo(b.getId()));
	    }
	    return banInfos;
	}

	@Override
	public void banUser(Integer userId, String explanation, Integer banDay) {
		UserInfo u=userDAO.findUserInfo(userId);
		u.setEnabled(false);
		userDAO.saveUser(u);
		BanInfo banInfo=new BanInfo();
		banInfo.setUserId(userId);
		banInfo.setExplanation(explanation);
		Calendar cal= Calendar.getInstance();
		banInfo.setBanTime(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, banDay);
		banInfo.setEndTime(cal.getTime());
		banInfo.setBanned(true);
		this.saveBan(banInfo);
	}

	@Override
	public void removeBan(Integer userId) {
		 List<BanInfo> banInfos = this.banHistory(userId);
		 for(BanInfo b:banInfos){
			 if(b.isBanned()){
				 b.setBanned(false);
				 this.saveBan(b);
			 }
		 }
	}
}
