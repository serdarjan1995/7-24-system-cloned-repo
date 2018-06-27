package tr.edu.yildiz.ce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tr.edu.yildiz.ce.dao.BanDAO;
import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.MailSend;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;
import tr.edu.yildiz.ce.model.UserRoleInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class SupporterController {
	
	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SupporterDAO supporterDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private BanDAO banDAO;
	
	@Autowired
	private MailSend mailSend;
	
	@RequestMapping(value = "/supporter", method = RequestMethod.GET)
	public String supporter(Model model, Principal principal, Integer offset, Integer maxResults) {
		List<ComplaintInfo> list = complaintDAO.listComplaintInfosForSupportPagination(userDAO.findLoginUserInfo(principal.getName()).getId(),offset, maxResults);
		model.addAttribute("count", complaintDAO.countComplaintInfosForSupportPagination(userDAO.findLoginUserInfo(principal.getName()).getId()));
		model.addAttribute("offset", offset);
		for (ComplaintInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
		}
		model.addAttribute("complaintInfos", list);
		return "supporter";
	}
	
	@RequestMapping(value = "/supporterAck", method = RequestMethod.GET)
	public String supporterAck(Model model, Principal principal, Integer offset, Integer maxResults) {
		List<ComplaintInfo> list = complaintDAO.listComplaintInfosForSupportAckPagination(userDAO.findLoginUserInfo(principal.getName()).getId(),offset, maxResults);
		model.addAttribute("count", complaintDAO.countComplaintInfosForSupportAckPagination(userDAO.findLoginUserInfo(principal.getName()).getId()));
		model.addAttribute("offset", offset);
		for (ComplaintInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
		}
		model.addAttribute("complaintInfos", list);
		return "supporterAck";
	}
	
	@RequestMapping(value = "/supporterPast", method = RequestMethod.GET)
	public String supporterPast(Model model, Principal principal, Integer offset, Integer maxResults) {
		List<ComplaintInfo> list = complaintDAO.listEndedComplaintInfosPagination(null, null, null, userDAO.findLoginUserInfo(principal.getName()).getId(),offset,maxResults);
		model.addAttribute("count", complaintDAO.countListEndedComplaintInfosPagination(null, null, null, userDAO.findLoginUserInfo(principal.getName()).getId()));
		model.addAttribute("offset", offset);
		for (ComplaintInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
		}
		model.addAttribute("complaintInfos", list);
		return "supporterPast";
	}
	
	@RequestMapping(value = "/ackComplaint", method = RequestMethod.GET)
	public String ackComplaint(Model model, @RequestParam(value = "id") Integer id, Principal principal) {
		
		ComplaintInfo list = complaintDAO.findComplaintInfo(id);
		if ( list == null) {
			model.addAttribute("message", "There is no complaint");
			return "supporterAck";
		}
		complaintDAO.ackComplaint(id);
		model.addAttribute("message", "Şikayet onaylandı.");
		return "redirect:/supporter";
	}
	
	@RequestMapping(value = "/reportComplaint", method = RequestMethod.GET)
	public String reportComplaint(Model model, @RequestParam(value = "id") Integer id, Principal principal) {
		
		ComplaintInfo list = complaintDAO.findComplaintInfo(id);
		if ( list == null) {
			model.addAttribute("message", "There is no complaint");
			return "supporterAck";
		}
		complaintDAO.reportComplaint(id);
		model.addAttribute("message", "Şikayet raporlandı.");
		return "redirect:/supporterAck";
	}
	
	@RequestMapping(value = "/unifyComplaint", method = RequestMethod.GET)
	public String unifyComplaint(Model model, @RequestParam(value = "id") Integer id, @RequestParam(value = "id2") Integer id2, Principal principal) {
		
		ComplaintInfo list = complaintDAO.findComplaintInfo(id);
		ComplaintInfo list2 = complaintDAO.findComplaintInfo(id2);
		if ( list == null || list2 == null) {
			model.addAttribute("message", "There is no complaint");
			return "reportedComplaints";
		}
		complaintDAO.uniteComplaints(id, id2);
		model.addAttribute("message", "Şikayetler birleştirildi.");
		return "reportedComplaints";
	}
	
	@RequestMapping(value = "/saveSupporter", method = RequestMethod.POST)
	public String saveSupporter(Model model, //
			@ModelAttribute("supporterForm") @Validated SupporterInfo supporterInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("supMsgError", "Hatalı giriş.");
			System.out.println("Hata!");
			return "supporterEditor";
		}
		userRoleDAO.saveUserRole(new UserRoleInfo(null, supporterInfo.getUserId(), "SUPPORT"));
		this.supporterDAO.saveSupporter(supporterInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("supMsgSuccess", "Destek birimi eklendi.");

//		return "redirect:/deptList";
		return "redirect:/supporterEdit";
	}
	
	@RequestMapping(value = "/deleteSupporter", method = RequestMethod.GET)
	public String deleteSupporter(Model model, @RequestParam(value = "id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id == null) {
			redirectAttributes.addFlashAttribute("supMsgError", "Destek silinemedi.");
			return "redirect:/supporterEdit";
		}
		this.supporterDAO.deleteSupporter(id);
		redirectAttributes.addFlashAttribute("supMsgSuccess", "Destek silindi.");
		return "redirect:/supporterEdit";
	}
}
