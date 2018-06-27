package tr.edu.yildiz.ce.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.MailSend;
import tr.edu.yildiz.ce.dao.ManagerDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.NotificationDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.entity.Complaint;
import tr.edu.yildiz.ce.entity.User;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocSupTypeInt;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.ManagerInfo;
import tr.edu.yildiz.ce.model.NotificationInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;
import tr.edu.yildiz.ce.model.UserInfo;

public class ComplaintDAOImpl implements ComplaintDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ManagerDAO managerDAO;
	@Autowired
	private LocationDAO locationDAO;
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	@Autowired
	private UserDAO userDAO;	
	@Autowired
	private SupporterDAO supporterDAO;
	@Autowired
	private NotificationDAO notificationDAO;
	@Autowired
	private MailSend mailSend;
	@Override
	public Complaint findComplaint(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("id", id));
        return (Complaint) crit.uniqueResult();
	}

	@Override
	public void saveComplaint(ComplaintInfo complaintInfo) {
        Integer id = complaintInfo.getId();
        Complaint complaint = null;
        if (id != null) {
        	complaint = this.findComplaint(id);
        }
        boolean isNew = false;
        if (complaint == null) {
            isNew = true;
            complaint = new Complaint();
        }
        complaint.setId(complaintInfo.getId());
        complaint.setLocationId(complaintInfo.getLocationId());
        complaint.setSupportTypeId(complaintInfo.getSupportTypeId());
        complaint.setParentId(complaintInfo.getParentId());
        complaint.setComplainantUserId( complaintInfo.getComplainantUserId() );
        complaint.setComplaintTime(complaintInfo.getComplaintTime());
        complaint.setComplaintText(complaintInfo.getComplaintText());
        complaint.setSupportUserId(complaintInfo.getSupportUserId());
        complaint.setResponseTime(complaintInfo.getResponseTime());
        complaint.setResponseText(complaintInfo.getResponseText());
        complaint.setChildId(complaintInfo.getChildId());
        complaint.setEnded(complaintInfo.isEnded());
        complaint.setAck(complaintInfo.isAck());
        complaint.setAckTime(complaintInfo.getAckTime());
        complaint.setReported(complaintInfo.isReported());
        complaint.setAssignTime(complaintInfo.getAssignTime());
        complaint.setComplaintFile(complaintInfo.getComplaintFile());
        complaint.setResponseFile(complaintInfo.getResponseFile());
        System.out.println(complaintInfo.getComplaintFile());
        if (isNew){
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(complaint);
        }
	}

	@Override
	public ComplaintInfo findComplaintInfo(Integer id) {
		Complaint complaint = this.findComplaint(id);
        if (complaint == null) {
            return null;
        }
        return new ComplaintInfo(complaint.getId(), complaint.getLocationId() ,complaint.getSupportTypeId() ,complaint.getParentId(),
        		complaint.getComplainantUserId(),complaint.getComplaintTime(), complaint.getComplaintText(),complaint.getSupportUserId(),
        		complaint.getResponseTime(),complaint.getResponseText(),complaint.getChildId() ,complaint.isEnded(),complaint.getAckTime(),
        		complaint.isAck(),complaint.isReported(),complaint.getAssignTime(),complaint.getComplaintFile(),complaint.getResponseFile());
	}

	@Override
	public void deleteComplaint(Integer id) {
		Complaint complaint = this.findComplaint(id);
        if (complaint != null) {
            this.sessionFactory.getCurrentSession().delete(complaint);
        }
	}

	@Override
	public void recordComplaint(Integer locationId, Integer supportTypeId, Integer complainantUserId,
			String complaintText,Integer parentId,byte[] complaintFile ) {
		//notifications
		ComplaintInfo complaintInfo = new ComplaintInfo();
		complaintInfo.setLocationId(locationId);
		complaintInfo.setSupportTypeId(supportTypeId);
		complaintInfo.setComplainantUserId(complainantUserId);
		complaintInfo.setComplaintText(complaintText);
		complaintInfo.setEnded(false);
		complaintInfo.setAck(false);
		complaintInfo.setParentId(parentId);
		complaintInfo.setReported(false);
		complaintInfo.setComplaintFile(complaintFile);
		
		Date dateNow=new Date();
		complaintInfo.setComplaintTime(dateNow);
		
		saveComplaint(complaintInfo);
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("locationId", locationId));
        crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        crit.add(Restrictions.eq("complainantUserId", complainantUserId));
        crit.add(Restrictions.eq("complaintText", complaintText));
        crit.add(Restrictions.eq("complaintTime", complaintInfo.getComplaintTime()));
        Complaint complaint=(Complaint) crit.uniqueResult();
        if(parentId==null){
			NotificationInfo notificationInfo =new NotificationInfo();
			notificationInfo.setUserId(complaint.getComplainantUserId());
			notificationInfo.setComplaintId(complaint.getId());
			notificationDAO.saveNotification(notificationInfo);
			complaintInfo=findComplaintInfo(complaint.getId());
		
			String to=userDAO.findUserInfo(complainantUserId).getEmail();
			String subject="Complaint Recorded";
			String text="  Merhaba "+userDAO.findUserInfo(complainantUserId).getUsername()+",\n"+
					"Location : "+locationDAO.findLocationInfo(locationId).getDescription()+",\n"+
					"Support Type : "+supportTypeDAO.findSupportTypeInfo( supportTypeId).getType() + ",\n"+
					"Complaint Text : "+complaintText+ ",\n"+
					"Complaint Time : "+ dateNow.toString()+ ",\n";
			mailSend.sendSimpleMessage(to, subject, text);
        }
	}

	@Override
	public void ackComplaint(Integer id) {
		ComplaintInfo c =this.findComplaintInfo(id);
		c.setAck(true);
		Date dateNow =new Date();
		c.setAckTime(dateNow);
		saveComplaint(c);
	}
	@Override
	public void reportComplaint(Integer id) {
		ComplaintInfo c =this.findComplaintInfo(id);
		c.setReported(true);
		c.setEnded(true);
		Date dateNow =new Date();
		if(c.getAckTime()==null){
			c.setAckTime(dateNow);
		}
		if(c.getResponseTime()==null){
			c.setResponseTime(dateNow);
		}
		saveComplaint(c);
	}
	@Override
	public void assingComplaint(Integer id, Integer supportUserId) {
		ComplaintInfo c =this.findComplaintInfo(id);
		List<SupporterInfo> supporterInfos=supporterDAO.listSupporterInfosById(supportUserId);
		List<Integer> supporterLocIds=new ArrayList<Integer>();
		List<Integer> locTreeIds=new ArrayList<Integer>();
		Integer locationId=null;
		for(SupporterInfo s:supporterInfos){
			if(s.getSupportTypeId()==c.getSupportTypeId()){
				supporterLocIds.add(s.getLocationId());
			}
		}
		List< LocationInfo> locTreeInfos=locationDAO.findLocationInfoUpperTree(c.getLocationId());
		for(LocationInfo l:locTreeInfos){
			locTreeIds.add(l.getId());
		}
		for(Integer i:locTreeIds){
			for(Integer j:supporterLocIds){
				if(i==j&&locationId==null){
					locationId=i;
				}
			}
		}
		c.setLocationId(locationId);
		c.setSupportUserId(supportUserId);
		Date dateNow =new Date();
		c.setAssignTime(dateNow);
		saveComplaint(c);
	}
	
	@Override
	public void transferComplaint(Integer id,  String responseText, Integer newLocationId,
		Integer newSupportTypeId, String newComplaintText,boolean ended,byte[] complaintFile,byte[] responseFile) {
		//notifications
		Integer parentComplaintId=id;
		Integer childComplaintId;
		ComplaintInfo complaintInfo = this.findComplaintInfo(id);
		complaintInfo.setResponseText(responseText);
		Date dateNow =new Date();
		if(complaintInfo.getResponseTime()==null){
			complaintInfo.setResponseTime(dateNow);
		}
		complaintInfo.setEnded(ended);
		complaintInfo.setResponseFile(responseFile);
		if(complaintInfo.getAckTime()==null){
			complaintInfo.setAckTime(dateNow);
		}
		if(complaintInfo.getAssignTime()==null){
			complaintInfo.setAssignTime(dateNow);
		}
		if(complaintInfo.isReported()==true){
			complaintInfo.setEnded(true);
			complaintInfo.setAck(true);
		}
		complaintInfo.setReported(false);
		
		this.recordComplaint(newLocationId,newSupportTypeId,complaintInfo.getComplainantUserId(),
				newComplaintText,complaintInfo.getId(),complaintFile);
		complaintInfo.setChildId(this.findChildInfo(id).getId());
		childComplaintId=findChildInfo(id).getId();
		this.saveComplaint(complaintInfo);
		childComplaintId=findChildInfo(id).getId();
		List<NotificationInfo> notificationInfos = notificationDAO.listNotificationInfosForComplaint(parentComplaintId);
		for(NotificationInfo n : notificationInfos){
			n.setComplaintId(childComplaintId);
			notificationDAO.saveNotification(n);
		}
		
	}

	@Override
	public void endComplaint(Integer id, String responseText,byte[] responseFile) {
		boolean sendEmail=false;
		ComplaintInfo complaintInfo = this.findComplaintInfo(id);
		complaintInfo.setResponseText(responseText);
		
		Date dateNow =new Date();
		if(complaintInfo.getResponseTime()==null){
			complaintInfo.setResponseTime(dateNow);
		}
		if(complaintInfo.getAckTime()==null){
			complaintInfo.setAckTime(dateNow);
		}
		if(complaintInfo.getAssignTime()==null){
			complaintInfo.setAssignTime(dateNow);
		}
		complaintInfo.setResponseFile(responseFile);
		complaintInfo.setEnded(true);
		complaintInfo.setReported(false);
		saveComplaint(complaintInfo);
		
		if(complaintInfo.getParentId()!=null){
			ComplaintInfo notComplete = findComplaintInfo(complaintInfo.getParentId());
			while( notComplete.getParentId()!=null && notComplete.isEnded()==true ){
				notComplete =findComplaintInfo(notComplete.getParentId());
			}
			if(notComplete.isEnded()==false){
				notComplete.setEnded(true);
				saveComplaint(notComplete);
				transferComplaint(id, responseText,notComplete.getLocationId(),
						notComplete.getSupportTypeId(), notComplete.getComplaintText(),true,notComplete.getComplaintFile(),responseFile);
			}else{
				sendEmail=true;
			}
				
		}else {
			sendEmail=true;
		}
		if(sendEmail){
			List<NotificationInfo> notificationInfos = notificationDAO.listNotificationInfosForComplaint(id);
			List<ComplaintInfo> process = this.listComplaintProcess(id);
			String past="\n";
			for(ComplaintInfo c: process ){
				past=past+"Location : "+ locationDAO.findLocationInfo(c.getLocationId()).getDescription()+",\n"+
						"Support Type : "+supportTypeDAO.findSupportTypeInfo(c.getSupportTypeId() ).getType() + ",\n"+
						"Response Text : "+c.getResponseText()+ ",\n"+
						"Complaint Time : "+c.getComplaintTime().toString()+ ",\n"+
						"Response Time : "+c.getResponseTime().toString()+ ",\n\n";
			}
			for(NotificationInfo n : notificationInfos){
				String to=userDAO.findUserInfo(n.getUserId()).getEmail();
				String subject="Complaint Resulted";
				String text="	Merhaba "+userDAO.findUserInfo(n.getUserId()).getUsername()+",\n"+past;
				mailSend.sendSimpleMessage(to, subject, text);
			}
		}
	}

	@Override
	public void uniteComplaints(Integer uniteTo, Integer delete) {
		List<ComplaintInfo> del = this.listComplaintProcess(delete);
		ComplaintInfo c = this.findComplaintInfo(uniteTo);
		while(c.getChildId()!=null){
			c=this.findComplaintInfo(c.getChildId());
			uniteTo = c.getId();
		}
		List<Integer> deleteList=new ArrayList<Integer>();
		List<NotificationInfo> notificationInfos = notificationDAO.listNotificationInfosForComplaint(delete);
		for(NotificationInfo n : notificationInfos){
			if(notificationDAO.doesExist(n.getUserId(),uniteTo)){
				deleteList.add(n.getId());
			}else{
				n.setComplaintId(uniteTo);
				notificationDAO.saveNotification(n);
			}
		}
		for(Integer id:deleteList){
			notificationDAO.deleteNotification(id);
		}
		for(ComplaintInfo d:del){
			deleteComplaint(d.getId());
		}
	}
	
	public ComplaintInfo findChildInfo(Integer parentId){
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("parentId", parentId));
        Complaint c=(Complaint)crit.uniqueResult();
        return findComplaintInfo(c.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfos(Integer locationId,Integer supportTypeId,Integer supporterId,Integer supportUserId) {
        if(supporterId==null&&supportUserId==null){
        	Session session = sessionFactory.getCurrentSession();
            List<Complaint> complaints =new ArrayList<Complaint>();
            if(supportTypeId==null&&locationId==null){
            	Criteria crit = session.createCriteria(Complaint.class);
            	complaints.addAll(crit.list());
            }
            if(supportTypeId!=null&&locationId==null){
            	Criteria crit = session.createCriteria(Complaint.class);
            	crit.add(Restrictions.eq("supportTypeId", supportTypeId));
            	complaints.addAll(crit.list());
            }
            if(locationId!=null){
                List<LocationInfo> locationInfoTree = locationDAO.findLocationInfoTree(locationId);
        		for(LocationInfo l:locationInfoTree){
        			Criteria crit = session.createCriteria(Complaint.class);
        	        if(supportTypeId!=null){
        	        	crit.add(Restrictions.eq("supportTypeId", supportTypeId));
        	        }
        	        crit.add(Restrictions.eq("locationId", l.getId()));
        	        complaints.addAll(crit.list());
        		}
            }

            List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
            for(Complaint c:complaints){
            	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
            }
            return complaintInfos;
        }
        if(supporterId!=null){
        	return this.listComplaintInfosBySupporterId(supporterId);
        }
        if(supportUserId!=null){
        	return this.listComplaintInfosByUserId(supportUserId);
        }
        return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForSupport(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("supportUserId", userId));
        crit.add(Restrictions.eq("ack", true));
        crit.add(Restrictions.eq("reported", false));
        crit.add(Restrictions.eq("ended",false));
        crit.add(Restrictions.isNull("childId"));
		List<Complaint> complaints = (List<Complaint>)crit.list();
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
		
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForSupportPagination(Integer userId,Integer offset, Integer maxResults) {
		List<ComplaintInfo> fullList=listComplaintInfosForSupport(userId);
        List<ComplaintInfo> offsetList=new ArrayList<ComplaintInfo>();
        maxResults = maxResults!=null?maxResults:10;
        offset = (offset!=null?offset:0);
        for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
        	offsetList.add(fullList.get(offset+i));
        }
        return offsetList;
	}
	
	@Override
	public Long countComplaintInfosForSupportPagination(Integer userId) {
		List<ComplaintInfo> fullList=listComplaintInfosForSupport(userId);
        return Integer.toUnsignedLong(fullList.size()) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForSupportAck(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("supportUserId", userId));
        crit.add(Restrictions.eq("ack", false));
        crit.add(Restrictions.eq("reported", false));
		List<Complaint> complaints = (List<Complaint>)crit.list();
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
		
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForSupportAckPagination(Integer userId,Integer offset, Integer maxResults) {
		List<ComplaintInfo> fullList=listComplaintInfosForSupportAck(userId);
        List<ComplaintInfo> offsetList=new ArrayList<ComplaintInfo>();
        maxResults = maxResults!=null?maxResults:10;
        offset = (offset!=null?offset:0);
        for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
        	offsetList.add(fullList.get(offset+i));
        }
        return offsetList;
	}
	
	@Override
	public Long countComplaintInfosForSupportAckPagination(Integer userId) {
		List<ComplaintInfo> fullList=listComplaintInfosForSupportAck(userId);
        return Integer.toUnsignedLong(fullList.size()) ;
	}
	
	
	@Override
	public List<ComplaintInfo> listComplaintProcess(Integer id) {
		ComplaintInfo c=this.findComplaintInfo(id);
		ComplaintInfo parent;
		ComplaintInfo child;
		long totalTime;
		long beginTime;
		long endTime;
		long div;
		long parentEnd;
		if(c==null){
			return null;
		}
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
		while(c.getParentId()!=null){
			c=this.findComplaintInfo(c.getParentId());
		}
		complaintInfos.add(c);
		while(c.getChildId()!=null){
			c=this.findComplaintInfo(c.getChildId());
			complaintInfos.add(c);
		}
		child=complaintInfos.get(complaintInfos.size()-1);
		parent=complaintInfos.get(0);
		beginTime=parent.getComplaintTime().getTime();
		parentEnd=beginTime;
		if(child.getResponseTime()!=null){
			endTime=child.getResponseTime().getTime();
		}else{
			endTime=new Date().getTime();
		}
		div=100;
		totalTime=endTime-beginTime;
		long totalFinal=0;
		long numOfPer=0;
		for(ComplaintInfo com:complaintInfos){
			long assign;
			long ack;
			long response;
			long reported;
			reported=com.getComplaintTime().getTime()-parentEnd;
			if(com.getAssignTime()!=null){
				assign=com.getAssignTime().getTime()-com.getComplaintTime().getTime();
			}else{
				assign=0;
			}
			if(com.getAckTime()!=null){
				ack=com.getAckTime().getTime()-com.getAssignTime().getTime();
			}else{
				ack=0;
			}
			if(com.getResponseTime()!=null){
				response=com.getResponseTime().getTime()-com.getAckTime().getTime();
				parentEnd=com.getResponseTime().getTime();
			}else{
				response=0;
			}
			long assignFinal=(assign*div)/totalTime;
			totalFinal+=assignFinal;
			if(assignFinal!=0){
				numOfPer++;
			}
			long ackFinal=(ack*div)/totalTime;
			totalFinal+=ackFinal;
			if(ackFinal!=0){
				numOfPer++;
			}
			long responseFinal=(response*div)/totalTime;
			totalFinal+=responseFinal;
			if(responseFinal!=0){
				numOfPer++;
			}
			long reportedFinal=(reported*div)/totalTime;
			totalFinal+=reportedFinal;
			if(reportedFinal!=0){
				numOfPer++;
			}
			com.setPercentAssign(assignFinal);
			com.setPercentAck(ackFinal);
			com.setPercentResponse(responseFinal);
			com.setPercentReported(reportedFinal);
		}
		if(totalFinal<100){
			System.out.println("Sayı"+numOfPer);
			if (numOfPer == 0 ){
				numOfPer = 1;
			}
			long inc=1+(100-totalFinal)/numOfPer;;
			long amount=100-totalFinal;
			for(ComplaintInfo com:complaintInfos){
				if(com.getPercentAssign()!=0&&amount>0){
					if(amount>inc){
						com.setPercentAssign(com.getPercentAssign()+inc);
						amount=amount-inc;
					}else{
						com.setPercentAssign(com.getPercentAssign()+amount);
						amount=0;
					}
				}
				if(com.getPercentAck()!=0&&amount>0){
					if(amount>inc){
						com.setPercentAck(com.getPercentAck()+inc);
						amount=amount-inc;
					}else{
						com.setPercentAck(com.getPercentAck()+amount);
						amount=0;
					}
				}
				if(com.getPercentResponse()!=0&&amount>0){
					if(amount>inc){
						com.setPercentResponse(com.getPercentResponse()+inc);
						amount=amount-inc;
					}else{
						com.setPercentResponse(com.getPercentResponse()+amount);
						amount=0;
					}
				}
				if(com.getPercentReported()!=0&&amount>0){
					if(amount>inc){
						com.setPercentReported(com.getPercentReported()+inc);
						amount=amount-inc;
					}else{
						com.setPercentReported(com.getPercentReported()+amount);
						amount=0;
					}
				}
			}
		}
		return complaintInfos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForAssignment(Integer userId) {
        List<ManagerInfo> managerInfos= managerDAO.listManagerInfosById(userId);
        List<LocationInfo> locationInfoTree = new ArrayList<LocationInfo>();
        List<Integer> locationInfoTreeIds=new ArrayList<Integer>();
        for(ManagerInfo m:managerInfos){
        	List<LocationInfo> loc=locationDAO.findLocationInfoTree(m.getLocationId());
        	List<Integer> locIds=new ArrayList<Integer>();
    		for(LocationInfo l:loc){
    			locIds.add(l.getId());
    		}
        	locIds.removeAll(locationInfoTreeIds);
        	for(Integer l:locIds){
        		locationInfoTreeIds.add(l);
        		locationInfoTree.add(locationDAO.findLocationInfo(l));
        	}
        }
        Session session = sessionFactory.getCurrentSession();
        List<Complaint> complaints =new ArrayList<Complaint>(); 
		for(LocationInfo l:locationInfoTree){
			Criteria crit = session.createCriteria(Complaint.class);
			crit.add(Restrictions.isNull("supportUserId"));
	        crit.add(Restrictions.eq("locationId", l.getId()));
	        crit.add(Restrictions.eq("ended",false));
	        complaints.addAll(crit.list());
		}
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosForAssignmentPagination(Integer userId,Integer offset, Integer maxResults){
		List<ComplaintInfo> fullList=listComplaintInfosForAssignment( userId);
	    List<ComplaintInfo> offsetList=new ArrayList<ComplaintInfo>();
	    maxResults = maxResults!=null?maxResults:10;
	    offset = (offset!=null?offset:0);
	    for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
	    	offsetList.add(fullList.get(offset+i));
	    }
	    return offsetList;
	}
	
	@Override
	public Long countListComplaintInfosForAssignmentPagination(Integer userId){
		List<ComplaintInfo> fullList=listComplaintInfosForAssignment( userId);
		return Integer.toUnsignedLong(fullList.size()) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listReportedComplaintInfosForManager(Integer userId) {
        List<ManagerInfo> managerInfos= managerDAO.listManagerInfosById(userId);
        List<LocationInfo> locationInfoTree = new ArrayList<LocationInfo>();
        List<Integer> locationInfoTreeIds=new ArrayList<Integer>();
        for(ManagerInfo m:managerInfos){
        	List<LocationInfo> loc=locationDAO.findLocationInfoTree(m.getLocationId());
        	List<Integer> locIds=new ArrayList<Integer>();
    		for(LocationInfo l:loc){
    			locIds.add(l.getId());
    		}
        	locIds.removeAll(locationInfoTreeIds);
        	for(Integer l:locIds){
        		locationInfoTreeIds.add(l);
        		locationInfoTree.add(locationDAO.findLocationInfo(l));
        	}
        }
        
        Session session = sessionFactory.getCurrentSession();
        List<Complaint> complaints =new ArrayList<Complaint>(); 
		for(LocationInfo l:locationInfoTree){
			Criteria crit = session.createCriteria(Complaint.class);
	        crit.add(Restrictions.eq("reported", true));
	        crit.add(Restrictions.eq("locationId", l.getId()));
	        complaints.addAll(crit.list());
		}
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>(); 
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listReportedComplaintInfosForManagerPagination(Integer userId,Integer offset, Integer maxResults){
		List<ComplaintInfo> fullList=listReportedComplaintInfosForManager( userId);
	    List<ComplaintInfo> offsetList=new ArrayList<ComplaintInfo>();
	    maxResults = maxResults!=null?maxResults:10;
	    offset = (offset!=null?offset:0);
	    for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
	    	offsetList.add(fullList.get(offset+i));
	    }
	    return offsetList;
	}
	
	@Override
	public Long countListReportedComplaintInfosForManagerPagination(Integer userId){
		List<ComplaintInfo> fullList=listReportedComplaintInfosForManager( userId);
		return Integer.toUnsignedLong(fullList.size()) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listActiveComplaintInfosForUnification(Integer id) {
		ComplaintInfo com=this.findComplaintInfo(id);
		List<LocationInfo> locTree=locationDAO.listLocationInfoByProximity(com.getLocationId());
		SupportTypeInfo supportTypeInfo=supportTypeDAO.findSupportTypeInfo(com.getSupportTypeId());
        Session session = sessionFactory.getCurrentSession();
        List<Complaint> complaints =new ArrayList<Complaint>();
        List<Integer> complaintIds =new ArrayList<Integer>();
        for(LocationInfo l:locTree){
        	Criteria crit = session.createCriteria(Complaint.class);
            crit.add(Restrictions.eq("ended",false));
            crit.add(Restrictions.eq("locationId",l.getId()));
            crit.add(Restrictions.eq("supportTypeId",supportTypeInfo.getId()));
            complaints.addAll(crit.list());
        }
        for(Complaint c: complaints){
        	complaintIds.add(c.getId());
        }
        List<Complaint> allComplaints =new ArrayList<Complaint>();
        List<Integer> allComplaintIds =new ArrayList<Integer>();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("supportTypeId",supportTypeInfo.getId()));
        crit.add(Restrictions.eq("ended",false));
        allComplaints.addAll(crit.list());
        for(Complaint c: allComplaints){
        	allComplaintIds.add(c.getId());
        }
        allComplaintIds.removeAll(complaintIds);
        complaintIds.addAll(allComplaintIds);
        complaintIds.remove(id);
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
        for(Integer i:complaintIds){
        	complaintInfos.add(this.findComplaintInfo(i));
        }
        return complaintInfos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer numOfProcess() {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.isNull("childId"));
        List<Complaint> complaints = crit.list();
        return complaints.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosByUserId(Integer supportUserId) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("supportUserId",supportUserId));
		List<Complaint> complaints = (List<Complaint>)crit.list();
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}

	@Override
	public List<ComplaintInfo> listWaitingAssingnComplaintInfos(Integer locationId, Integer supportTypeId,Integer supporterId,Integer supportUserId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId,supporterId,supportUserId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.getSupportUserId()==null&&c.isEnded()==false){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}

	@Override
	public List<ComplaintInfo> listWaitingAckComplaintInfos(Integer locationId, Integer supportTypeId,Integer supporterId,Integer supportUserId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId,supporterId,supportUserId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isAck()==false&&c.getSupportUserId()!=null&&c.isReported()==false){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}

	@Override
	public List<ComplaintInfo> listActiveComplaintInfos(Integer locationId, Integer supportTypeId,Integer supporterId,Integer supportUserId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId,supporterId,supportUserId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isAck()==true&&c.isEnded()==false&&c.getChildId()==null&&c.isReported()==false){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}
	@Override
	public List<ComplaintInfo> listWaitingChildComplaintInfos(Integer locationId, Integer supportTypeId,Integer supporterId,Integer supportUserId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId,supporterId,supportUserId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isAck()==true&&c.isEnded()==false&&c.getChildId()!=null&&c.isReported()==false){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}

	@Override
	public List<ComplaintInfo> listReportedComplaintInfos(Integer locationId, Integer supportTypeId,Integer supporterId,Integer supportUserId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId,supporterId,supportUserId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isReported()==true&&c.isEnded()==false){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}

	@Override
	public List<ComplaintInfo> listEndedComplaintInfos(Integer locationId, Integer supportTypeId,Integer supporterId,Integer supportUserId) {
		List<ComplaintInfo> complaintInfos=this.listComplaintInfos(locationId, supportTypeId,supporterId,supportUserId);
		List<ComplaintInfo> retComplaintInfos=new ArrayList<ComplaintInfo>();
		for(ComplaintInfo c:complaintInfos){
			if(c.isEnded()==true&&c.isReported()==false){
				retComplaintInfos.add(c);
			}
		}
		return retComplaintInfos;
	}
	
	@Override
	public List<ComplaintInfo> listEndedComplaintInfosPagination(Integer locationId, Integer supportTypeId,Integer supporterId,Integer supportUserId,Integer offset, Integer maxResults) {
		List<ComplaintInfo> fullList=listEndedComplaintInfos( locationId,  supportTypeId, supporterId, supportUserId);
        List<ComplaintInfo> offsetList=new ArrayList<ComplaintInfo>();
        maxResults = maxResults!=null?maxResults:10;
        offset = (offset!=null?offset:0);
        for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
        	offsetList.add(fullList.get(offset+i));
        }
        return offsetList;
	}
	
	@Override
	public Long countListEndedComplaintInfosPagination(Integer locationId, Integer supportTypeId,Integer supporterId,Integer supportUserId) {
		List<ComplaintInfo> fullList=listEndedComplaintInfos( locationId,  supportTypeId, supporterId, supportUserId);
        return Integer.toUnsignedLong(fullList.size()) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosBySupporterId(Integer supporterId) {
		SupporterInfo supporterInfo = supporterDAO.findSupporterInfo(supporterId);
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("supportUserId",supporterInfo.getUserId()));
        crit.add(Restrictions.eq("locationId", supporterInfo.getLocationId()));
        crit.add(Restrictions.eq("supportTypeId", supporterInfo.getSupportTypeId()));
        List<Complaint> complaints = (List<Complaint>)crit.list();
        List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
        for(Complaint c:complaints){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        }
        return complaintInfos;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosByComplainantUserId(Integer complainantUserId) {
		List<NotificationInfo>notificationInfos=notificationDAO.listNotificationInfosForUser(complainantUserId);
		List<Integer>notificationComplaintIds=new ArrayList<Integer>();
		for(NotificationInfo n:notificationInfos){
			notificationComplaintIds.add(n.getComplaintId());
		}
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Complaint.class);
        crit.add(Restrictions.eq("complainantUserId",complainantUserId));
		List<Complaint> complaints = (List<Complaint>)crit.list();
		List<ComplaintInfo> complaintInfos =new ArrayList<ComplaintInfo>();
        for(Complaint c:complaints){
        	if(c.getChildId()==null){
            	complaintInfos.add((ComplaintInfo)findComplaintInfo(c.getId()));
        	}
        	if(notificationComplaintIds.contains(c.getId())){
        		notificationComplaintIds.remove(c.getId());
        	}
        }
        for(Integer i:notificationComplaintIds){
        	complaintInfos.add((ComplaintInfo)findComplaintInfo(i));
        }
        return complaintInfos;
	}

	@Override
	public List<LocSupTypeInt> listLocSupTypeInt() {
		List<LocationInfo> loc = this.locationDAO.listLocationInfos();
		
		List<SupportTypeInfo> sup = this.supportTypeDAO.listSupportTypeInfos();
		
		List<LocSupTypeInt> list = new ArrayList<LocSupTypeInt>();
		
		for (LocationInfo l : loc) {
			for (SupportTypeInfo s : sup) {
				List<ComplaintInfo> complaintInfos =this.listComplaintInfos(l.getId(), s.getId(),null,null);
				if (complaintInfos.size() != 0) {
					long totalAssignTime=0;
				    long totalAwarenessTime=0;
				    long totalResponseTime=0;
				    long numAssignTime=0;
				    long numAwarenessTime=0;
				    long numResponseTime=0;
				    long time=0;
					LocSupTypeInt x = new LocSupTypeInt();
					x.setLocationInfo(l);
					x.setSupportTypeInfo(s);
					x.setTotal(complaintInfos.size());
					x.setActive(this.listActiveComplaintInfos(l.getId(), s.getId(),null,null).size());
					x.setReport(this.listReportedComplaintInfos(l.getId(), s.getId(),null,null).size());
					x.setWaitAck(this.listWaitingAckComplaintInfos(l.getId(), s.getId(),null,null).size());
					x.setWaitAsg(this.listWaitingAssingnComplaintInfos(l.getId(), s.getId(),null,null).size());
					x.setWaitChild(this.listWaitingChildComplaintInfos(l.getId(), s.getId(),null,null).size());
					x.setEnded(this.listEndedComplaintInfos(l.getId(), s.getId(),null,null).size());
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
						x.setAvgAssignTime(totalAssignTime/numAssignTime);
					}
					if(numAwarenessTime!=0){
						x.setAvgAwarenessTime(totalAwarenessTime/numAwarenessTime);
					}
					if(numResponseTime!=0){
						x.setAvgResponseTime(totalResponseTime/numResponseTime);
					}
					list.add(x);
				}
			}
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> listComplaintInfosByComplainantUserIdPagination(Integer complainantUserId,Integer offset, Integer maxResults) {
		List<ComplaintInfo> fullList=listComplaintInfosByComplainantUserId( complainantUserId);
	    List<ComplaintInfo> offsetList=new ArrayList<ComplaintInfo>();
	    maxResults = maxResults!=null?maxResults:10;
	    offset = (offset!=null?offset:0);
	    for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
	    	offsetList.add(fullList.get(offset+i));
	    }
	    return offsetList;
	}
	
	@Override
	public Long countListComplaintInfosByComplainantUserIdPagination(Integer complainantUserId){
		List<ComplaintInfo> fullList=listComplaintInfosByComplainantUserId( complainantUserId);
		return Integer.toUnsignedLong(fullList.size()) ;
	}

}