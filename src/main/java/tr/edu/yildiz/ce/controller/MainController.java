package tr.edu.yildiz.ce.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tr.edu.yildiz.ce.dao.BanDAO;
import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.MailSend;
import tr.edu.yildiz.ce.dao.ManagerDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.SupporterDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.BanInfo;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocSupTypeInt;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.ManagerInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.model.SupporterInfo;
import tr.edu.yildiz.ce.model.SupporterRepInt;
import tr.edu.yildiz.ce.model.UserInfo;
import tr.edu.yildiz.ce.model.UserRoleInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class MainController {

	/*	@Autowired
	private UniValidator uniValidator;*/
	
	@Autowired
	private ManagerDAO managerDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	@Autowired
	private SupporterDAO supporterDAO;
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private BanDAO banDAO;
	
	@Autowired
	private MailSend mailSend;
    
	
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "7-24 Servisi Sistemi");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model) {
		List<LocationInfo> list = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", list);
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		List<UserRoleInfo> list3 = userRoleDAO.listUserRoleInfos();
		model.addAttribute("userRoleInfos", list3);
		List<SupporterInfo> list4 = supporterDAO.listSupporterInfos();
		for (SupporterInfo l : list4) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setUserInfo(userDAO.findUserInfo(l.getUserId()));
		}
		model.addAttribute("supporterInfos", list4);
		List<UserInfo> list5 = userDAO.listUserInfos();
		model.addAttribute("userInfos", list5);
		LocationInfo mod = new LocationInfo();
		model.addAttribute("locationForm", mod);
		return "adminPage";
	}
	
	@RequestMapping(value = "/supporterEdit", method = RequestMethod.GET)
	public String supporterEditPage(Model model, Integer offset, Integer maxResults) {
		
		List<LocationInfo> listLoc = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", listLoc);
		
		List<SupporterInfo> list = supporterDAO.listSupporterInfosPagination( offset,  maxResults);
		model.addAttribute("count", supporterDAO.count());
		model.addAttribute("offset", offset);
		model.addAttribute("supporterInfos", list);
		for (SupporterInfo l : list) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setUserInfo(userDAO.findUserInfo(l.getUserId()));
		}
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		
		List<UserInfo> list3 = userDAO.listUserInfos();
		model.addAttribute("userInfos", list3);
		
		return "supporterEditor";
	}
	
	@RequestMapping(value = "/supporterTypeEdit", method = RequestMethod.GET)
	public String supporterTypeEditPage(Model model,Integer offset, Integer maxResults) {
		
		
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfosPagination(offset, maxResults);
		model.addAttribute("count", supportTypeDAO.count());
		model.addAttribute("offset", offset);
		model.addAttribute("supportTypeInfos", list2);
		
		return "supportTypeEditor";
	}
	
	@RequestMapping(value = "/locationEdit", method = RequestMethod.GET)
	public String locationEditPage(Model model, Integer offset, Integer maxResults) {
		
		List<LocationInfo> listLoc = locationDAO.listLocationInfosPagination(offset, maxResults);
		model.addAttribute("count", locationDAO.count());
		model.addAttribute("offset", offset);
		model.addAttribute("locationInfos", listLoc);
		
		return "location";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String ban(Model model, Integer offset, Integer maxResults) {
		
		List<UserInfo> user = userDAO.listUserInfosPagination(offset, maxResults);
		model.addAttribute("count", userDAO.count());
		model.addAttribute("offset", offset);
		for (UserInfo u : user) {
			u.setBanned(banDAO.isBanned(u.getId()));
		}
		model.addAttribute("users", user);
		
		return "users";
	}
	
	@RequestMapping(value = "/banUser", method = RequestMethod.GET)
	public String banUser(Model model, @RequestParam(value = "id") Integer id) {
		
		UserInfo user = userDAO.findUserInfo(id);
		
		return this.userBanForm(model, user);
	}
	
	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public String unbanUser(Model model, @RequestParam(value = "id") Integer id,final RedirectAttributes redirectAttributes) {
		
		UserInfo user = userDAO.findUserInfo(banDAO.findBanInfo(id).getUserId());
		banDAO.deleteBan(id);
		redirectAttributes.addFlashAttribute("banSuccess", "Ban kaldırıldı");
		return "redirect:/banUser?id="+user.getId();
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String report(Model model ) {
		
		List<LocationInfo> loc = this.locationDAO.listLocationInfos();
		model.addAttribute("loc", loc);
		
		List<SupportTypeInfo> sup = this.supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("sup", sup);
		
//		List<LocSupTypeInt> list = new ArrayList<LocSupTypeInt>();
//		
//		for (LocationInfo l : loc) {
//			for (SupportTypeInfo s : sup) {
//				if (complaintDAO.listComplaintInfos(l.getId(), s.getId(),null,null).size() != 0) {
//					LocSupTypeInt x = new LocSupTypeInt();
//					x.setLocationInfo(l);
//					x.setSupportTypeInfo(s);
//					x.setActive(complaintDAO.listActiveComplaintInfos(l.getId(), s.getId(),null,null).size());
//					x.setReport(complaintDAO.listReportedComplaintInfos(l.getId(), s.getId(),null,null).size());
//					x.setTotal(complaintDAO.listComplaintInfos(l.getId(), s.getId(),null,null).size());
//					x.setWaitAck(complaintDAO.listWaitingAckComplaintInfos(l.getId(), s.getId(),null,null).size());
//					x.setWaitAsg(complaintDAO.listWaitingAssingnComplaintInfos(l.getId(), s.getId(),null,null).size());
//					x.setWaitChild(complaintDAO.listWaitingChildComplaintInfos(l.getId(), s.getId(),null,null).size());
//					x.setEnded(complaintDAO.listEndedComplaintInfos(l.getId(), s.getId(),null,null).size());
//					list.add(x);
//				}
//			}
//		}
		
		model.addAttribute("LocSupTypeInfo", complaintDAO.listLocSupTypeInt());
		
		List<SupporterInfo> sups = supporterDAO.reportSupporterInfos();
		//List<SupporterRepInt> list2 = new ArrayList<SupporterRepInt>();
		for (SupporterInfo s : sups) {
			//SupporterRepInt x = new SupporterRepInt();
			s.setLocationInfo(locationDAO.findLocationInfo(s.getLocationId()));
			s.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(s.getSupportTypeId()));
			//x.setComps(complaintDAO.listComplaintInfosByUserId(s.getUserId()).size());
			s.setUserInfo(userDAO.findUserInfo(s.getUserId()));
			//list2.add(x);
		}
		model.addAttribute("SupporterRepInfo", sups);
		
		Integer numTotal = complaintDAO.numOfProcess();
		model.addAttribute("total", numTotal);
		
		return "report";
	}
	
	@RequestMapping(value="/getLocSupComplaints",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getLocSupComplaints(@RequestParam(required = false) Integer id, @RequestParam(required = false) Integer id2) {
		String res = "";
		
//		List<LocationInfo> loc = this.locationDAO.listLocationInfos();
//		
//		List<SupportTypeInfo> sup = this.supportTypeDAO.listSupportTypeInfos();
		List<LocSupTypeInt> list2 = new ArrayList<LocSupTypeInt>();
		List<LocSupTypeInt> list = complaintDAO.listLocSupTypeInt();
		System.out.println("idler: "+id+" "+id2);
		for (LocSupTypeInt tmp : list) {
			if (id != 0 && tmp.getLocationInfo().getId() == id) {
				list2.add(tmp);
				System.out.println("Ekledim:"+tmp.getLocationInfo().getDescription());
			}
			if (id2 != 0 && tmp.getSupportTypeInfo().getId() == id2) {
				list2.add(tmp);
				System.out.println("Ekledim2:"+tmp.getSupportTypeInfo().getType());
			}
			if (id == 0 && id2 == 0) {
				list2.add(tmp);
			}
		}
//		for (LocationInfo l : loc) {
//			System.out.println("l id: "+l.getId());
//			if (id == null || id == 0 || l.getId() == id) {
//				for (SupportTypeInfo s : sup) {
//					System.out.println("s id: "+s.getId());
//					if (id2 == null || id2 == 0 || s.getId() == id2) {
//						if (complaintDAO.listComplaintInfos(l.getId(), s.getId(),null,null).size() != 0) {
//							LocSupTypeInt x = new LocSupTypeInt();
//							x.setLocationInfo(l);
//							x.setSupportTypeInfo(s);
//							x.setActive(complaintDAO.listActiveComplaintInfos(l.getId(), s.getId(),null,null).size());
//							x.setReport(complaintDAO.listReportedComplaintInfos(l.getId(), s.getId(),null,null).size());
//							x.setTotal(complaintDAO.listComplaintInfos(l.getId(), s.getId(),null,null).size());
//							x.setWaitAck(complaintDAO.listWaitingAckComplaintInfos(l.getId(), s.getId(),null,null).size());
//							x.setWaitAsg(complaintDAO.listWaitingAssingnComplaintInfos(l.getId(), s.getId(),null,null).size());
//							x.setWaitChild(complaintDAO.listWaitingChildComplaintInfos(l.getId(), s.getId(),null,null).size());
//							x.setEnded(complaintDAO.listEndedComplaintInfos(l.getId(), s.getId(),null,null).size());
//							list.add(x);
//						}
//					}
//				}
//			}
//		}
		for (LocSupTypeInt tmp : list2) {
			System.out.println("avg"+tmp.getAvgAwarenessTime());
			res = res.concat("<tr><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getAvgAssignTime()/86400000+" gün<br/>"+tmp.getAvgAssignTime()/3600000%24+" saat<br/>"+tmp.getAvgAssignTime()/60000%60+" dakika</td><td>"+tmp.getAvgAwarenessTime()/86400000+" gün<br/>"+tmp.getAvgAwarenessTime()/3600000%24+" saat<br/>"+tmp.getAvgAwarenessTime()/60000%60+" dakika</td><td>"+tmp.getAvgResponseTime()/86400000+" gün<br/>"+tmp.getAvgResponseTime()/3600000%24+" saat<br/>"+tmp.getAvgResponseTime()/60000%60+" dakika</td>");
			res = res.concat("<td onclick=\"getalldata("+tmp.getLocationInfo().getId()+", "+tmp.getSupportTypeInfo().getId()+", 0, 0, 1)\"><a onclick=\"$('#allDepartmentStatisticsCHILD').show();\">"+tmp.getTotal()+"</a></td><td onclick=\"getalldata("+tmp.getLocationInfo().getId()+", "+tmp.getSupportTypeInfo().getId()+", 0, 0, 2)\"><a onclick=\"$('#allDepartmentStatisticsCHILD').show();\">"+tmp.getWaitAck()+"</a></td><td onclick=\"getalldata("+tmp.getLocationInfo().getId()+", "+tmp.getSupportTypeInfo().getId()+", 0, 0, 3)\"><a onclick=\"$('#allDepartmentStatisticsCHILD').show();\">"+tmp.getWaitAsg()+"</a></td><td onclick=\"getalldata("+tmp.getLocationInfo().getId()+", "+tmp.getSupportTypeInfo().getId()+", 0, 0, 4)\"><a onclick=\"$('#allDepartmentStatisticsCHILD').show();\">"+tmp.getWaitChild()+"</a></td><td onclick=\"getalldata("+tmp.getLocationInfo().getId()+", "+tmp.getSupportTypeInfo().getId()+", 0, 0, 5)\"><a onclick=\"$('#allDepartmentStatisticsCHILD').show();\">"+tmp.getActive()+"</a></td><td onclick=\"getalldata("+tmp.getLocationInfo().getId()+", "+tmp.getSupportTypeInfo().getId()+", 0, 0, 6)\"><a onclick=\"$('#allDepartmentStatisticsCHILD').show();\">"+tmp.getEnded()+"</a></td><td onclick=\"getalldata("+tmp.getLocationInfo().getId()+", "+tmp.getSupportTypeInfo().getId()+", 0, 0, 7)\"><a onclick=\"$('#allDepartmentStatisticsCHILD').show();\">"+tmp.getReport()+"</a></td></tr>");
		}
		return res;
	}
	
	@RequestMapping(value="/getSupporterInfo",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getSupporterInfo(@RequestParam(required = false) Integer id, @RequestParam(required = false) Integer id2 ) {
		String res = "";
		System.out.println("idler: "+id+" "+id2);
		List<LocationInfo> loc = this.locationDAO.listLocationInfos();
		
		List<SupportTypeInfo> sup = this.supportTypeDAO.listSupportTypeInfos();
		
		List<SupporterInfo> supInfo = this.supporterDAO.reportSupporterInfos();
		
		for (LocationInfo l : loc) {
			if (l.getId() == id || id == 0 || id == null) {
				for (SupportTypeInfo s : sup) {
					if (s.getId() == id2 || id2 == 0 || id2 == null) {
						for (SupporterInfo sI : supInfo) {
							if (s.getId() == sI.getSupportTypeId() && l.getId() == sI.getLocationId()) {
								res = res.concat("<tr><td>"+userDAO.findUserInfo(sI.getUserId()).getUsername()+"</td><td>"+l.getDescription()+"</td><td>"+s.getType()+"</td><td onclick=\"getdata("+sI.getUserId()+")\"><a onclick=\"$('#supportPersonnelDensityCHILD').show();\">"+complaintDAO.listComplaintInfosByUserId(sI.getUserId()).size()+"</a></td><td>"+sI.getAvgAwarenessTime()/86400000+" gün<br/>"+sI.getAvgAwarenessTime()/3600000%24+" saat<br/>"+sI.getAvgAwarenessTime()/60000%60+" dakika</td><td>"+sI.getAvgResponseTime()/86400000+" gün<br/>"+sI.getAvgResponseTime()/3600000%24+" saat<br/>"+sI.getAvgResponseTime()/60000%60+" dakika</td><td><a onclick=\"$('#supportPersonnelDensityCHILD').show();\">"+sI.getWaitingAck()+"</a></td><td><a onclick=\"$('#supportPersonnelDensityCHILD').show();\">"+sI.getActive()+"</a></td><td><a onclick=\"$('#supportPersonnelDensityCHILD').show();\">"+sI.getWaitingChild()+"</a></td><td><a onclick=\"$('#supportPersonnelDensityCHILD').show();\">"+sI.getReported()+"</a></td><td><a onclick=\"$('#supportPersonnelDensityCHILD').show();\">"+sI.getEnded()+"</a></td></tr>");
							}
						}
					}
				}
			}
		}
		return res;
	}
	
	@RequestMapping(value = "/reportComplaintsPdf", method = RequestMethod.GET)
    public ModelAndView downloadExcel() {

        List<ComplaintInfo> listComplaints = complaintDAO.listActiveComplaintInfos(1, 1,null,null);
		for (ComplaintInfo l : listComplaints) {
			l.setLocationInfo(locationDAO.findLocationInfo(l.getLocationId()));
			l.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(l.getSupportTypeId()));
			l.setComplainantUserInfo(userDAO.findUserInfo(l.getComplainantUserId()));
		}
 
        return new ModelAndView("complaintPdfView", "listComplaints", listComplaints);
    }
	
	@RequestMapping(value = "/userBanForm", method = RequestMethod.GET)
	public String userBanForm(Model model, UserInfo user) {
		
		List<BanInfo> bans = banDAO.banHistory(user.getId());
		model.addAttribute("bans", bans);
		model.addAttribute("user", user);
		return "userBanForm";
	}
	
	@RequestMapping(value = "/userRoleEdit", method = RequestMethod.GET)
	public String userRoleEditPage(Model model, Integer offset, Integer maxResults) {
		
		List<UserRoleInfo> list3 = userRoleDAO.listUserRoleInfosPagination(offset, maxResults);
		model.addAttribute("count", userRoleDAO.count());
		model.addAttribute("offset", offset);
		model.addAttribute("userRoleInfos", list3);
		
		List<UserInfo> list5 = userDAO.listUserInfos();
		model.addAttribute("userInfos", list5);
		
		return "userRoleEditPage";
	}
	
	@RequestMapping(value = "/addManager", method = RequestMethod.GET)
	public String addManager(Model model, Integer offset, Integer maxResults) {

		List<UserInfo> user = userDAO.listUserInfos();
		model.addAttribute("userInfos", user);
		
		List<LocationInfo> list6 = locationDAO.listLocationInfos();
		
		for (LocationInfo l : list6) {
			while (l.getParentId() != null){
				l.setDescription(locationDAO.findLocationInfo(l.getParentId()).getDescription()+">"+l.getDescription());
				l = locationDAO.findLocationInfo(l.getParentId());
			}
		}
		model.addAttribute("locInfos", list6);
		
		
		List<ManagerInfo> listManager = managerDAO.listManagerInfosPagination(offset, maxResults);
		model.addAttribute("count", managerDAO.count());
		model.addAttribute("offset", offset);
		model.addAttribute("managerInfos", listManager);
		
		return "addManager";
	}
	
	@RequestMapping(value = "/complaint", method = RequestMethod.GET)
	public String complaintPage(Model model,Principal principal) {
		
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		model.addAttribute("userInfo", user);
		
		List<LocationInfo> list = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", list);
		
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		
		return "complaintPage";
	}
	
	@RequestMapping(value="/getLocationList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getLocationList() {
		String res = "<option id= value=>Üst konum seçin.</option>";
		List<LocationInfo> list = locationDAO.listLocationInfos();
		for (LocationInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getDescription()+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getUserList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getUserList() {
		String res = "<option id= value=>Kullanıcı seçin.</option>";
		List<UserInfo> list = userDAO.listUserInfos();
		for (UserInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getUsername()+"</option>");
		}
		return res;
	}
	
	@RequestMapping(value="/getUserRoleList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getUserRoleList() {
		String res = "<option id= value=>Rol seçin.</option>";
		List<UserRoleInfo> list = userRoleDAO.listUserRoleInfos();
		for (UserRoleInfo tmp : list) {
			res = res.concat("<option "+"id="+tmp.getId()+" value="+tmp.getId()+">"+tmp.getRole()+"</option>");
		}
		return res;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model ) {
		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {

		// After user login successfully.
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		model.addAttribute("userInfo", user);
		model.addAttribute("count", complaintDAO.countListComplaintInfosByComplainantUserIdPagination(userDAO.findLoginUserInfo(principal.getName()).getId()));
		return "userInfoPage";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			model.addAttribute("message", "Hi " + principal.getName()
			+ "<br> You do not have permission to access this page!");
		} else {
			model.addAttribute("msg",
					"You do not have permission to access this page!");
		}
		return "403Page";
	}
	
	@RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
         
        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
 
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Geçersiz istek";
                break;
            }
            case 401: {
                errorMsg = "Yetkilendirme yapılmadı";
                break;
            }
            case 404: {
                errorMsg = "İçerik bulunamadı";
                break;
            }
            case 500: {
                errorMsg = "İç sunucu hatası";
                break;
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        errorPage.addObject("errorCode", httpErrorCode);
        return errorPage;
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }
	
	@RequestMapping(value = "errors", method = RequestMethod.POST)
    public ModelAndView renderErrorPage2(HttpServletRequest httpRequest) {
        
        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
 
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Hata kodu: 400. Geçersiz istek";
                break;
            }
            case 401: {
                errorMsg = "Http Hata kodu: 401. Yetkilendirme yapılmadı";
                break;
            }
            case 404: {
                errorMsg = "Http Hata kodu: 404. İçerik bulunamadı";
                break;
            }
            case 500: {
                errorMsg = "Http Hata kodu: 500. İç sunucu hatası";
                break;
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        errorPage.addObject("errorCode", httpErrorCode);
        return errorPage;
    }
	
}