package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Notification;
import tr.edu.yildiz.ce.model.NotificationInfo;

public interface NotificationDAO {
	public Notification findNotification (Integer id); 
	public void saveNotification (NotificationInfo notificationInfo);
    public NotificationInfo findNotificationInfo (Integer id);  
    public void deleteNotification (Integer id);
    public List<NotificationInfo> listNotificationInfos ();
    public List<NotificationInfo> listNotificationInfosForComplaint (Integer complaintId);
    public List<NotificationInfo> listNotificationInfosForUser (Integer userId);
    public boolean doesExist(Integer userId,Integer complaintId);
}
