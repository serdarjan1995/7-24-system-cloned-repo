package tr.edu.yildiz.ce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class ManagerController {

	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	@RequestMapping(value = "/reportedComplaints", method = RequestMethod.GET)
	public String reportedComplaints(Model model, Principal principal, Integer offset, Integer maxResults ) {
		List<ComplaintInfo> list = complaintDAO.listReportedComplaintInfosForManagerPagination(userDAO.findLoginUserInfo(principal.getName()).getId(),offset,maxResults);
		model.addAttribute("count", complaintDAO.countListReportedComplaintInfosForManagerPagination(userDAO.findLoginUserInfo(principal.getName()).getId()));
		model.addAttribute("offset", offset);
		
		for (ComplaintInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
			l.setSupportUserInfo(userDAO.findUserInfo(l.getSupportUserId()));
		}
		model.addAttribute("complaintInfos", list);
		return "reportedComplaints";
	}
	
	@RequestMapping(value = "/assignComplaints", method = RequestMethod.GET)
	public String assignComplaints(Model model, Principal principal, Integer offset, Integer maxResults ) {
		List<ComplaintInfo> list = complaintDAO.listComplaintInfosForAssignmentPagination(userDAO.findLoginUserInfo(principal.getName()).getId(),offset,maxResults);
		model.addAttribute("count", complaintDAO.countListComplaintInfosForAssignmentPagination(userDAO.findLoginUserInfo(principal.getName()).getId()));
		model.addAttribute("offset", offset);
		
		for (ComplaintInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
		}
		model.addAttribute("complaintInfos", list);
		return "assignComplaints";
	}
	
	@RequestMapping(value = "/unifyComplaints", method = RequestMethod.GET)
	public String unifyComplaints(Model model, @RequestParam Integer id, Principal principal ) {
		List<ComplaintInfo> list = complaintDAO.listActiveComplaintInfosForUnification(id);
		for (ComplaintInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
		}
		model.addAttribute("complaintInfos", list);
		return "unifyComplaints";
	}
	
	
}
