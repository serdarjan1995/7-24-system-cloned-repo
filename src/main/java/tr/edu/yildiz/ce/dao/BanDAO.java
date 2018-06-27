package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Ban;
import tr.edu.yildiz.ce.model.BanInfo;

public interface BanDAO {
	public Ban findBan(Integer id);
	public void saveBan (BanInfo banInfo);
	public BanInfo findBanInfo (Integer id);
	public void deleteBan (Integer id);
	
	public boolean isBanned (Integer userId);
	public List<BanInfo> banHistory(Integer userId);
	public void banUser (Integer userId,String explanation,Integer banDay);
	public void removeBan (Integer userId);
}
