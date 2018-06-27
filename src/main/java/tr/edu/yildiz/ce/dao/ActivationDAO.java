package tr.edu.yildiz.ce.dao;

import java.util.List;

import tr.edu.yildiz.ce.entity.Activation;
import tr.edu.yildiz.ce.model.ActivationInfo;

public interface ActivationDAO {
	
	public Activation findActivation(Integer id);
	public Activation findActivationWithUser(Integer id);
	public Activation findActivationWithCode(String code);
	public void saveActivation(Activation act);
	public void deleteActivation(Integer id);
	public List<ActivationInfo> listActivations();
	public void deleteUnusedAccs();
}
