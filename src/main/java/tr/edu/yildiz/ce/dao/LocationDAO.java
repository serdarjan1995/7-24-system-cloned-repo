package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Location;
import tr.edu.yildiz.ce.model.LocationInfo;

public interface LocationDAO {
	public Location findLocation (Integer id); 
	public void saveLocation (LocationInfo locationInfo);
    public LocationInfo findLocationInfo (Integer id);  
    public void deleteLocation (Integer id);
    
    public List<LocationInfo> findParents();
    public List<LocationInfo> findChildInfos(Integer id);
    public List<LocationInfo> listLocationInfos();
	public List<LocationInfo> findLocationInfoTree(Integer id);
	public List<LocationInfo> listLocationInfoByProximity(Integer id);
	public List<LocationInfo> findLocationInfoUpperTree(Integer id);
	public List<LocationInfo> reportLocationInfos();
	public List<LocationInfo> listLocationInfosPagination(Integer offset, Integer maxResults);
    public Long count();
}
