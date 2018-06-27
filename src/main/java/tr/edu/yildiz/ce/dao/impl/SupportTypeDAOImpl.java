package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.entity.SupportType;
import tr.edu.yildiz.ce.entity.UserRole;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.model.UserRoleInfo;

public class SupportTypeDAOImpl implements SupportTypeDAO {
    @Autowired
    private ComplaintDAO complaintDAO;

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public SupportType findSupportType(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SupportType.class);
        crit.add(Restrictions.eq("id", id));
        return (SupportType) crit.uniqueResult();
	}

	@Override
	public void saveSupportType(SupportTypeInfo supportTypeInfo) {
        Integer id = supportTypeInfo.getId();
        SupportType supportType = null;
        if (id != null) {
        	supportType = this.findSupportType(id);
        }
        boolean isNew = false;
        if (supportType == null) {
            isNew = true;
            supportType = new SupportType();
        }
        supportType.setId(supportTypeInfo.getId());
        supportType.setType(supportTypeInfo.getType());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(supportType);
        }

	}

	@Override
	public SupportTypeInfo findSupportTypeInfo(Integer id) {
		SupportType supportType = this.findSupportType(id);
        if (supportType == null) {
            return null;
        }
        return new SupportTypeInfo(supportType.getId(), supportType.getType() );
	}

	@Override
	public void deleteSupportType(Integer id) {
		SupportType supportType = this.findSupportType(id);
        if (supportType != null) {
            this.sessionFactory.getCurrentSession().delete(supportType);
        }

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SupportTypeInfo> listSupportTypeInfos() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SupportType.class);
        List<SupportType> supportTypes=(List<SupportType>)crit.list();
        List<SupportTypeInfo> supportTypeInfos=new ArrayList<SupportTypeInfo>();
        for(SupportType s:supportTypes){
        	supportTypeInfos.add((SupportTypeInfo)findSupportTypeInfo(s.getId()));
        }
        return supportTypeInfos;
	}

	@Override
	public List<SupportTypeInfo> reportSupportTypeInfos() {
		List<SupportTypeInfo> supportTypeInfos= this.listSupportTypeInfos();
		List<SupportTypeInfo> list=new ArrayList<SupportTypeInfo>();
		for(SupportTypeInfo s:supportTypeInfos){
			List<ComplaintInfo> complaintInfos =complaintDAO.listComplaintInfos(null,s.getId(),null,null);
			if(complaintInfos.size()!=0){
				long totalAssignTime=0;
			    long totalAwarenessTime=0;
			    long totalResponseTime=0;
			    long numAssignTime=0;
			    long numAwarenessTime=0;
			    long numResponseTime=0;
			    long time=0;
			    s.setTotal(complaintInfos.size());
				s.setWaitingAssign(complaintDAO.listWaitingAssingnComplaintInfos(null,s.getId(),null,null).size());
				s.setWaitingAck(complaintDAO.listWaitingAckComplaintInfos(null,s.getId(),null,null).size());
				s.setActive(complaintDAO.listActiveComplaintInfos(null,s.getId(),null,null).size());
				s.setWaitingChild(complaintDAO.listWaitingChildComplaintInfos(null,s.getId(),null,null).size());
				s.setReported(complaintDAO.listReportedComplaintInfos(null,s.getId(),null,null).size());
				//times
				for(ComplaintInfo c:complaintInfos){
					if(c.getComplaintTime()!=null&&c.getAssignTime()!=null){
						time=c.getAssignTime().getTime()-c.getComplaintTime().getTime();
						if(time!=0){
							totalAssignTime+=time;
							numAssignTime++;
						}
					}
					if(c.getAssignTime()!=null&&c.getAckTime()!=null){
						time=c.getAckTime().getTime()-c.getAssignTime().getTime();
						if(time!=0){
							totalAwarenessTime+=time;
							numAwarenessTime++;
						}
					}
					if(c.getAckTime()!=null&&c.getResponseTime()!=null){
						time=c.getResponseTime().getTime()-c.getAckTime().getTime();
						if(time!=0){
							totalResponseTime+=time;
							numResponseTime++;	
						}
					}
				}
				if(numAssignTime!=0){
					s.setAvgAssignTime(totalAssignTime/numAssignTime);
				}
				if(numAwarenessTime!=0){
					s.setAvgAwarenessTime(totalAwarenessTime/numAwarenessTime);
				}
				if(numResponseTime!=0){
					s.setAvgResponseTime(totalResponseTime/numResponseTime);
				}
				list.add(s);
			}
		}
		return list;
	}
	

	@Override
	public List<SupportTypeInfo> listSupportTypeInfosPagination(Integer offset, Integer maxResults) {
		List<SupportTypeInfo> fullList=listSupportTypeInfos();
        List<SupportTypeInfo> offsetList=new ArrayList<SupportTypeInfo>();
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
        Criteria crit = session.createCriteria(SupportType.class);
        crit.setProjection(Projections.rowCount());
        return (Long) crit.uniqueResult();
	}

}
