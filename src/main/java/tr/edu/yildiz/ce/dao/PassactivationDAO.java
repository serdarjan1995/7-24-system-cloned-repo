package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Passactivation;
import tr.edu.yildiz.ce.model.PassactivationInfo;

public interface PassactivationDAO {
	public Passactivation findPassactivation(Integer id);
	public Passactivation findPassactivationWithUser(Integer id);
	public Passactivation findPassactivationWithCode(String code);
	public void savePassactivation(Passactivation act);
	public void deletePassactivation(Integer id);
	public List<PassactivationInfo> listPassactivations();
	void deletePassactivationWithUser(Integer id);
}
