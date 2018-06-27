package tr.edu.yildiz.ce.controller;

import java.io.UnsupportedEncodingException;
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

import tr.edu.yildiz.ce.dao.ManagerDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.ManagerInfo;
import tr.edu.yildiz.ce.model.UserInfo;
import tr.edu.yildiz.ce.model.UserRoleInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class UserRoleController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ManagerDAO managerDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
	public String saveUserRole(Model model, //
			@ModelAttribute("userRoleForm") @Validated UserRoleInfo userRoleInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			

		if (result.hasErrors()) {
			model.addAttribute("userRoleMsgError", "Eklenemedi.");
			return "userRoleEditPage";
		}
		System.out.println(userRoleInfo.getRole());
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(userRoleInfo.getRole().getBytes("ISO-8859-1"), "UTF-8");
			userRoleInfo.setRole(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("UserRole cannot converted.");
			e.printStackTrace();
		}
		userRoleInfo.setUserId(userRoleInfo.getUserId());
		if (userRoleInfo.getRole().equals("MANAGER")) {
			ManagerInfo m = new ManagerInfo(null, userRoleInfo.getUserId(), userRoleInfo.getLocid());
			managerDAO.saveManager(m);
		}
		this.userRoleDAO.saveUserRole(userRoleInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("userRoleMsgSuccess", "Başarıyla eklendi.");

//		return "redirect:/deptList";
		return "redirect:/userRoleEdit";
	}
	
	@RequestMapping(value = "/deleteUserRole", method = RequestMethod.GET)
	public String deleteUserRole(Model model, @RequestParam Integer id, final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "redirect:/userRoleEdit";
		}
		UserRoleInfo userRole = userRoleDAO.findUserRoleInfo(id);
		UserInfo user = userDAO.findUserInfo(userRole.getUserId());
		if (this.userRoleDAO.deleteUserRole(id)) {
			redirectAttributes.addFlashAttribute("userRoleMsgSuccess", "Silindi");
			if (userRole.getRole().equals("MANAGER")) {
				List<ManagerInfo> mn = managerDAO.listManagerInfosById(user.getId());
				for (ManagerInfo m : mn) {
					managerDAO.deleteManager(m.getId());
				}
				return "redirect:/addManager";
			}
			return "redirect:/userRoleEdit";
		}
		redirectAttributes.addFlashAttribute("userRoleMsgError", "Hata oluştu!");
		return "redirect:/userRoleEdit";
	}
	
}