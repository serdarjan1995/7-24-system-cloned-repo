package tr.edu.yildiz.ce.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import tr.edu.yildiz.ce.dao.ActivationDAO;
import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.MailSend;
import tr.edu.yildiz.ce.dao.PassactivationDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.entity.Activation;
import tr.edu.yildiz.ce.entity.Passactivation;
import tr.edu.yildiz.ce.model.UserRoleInfo;

import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.UserInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class UserController {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	@Autowired
	private ActivationDAO activationDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MailSend mailSend;
	
	@Autowired
	private PassactivationDAO passactivationDAO;
	
	public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}
	
	@RequestMapping(value = "/userPast", method = RequestMethod.GET)
	public String userPast(Model model, Principal principal, Integer offset, Integer maxResults) {
		List<ComplaintInfo> list = complaintDAO.listComplaintInfosByComplainantUserIdPagination(userDAO.findLoginUserInfo(principal.getName()).getId(),offset,maxResults);
		model.addAttribute("count", complaintDAO.countListComplaintInfosByComplainantUserIdPagination(userDAO.findLoginUserInfo(principal.getName()).getId()));
		model.addAttribute("offset", offset);
		for (ComplaintInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
		}
		model.addAttribute("complaintInfos", list);
		return "userPast";
	}
	
	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public String changePass(Model model, Principal principal, @ModelAttribute("userForm") @Validated UserInfo userInfo,final RedirectAttributes redirectAttributes) {
		if(userInfo.getPassword().equals(userInfo.getPasswordConf())) {
			System.out.println("Pass equals");
			UserInfo user = userDAO.findLoginUserInfo(principal.getName());
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(userInfo.getPassword()));
			userDAO.saveUser(user);
			redirectAttributes.addFlashAttribute("msg", "Şifreniz değiştirildi.");
			return "redirect:/userInfo";
		}
		redirectAttributes.addFlashAttribute("msg", "Şifre eşleşmedi. Girdileri kontrol edin");
		return "redirect:/userInfo";
	}
	
	@RequestMapping(value = "/refreshPass", method = RequestMethod.POST)
	public String refreshPass(Model model, Principal principal, @ModelAttribute("userForm") @Validated UserInfo userInfo,final RedirectAttributes redirectAttributes) {
		if(userInfo.getPassword().equals(userInfo.getPasswordConf())) {
			System.out.println("Pass equals");
			UserInfo user = userDAO.findUserInfo(userInfo.getId());
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(userInfo.getPassword()));
			userDAO.saveUser(user);
			redirectAttributes.addFlashAttribute("msg", "Şifreniz değiştirildi.");
			passactivationDAO.deletePassactivationWithUser(userInfo.getId());
			return "redirect:/login";
		}
		redirectAttributes.addFlashAttribute("msg", "Şifreniz değiştirilemedi.");
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(Model model, //
			@ModelAttribute("userForm") @Validated UserInfo userInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			model.addAttribute("signupMsgError", "Hatalı giriş!");
			System.out.println("Hata!");
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(userInfo.getUsername().getBytes("ISO-8859-1"), "UTF-8");
			userInfo.setUsername(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Username cannot converted.");
			e.printStackTrace();
		}
		if (!userInfo.getPassword().equals(userInfo.getPasswordConf())) {
			model.addAttribute("signupMsgError", "Parola eşleşmedi.");
			System.out.println("Parola eşleşmedi.");
			return "loginPage";
		}
		if (!validate(userInfo.getEmail())) {
			model.addAttribute("signupMsgError", "Email onaylanmadı.");
			System.out.println("Email onaylanmadı.");
			return "loginPage";
		}
		if (this.userDAO.findLoginUser(userInfo.getUsername()) != null) {
			model.addAttribute("signupMsgError", "Kullanıcı mevcut.");
			System.out.println("Kullanıcı mevcut.");
			return "loginPage";
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		System.out.println(userInfo.getUsername()+userInfo.getPassword()+userInfo.getEmail());
		this.userDAO.saveUser(userInfo);

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("signupMsgSuccess", "Hesabınızı mailize gelen aktivasyon linki ile aktive edin.");

//		return "redirect:/deptList";
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public String activate(Model model, @RequestParam String code,	final RedirectAttributes redirectAttributes) {
		Activation act = activationDAO.findActivationWithCode(code);
		if (act != null) {
			UserInfo user = userDAO.findLoginUserInfo(act.getUsername());
			user.setEnabled(true);
			userDAO.saveUser(user);
			System.out.println(act.getId()+act.getUsername());
			activationDAO.deleteActivation(act.getId());
			UserRoleInfo user2 = new UserRoleInfo();
			user2.setUserInfo(user);
			user2.setRole("USER");
			userRoleDAO.saveUserRole(user2);
			redirectAttributes.addFlashAttribute("signupMsgSuccess", "Hesabınız aktif edildi.");
			return "redirect:/login";
		}
		else {
			return "redirect:/403";
		}
	}
	
	@RequestMapping(value = "/repass", method = RequestMethod.GET)
	public String repass(Model model, @RequestParam String code,	final RedirectAttributes redirectAttributes) {
		Passactivation act = passactivationDAO.findPassactivationWithCode(code);
		if (act != null) {
			System.out.println("act uid:"+act.getUserId());
			UserInfo user = userDAO.findUserInfo(act.getUserId());
			System.out.println(user.getUsername());
			model.addAttribute("user", user);
			model.addAttribute("signupMsgSuccess", "Yeni şifrenizi  giriniz.");
			return "newPass";
		}
		else {
			return "redirect:/403";
		}
	}
	
	@RequestMapping(value = "/guestAct", method = RequestMethod.GET)
	public String guestAct(Model model, @RequestParam String mail,	final RedirectAttributes redirectAttributes) {
		if (!validate(mail)) {
			model.addAttribute("signupMsgError", "Email onaylanmadı.");
			System.out.println("Email onaylanmadı.");
			return "loginPage";
		}
		UserInfo user = new UserInfo();
		user.setUsername(mail);
		user.setEmail(mail);
		user.setEnabled(false);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode("123"));
		Activation act = new Activation();
		act.setUsername(user.getUsername());
	    act.setCode(getSaltString());
	    act.setRecordDate(new Date());
	    activationDAO.saveActivation(act);
	    String text = "7/24 ziyaretçi hesabını aktif etmek için aşağıdaki linke tıklayın.\n\n";
	    text = text.concat("http://localhost:8080/sysprog/activate?code="+act.getCode().toString()+"\n Kullanıcı adı: "+user.getUsername()+"\nŞifre: 123\n Giriş yaptıktan sonra lütfen şifrenizi değiştiriniz.");
	    mailSend.sendSimpleMessage(user.getEmail(), "7/24 Sistem Aktivasyon", text);
		redirectAttributes.addFlashAttribute("signupMsgSuccess", "Mailinize gönderilen link ile aktivasyonu tamamlayın.");
		userDAO.saveUser(user);
		return "redirect:/login";
	}
	
	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	@RequestMapping(value = "/forgotPass", method = RequestMethod.POST)
	public String forgotPass(Model model, @ModelAttribute ("userForm") @Validated UserInfo userInfo,	final RedirectAttributes redirectAttributes) {
		if (userDAO.findLoginUserInfoWithEmail(userInfo.getEmail()) == null) {
			redirectAttributes.addFlashAttribute("msg", "E-mail bulunamadı.");
			return "redirect:/login";
		}
		else {
			String code = getSaltString();
			passactivationDAO.savePassactivation(new Passactivation(null, userDAO.findLoginUserInfoWithEmail(userInfo.getEmail()).getId(), code ));
			mailSend.sendSimpleMessage(userInfo.getEmail(), "Şifre yenileme", "http://localhost:8080/sysprog/repass?code="+code);
			redirectAttributes.addFlashAttribute("msg", "Şifre yenileme linki yollandı.");;
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/mailForgot", method = RequestMethod.GET)
	public String mailForgot(Model model, final RedirectAttributes redirectAttributes) {
		return "mailForgot";
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(Model model, @RequestParam(value = "id") Integer id, final RedirectAttributes redirectAttributes) {
		if (id == null) {
			return "redirect:/users";
		}
		this.userDAO.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "Kullanıcı silindi.");
		return "redirect:/users";
	}
	
	@RequestMapping(value = "/deleteInactiveUsers", method = RequestMethod.GET)
	public String deleteInactiveUsers(Model model, final RedirectAttributes redirectAttributes) {
		
		this.activationDAO.deleteUnusedAccs();
		redirectAttributes.addFlashAttribute("message", "İnaktif kullanıcılar silindi.");
		return "redirect:/users";
	}
}
