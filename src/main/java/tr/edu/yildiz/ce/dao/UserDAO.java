package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.User;
import tr.edu.yildiz.ce.model.UserInfo;
 
public interface UserDAO {
	
	public User findUser (Integer id); 
	public void saveUser (UserInfo userInfo);
    public UserInfo findUserInfo (Integer id);  
    public void deleteUser (Integer id);
    
    public User findLoginUser(String username);
    public UserInfo findLoginUserInfo(String username);
    public List<UserInfo> listUserInfos();
    public List<UserInfo> listUserInfosForAssignment(Integer complaintId);
    public List<UserInfo> reportSupportUserInfos();
    public List<UserInfo> listUserInfosPagination(Integer offset, Integer maxResults);
    public Long count();
	UserInfo findLoginUserInfoWithEmail(String username);
}