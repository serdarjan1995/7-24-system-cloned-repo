package tr.edu.yildiz.ce.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tr.edu.yildiz.ce.dao.BanDAO;
import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.model.BanInfo;
import tr.edu.yildiz.ce.model.ComplaintInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class BanController {
	
	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BanDAO banDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	@RequestMapping(value = "/saveBan", method = RequestMethod.POST)
	public String saveBan(Model model, //
			@ModelAttribute("banForm") @Validated BanInfo banInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("banError", "Ban yapılamadı");
			System.out.println("Hata!");
			return "redirect:/banUser?id="+banInfo.getUserId();
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(banInfo.getExplanation().getBytes("ISO-8859-1"), "UTF-8");
			banInfo.setExplanation(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Ban name cannot converted.");
			e.printStackTrace();
		}
		this.banDAO.banUser(banInfo.getUserId(), banInfo.getExplanation(), banInfo.getBanDay());

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("banSuccess", "Kullanıcı banlandı");

//		return "redirect:/deptList";
		return "redirect:/banUser?id="+banInfo.getUserId();
	}
}
