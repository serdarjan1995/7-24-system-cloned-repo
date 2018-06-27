package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.entity.UserRole;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.UserRoleInfo;

@Service
@Transactional
public class UserRoleDAOImpl implements UserRoleDAO {
	

	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public UserRole findUserRole(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(UserRole.class);
        crit.add(Restrictions.eq("id", id));
        return (UserRole) crit.uniqueResult();
	}

	@Override
	public void saveUserRole(UserRoleInfo userRoleInfo) {
        Integer id = userRoleInfo.getId();
        UserRole userRole = null;
        if (id != null) {
        	userRole = this.findUserRole(id);
        }
        boolean isNew = false;
        if (userRole == null) {
            isNew = true;
            userRole = new UserRole();
        }
        userRole.setId(userRoleInfo.getId());
        userRole.setUserId(null);
        if(userRoleInfo.getUserInfo()!=null){
        	userRole.setUserId(userRoleInfo.getUserInfo().getId());
        }else{
        	userRole.setUserId(userRoleInfo.getUserId());
        }
        userRole.setRole(userRoleInfo.getRole());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(userRole);
        }

	}

	@Override
	public UserRoleInfo findUserRoleInfo(Integer id) {
		UserRole userRole = this.findUserRole(id);
        if (userRole == null) {
            return null;
        }
		return new UserRoleInfo(userRole.getId(),userRole.getUserId(),userRole.getRole());
	}

	@Override
	public boolean deleteUserRole(Integer id) {
		UserRole userRole = this.findUserRole(id);
        if (userRole != null) {
        	this.sessionFactory.getCurrentSession().delete(userRole);
        	return true;
        }
        return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserRoles(Integer userId) {
		List<String> userRoles = new ArrayList<String>(); 
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(UserRole.class);
        crit.add(Restrictions.eq("userId", userId));
        List<UserRole> roles = (List<UserRole>) crit.list();
        for(UserRole r : roles){
        	userRoles.add(r.getRole());
        }
        return userRoles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRoleInfo> listUserRoleInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(UserRole.class);
        List<UserRole> userRoles =(List<UserRole>) crit.list();
        List<UserRoleInfo> userRoleInfos=new ArrayList<UserRoleInfo>();
        for(UserRole u : userRoles){
        	userRoleInfos.add((UserRoleInfo)findUserRoleInfo(u.getId()));
        }
        return userRoleInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRoleInfo> listUserRoleInfosPagination(Integer offset, Integer maxResults) {
        List<UserRoleInfo> fullList=listUserRoleInfos();
        List<UserRoleInfo> offsetList=new ArrayList<UserRoleInfo>();
        maxResults = maxResults!=null?maxResults:10;
        offset = (offset!=null?offset:0);
        for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
        	offsetList.add(fullList.get(offset+i));
        }
        return offsetList;
	}
	
	@Override
	public Long count() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(UserRole.class);
        crit.setProjection(Projections.rowCount());
        return (Long) crit.uniqueResult();
	}

}
