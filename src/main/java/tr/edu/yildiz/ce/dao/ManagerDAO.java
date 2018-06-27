package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Manager;
import tr.edu.yildiz.ce.model.ManagerInfo;


public interface ManagerDAO {
	public Manager findManager (Integer id); 
	public void saveManager (ManagerInfo managerInfo);
    public ManagerInfo findManagerInfo (Integer id);  
    public void deleteManager (Integer id);
    
    public List<ManagerInfo> listManagerInfosById(Integer userId);
    public List<ManagerInfo> listManagerInfos();
    public List<ManagerInfo> listManagerInfosPagination(Integer offset, Integer maxResults);
    public Long count();

}
