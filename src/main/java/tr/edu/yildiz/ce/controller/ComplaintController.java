package tr.edu.yildiz.ce.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Blob;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import tr.edu.yildiz.ce.dao.ComplaintDAO;
import tr.edu.yildiz.ce.dao.LocationDAO;
import tr.edu.yildiz.ce.dao.SupportTypeDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.model.LocationInfo;
import tr.edu.yildiz.ce.model.SupportTypeInfo;
import tr.edu.yildiz.ce.model.UserInfo;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class ComplaintController {

	@Autowired
	private ComplaintDAO complaintDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Autowired
	private SupportTypeDAO supportTypeDAO;
	
	@RequestMapping(value = "/saveComplaint", method = RequestMethod.POST)
	public String saveComplaint(Model model, HttpServletRequest request, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			@RequestParam("file2") MultipartFile file,BindingResult result, //
			final RedirectAttributes redirectAttributes) throws IOException, ServletException {
		if (result.hasErrors()) {
			model.addAttribute("compMsgError", "Hatalı giriş.");
			System.out.println("Hata!");
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(complaintInfo.getComplaintText().getBytes("ISO-8859-1"), "UTF-8");
			complaintInfo.setComplaintText(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}
		System.out.println(complaintInfo.getLocationId()+" "+complaintInfo.getSupportTypeId()+" "+complaintInfo.getComplainantUserId()+" "+complaintInfo.getComplaintText()+" "+complaintInfo.getFile());
		if (file.isEmpty()) {
			this.complaintDAO.recordComplaint(complaintInfo.getLocationId(), complaintInfo.getSupportTypeId(), complaintInfo.getComplainantUserId(), complaintInfo.getComplaintText(), complaintInfo.getParentId(),null);

            System.out.println("file empty!");
            return "redirect:/complaint";
        }
//		Byte[] bytes2 = new Byte[file.getBytes().length];
//		byte[] bytes;
		else {
			try {
	
	            // Get the file and save it somewhere
	        	byte[] bytes = file.getBytes();
	        	this.complaintDAO.recordComplaint(complaintInfo.getLocationId(), complaintInfo.getSupportTypeId(), complaintInfo.getComplainantUserId(), complaintInfo.getComplaintText(), complaintInfo.getParentId(),bytes);
	
	//            int i=0;    
	            // Associating Byte array values with bytes. (byte[] to Byte[])
	//            for(byte b: bytes)
	//               bytes2[i++] = b;  // Autoboxing.
	//            Path path = Paths.get("D://temp//" + file.getOriginalFilename());
	//            Files.write(path, bytes);
	            redirectAttributes.addFlashAttribute("message",
	                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
	            System.out.println("kaydettim");
	            
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsgSuccess", "Şikayet gönderildi.");

//		return "redirect:/deptList";
		return "redirect:/complaint";
	}
	
	@RequestMapping(value = "/save2Complaint", method = RequestMethod.POST)
	public String save2Complaint(Model model, HttpServletRequest request, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			@RequestParam("file2") MultipartFile file,BindingResult result, //
			final RedirectAttributes redirectAttributes) throws IOException, ServletException {
			
		if (result.hasErrors()) {
			model.addAttribute("compMsgError", "Hatalı giriş.");
			System.out.println("Hata!");
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(complaintInfo.getComplaintText().getBytes("ISO-8859-1"), "UTF-8");
			complaintInfo.setComplaintText(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Dept name cannot converted.");
			e.printStackTrace();
		}
		ComplaintInfo cmp = complaintDAO.findComplaintInfo(complaintInfo.getId());
		cmp.setLocationId(complaintInfo.getLocationId());
		cmp.setSupportTypeId(complaintInfo.getSupportTypeId());
		cmp.setComplaintText(complaintInfo.getComplaintText());
		
		if (file.isEmpty()) {
			this.complaintDAO.saveComplaint(cmp);
            System.out.println("file empty!");
            return "redirect:/assignComplaints";
        }
//		Byte[] bytes2 = new Byte[file.getBytes().length];
//		byte[] bytes;
		else {
			try {
	
	            // Get the file and save it somewhere
	        	byte[] bytes = file.getBytes();
	        	
	    		cmp.setComplaintFile(bytes);
	    		this.complaintDAO.saveComplaint(cmp);
	//            int i=0;    
	            // Associating Byte array values with bytes. (byte[] to Byte[])
	//            for(byte b: bytes)
	//               bytes2[i++] = b;  // Autoboxing.
	//            Path path = Paths.get("D://temp//" + file.getOriginalFilename());
	//            Files.write(path, bytes);
	            redirectAttributes.addFlashAttribute("message",
	                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
	            System.out.println("kaydettim");
	            
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsgSuccess", "Şikayet gönderildi.");

//		return "redirect:/deptList";
		return "redirect:/assignComplaints";
	}
	
	@RequestMapping(value = "/endComplaint", method = RequestMethod.GET)
	public String endComplaint(Model model,Principal principal, @RequestParam("id") Integer id) {
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		if(id == null){
			return "manager";
		}
		model.addAttribute("userInfo", user);
		ComplaintInfo comp = this.complaintDAO.findComplaintInfo(id);
		comp.setComplainantUserInfo(userDAO.findUserInfo(comp.getComplainantUserId()));
		comp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(comp.getSupportTypeId()));
		comp.setLocationInfo(locationDAO.findLocationInfo(comp.getLocationId()));
		model.addAttribute("comp", comp);
//		return "redirect:/deptList";
		return "endComplaint";
	}
	
	@RequestMapping(value = "/endedComplaint", method = RequestMethod.POST)
	public String endedComplaint(Model model, Principal principal, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			@RequestParam("file2") MultipartFile file,BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("compMsgError", "Şikayet kapatılamadı.");
			System.out.println("Hata!");
			if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("MANAGER")) {
				return "redirect:/reportedComplaints";
			}
			else {
				return "redirect:/supporter";
			}
		}
		String decodedToUTF8;
		try {
			decodedToUTF8 = new String(complaintInfo.getResponseText().getBytes("ISO-8859-1"), "UTF-8");
			complaintInfo.setResponseText(decodedToUTF8);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Complaint name cannot converted.");
			e.printStackTrace();
		}
		System.out.println("detay: "+complaintInfo.getId()+" "+complaintInfo.getSupportUserId()+" "+complaintInfo.getResponseText());
		if (file.isEmpty()) {
			this.complaintDAO.endComplaint(complaintInfo.getId(),complaintInfo.getResponseText(),complaintInfo.getResponseFile());
			
            System.out.println("file empty!");
            return "redirect:/reportedComplaints";
        }
//		Byte[] bytes2 = new Byte[file.getBytes().length];
//		byte[] bytes;
		else {
			try {
	
	            // Get the file and save it somewhere
	        	byte[] bytes = file.getBytes();
	        	this.complaintDAO.endComplaint(complaintInfo.getId(),complaintInfo.getResponseText(),bytes);
	    		
	//            int i=0;    
	            // Associating Byte array values with bytes. (byte[] to Byte[])
	//            for(byte b: bytes)
	//               bytes2[i++] = b;  // Autoboxing.
	//            Path path = Paths.get("D://temp//" + file.getOriginalFilename());
	//            Files.write(path, bytes);
	            redirectAttributes.addFlashAttribute("message",
	                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
	            System.out.println("kaydettim");
	            
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsgSuccess", "Şikayet kapatıldı.");

//		return "redirect:/deptList";
		if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("MANAGER")) {
			return "redirect:/reportedComplaints";
		}
		else {
			return "redirect:/supporter";
		}
	}
	
	@RequestMapping(value = "/transferComplaint", method = RequestMethod.GET)
	public String transferComplaint(Model model,Principal principal, @RequestParam("id") Integer id) {
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		model.addAttribute("userInfo", user);
		model.addAttribute("comp", this.complaintDAO.findComplaintInfo(id));
		List<LocationInfo> list = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", list);
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		return "transferComplaint";
	}
	
	@RequestMapping(value = "/getImageC", method = RequestMethod.GET)
	public String getImageC(Model model,Principal principal, @RequestParam("id") Integer id) {
		ComplaintInfo comp = complaintDAO.findComplaintInfo(id);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buf = comp.getComplaintFile();
	    byte[] bytes = baos.toByteArray();
	    System.out.println("bytes" +bytes);
	    byte[] encodeBase64 = Base64.getEncoder().encode(buf);
	    try {
			String base64Encoded = new String(encodeBase64, "UTF-8");
			model.addAttribute("image", base64Encoded);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "image";
	}
	
	@RequestMapping(value = "/getImageR", method = RequestMethod.GET)
	public String getImageR(Model model,Principal principal, @RequestParam("id") Integer id) {
		ComplaintInfo comp = complaintDAO.findComplaintInfo(id);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buf = comp.getResponseFile();
	    byte[] bytes = baos.toByteArray();
	    System.out.println("bytes" +bytes);
	    byte[] encodeBase64 = Base64.getEncoder().encode(buf);
	    try {
			String base64Encoded = new String(encodeBase64, "UTF-8");
			model.addAttribute("image", base64Encoded);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "image";
	}
	
	@RequestMapping(value = "/savedComplaint", method = RequestMethod.GET)
	public String savedComplaint(Model model,Principal principal, @RequestParam("id") Integer id) {
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		ComplaintInfo comp = complaintDAO.findComplaintInfo(id);
		comp.setLocationInfo(locationDAO.findLocationInfo(comp.getLocationId()));
		comp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(comp.getSupportTypeId()));
		model.addAttribute("userInfo", user);
		model.addAttribute("comp", comp);
		List<LocationInfo> list = locationDAO.listLocationInfos();
		model.addAttribute("locationInfos", list);
		List<SupportTypeInfo> list2 = supportTypeDAO.listSupportTypeInfos();
		model.addAttribute("supportTypeInfos", list2);
		return "savedComplaint";
	}
	
	@RequestMapping(value = "/assignComplaint", method = RequestMethod.GET)
	public String assignComplaint(Model model,Principal principal, @RequestParam("id") Integer id) {
		UserInfo user = userDAO.findLoginUserInfo(principal.getName());
		model.addAttribute("userInfo", user);
		ComplaintInfo comp = this.complaintDAO.findComplaintInfo(id);
		comp.setLocationInfo(locationDAO.findLocationInfo(comp.getLocationId()));
		comp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(comp.getSupportTypeId()));
		model.addAttribute("comp", comp);
		List<UserInfo> list3 = userDAO.listUserInfosForAssignment(id);
		model.addAttribute("asgUser", list3);
		return "assignComplaint";
	}
	
	@RequestMapping(value="/getSupportComplaints",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getSupportComplaints(@RequestParam Integer id) {
		String res = "";
		List<ComplaintInfo> list4 = this.complaintDAO.listComplaintInfosForSupport(id);
		list4.addAll(complaintDAO.listComplaintInfosForSupportAck(id));
		for (ComplaintInfo tmp : list4) {
			tmp.setLocationInfo(locationDAO.findLocationInfo(tmp.getLocationId()));
			tmp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(tmp.getSupportTypeId()));
			tmp.setComplainantUserInfo(userDAO.findUserInfo(tmp.getSupportUserId()));
		}
		for (ComplaintInfo tmp : list4) {
			if (tmp.getComplaintTime() == null ) {
				res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getComplainantUserInfo().getUsername()+"</td><td>"+"Atama yapılmamış."+"</td><td>"+tmp.getComplaintText()+"</td><td>"+userDAO.findUserInfo(tmp.getComplainantUserId()).getUsername()+"</td><td>"+tmp.getResponseTime()+"</td><td>"+tmp.getResponseText()+"</td><td>"+tmp.getAckTime()+"</td><td>"+tmp.getAssignTime()+"</td><td>"+tmp.isEnded()+"</td><td>"+tmp.isAck()+"</td><td>"+tmp.isReported()+"</td><td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-success btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
			}
			else {
				res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getComplainantUserInfo().getUsername()+"</td><td>"+tmp.getComplaintTime()+"</td><td>"+tmp.getComplaintText()+"</td><td>"+userDAO.findUserInfo(tmp.getComplainantUserId()).getUsername()+"</td><td>"+tmp.getResponseTime()+"</td><td>"+tmp.getResponseText()+"</td><td>"+tmp.getAckTime()+"</td><td>"+tmp.getAssignTime()+"</td><td>"+tmp.isEnded()+"</td><td>"+tmp.isAck()+"</td><td>"+tmp.isReported()+"</td><td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-success btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
			}
		}
		return res;
	}
	
	@RequestMapping(value="/getSupportAllComplaints",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getSupportAllComplaints(@RequestParam Integer id) {
		String res = "";
		List<ComplaintInfo> list4 = this.complaintDAO.listComplaintInfosByUserId(id);
		for (ComplaintInfo tmp : list4) {
			tmp.setLocationInfo(locationDAO.findLocationInfo(tmp.getLocationId()));
			tmp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(tmp.getSupportTypeId()));
			tmp.setComplainantUserInfo(userDAO.findUserInfo(tmp.getSupportUserId()));
		}
		for (ComplaintInfo tmp : list4) {
			res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td><td>"+tmp.getComplainantUserInfo().getUsername()+"</td>");
			if (tmp.getComplaintTime() == null ) {
				res = res.concat("<td>"+"Atama yapılmamış</td>");
			}
			else{
				res = res.concat("<td>"+tmp.getComplaintTime()+"</td>");
			}
			res = res.concat("<td>"+tmp.getComplaintText()+"</td><td>"+userDAO.findUserInfo(tmp.getComplainantUserId()).getUsername()+"</td><td>"+tmp.getResponseTime()+"</td><td>"+tmp.getResponseText()+"</td><td>"+tmp.getAckTime()+"</td><td>"+tmp.getAssignTime()+"</td>");
			
			if(tmp.isEnded()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			if(tmp.isAck()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			if(tmp.isReported()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			res = res.concat("<td><a href=\"listCompProcess?id="+tmp.getId()+"\" class=\"btn btn-success btn-xs\"><span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
		}
		return res;
	}
	
	@RequestMapping(value="/getComplaintList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getComplaintList(@RequestParam(required = false) Integer id, @RequestParam(required = false) Integer id2 ) {
		String res = "";
		List<ComplaintInfo> list4 = null;
		if (id == 0 && id2 != 0) {
			list4 = this.complaintDAO.listComplaintInfos(null, id2,null,null);
		}
		else if (id != 0 && id2 == 0){
			list4 = this.complaintDAO.listComplaintInfos(id, null,null,null);
		}
		else if ((id == 0 && id2 == 0)){
			list4 = this.complaintDAO.listComplaintInfos(null, null,null,null);
		}
		else {
			list4 = this.complaintDAO.listComplaintInfos(id, id2,null,null);
		}
		if (list4.isEmpty() || list4 == null ) {
			System.out.println("Liste boş döndü");
			return res;
		}
		for (ComplaintInfo tmp : list4) {
			tmp.setLocationInfo(locationDAO.findLocationInfo(tmp.getLocationId()));
			tmp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(tmp.getSupportTypeId()));
			tmp.setComplainantUserInfo(userDAO.findUserInfo(tmp.getSupportUserId()));
		}
		for (ComplaintInfo tmp : list4) {
			
			res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td>");
			
			if (tmp.getSupportUserId() == null) {	
				res = res.concat("<td>Atama yapılmamış."+"</td>");
			}else{
				res = res.concat("<td>"+tmp.getComplainantUserInfo().getUsername()+"</td>");
			}
			
			res = res.concat("<td>"+tmp.getComplaintTime()+"</td><td>"+tmp.getComplaintText()+"</td><td>"+userDAO.findUserInfo(tmp.getComplainantUserId()).getUsername()+"</td><td>"+tmp.getResponseTime()+"</td><td>"+tmp.getResponseText()+"</td><td>"+tmp.getAckTime()+"</td><td>"+tmp.getAssignTime()+"</td>");
			
			if(tmp.isEnded()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			if(tmp.isAck()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			if(tmp.isReported()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			res = res.concat("<td><a href=\"listCompProcess?id="+tmp.getId()+"\" ");
			
			if (tmp.getSupportUserId() == null) {	
				res = res.concat(" class=\"btn btn-danger btn-xs\">");
			}else{
				res = res.concat(" class=\"btn btn-success btn-xs\">");
			}
			
			res = res.concat("<span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
		}
		return res;
	}
	
	@RequestMapping(value="/getComplexComplaintList",method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getComplexComplaintList(@RequestParam(required = false) Integer id, @RequestParam(required = false) Integer id2, @RequestParam(required = false) Integer id3, @RequestParam(required = false) Integer id4, @RequestParam(required = false) Integer id5 ) {
		String res = "";
		List<ComplaintInfo> list4 = null;
		if (id == 0) {
			id = null;
		}
		if (id2 == 0) {
			id2 = null;
		}
		if (id3 == 0) {
			id3 = null;
		}
		if (id4 == 0) {
			id4 = null;
		}
		if (id5 == 1) {
			list4 = this.complaintDAO.listComplaintInfos(id, id2,id3,id4);
		}
		else if (id5 == 2){
			list4 = this.complaintDAO.listWaitingAckComplaintInfos(id, id2,id3,id4);
		}
		else if (id5 == 3){
			list4 = this.complaintDAO.listWaitingAssingnComplaintInfos(id, id2,id3,id4);
		}
		else if (id5 == 4) {
			list4 = this.complaintDAO.listWaitingChildComplaintInfos(id, id2, id3, id4);
		}
		else if (id5 == 5) {
			list4 = this.complaintDAO.listActiveComplaintInfos(id, id2, id3, id4);
		}
		else if (id5 == 6) {
			list4 = this.complaintDAO.listEndedComplaintInfos(id, id2, id3, id4);
		}
		else if (id5 == 7) {
			list4 = this.complaintDAO.listReportedComplaintInfos(id, id2, id3, id4);
		}
		if (list4.isEmpty() || list4 == null ) {
			System.out.println("Liste boş döndü");
			return res;
		}
		for (ComplaintInfo tmp : list4) {
			tmp.setLocationInfo(locationDAO.findLocationInfo(tmp.getLocationId()));
			tmp.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(tmp.getSupportTypeId()));
			tmp.setComplainantUserInfo(userDAO.findUserInfo(tmp.getSupportUserId()));
		}
		for (ComplaintInfo tmp : list4) {
			
			res = res.concat("<tr><td>"+tmp.getId()+"</td><td>"+tmp.getLocationInfo().getDescription()+"</td><td>"+tmp.getSupportTypeInfo().getType()+"</td>");
			
			if (tmp.getSupportUserId() == null) {	
				res = res.concat("<td>Atama yapılmamış."+"</td>");
			}else{
				res = res.concat("<td>"+tmp.getComplainantUserInfo().getUsername()+"</td>");
			}
			
			res = res.concat("<td>"+tmp.getComplaintTime()+"</td><td>"+tmp.getComplaintText()+"</td><td>"+userDAO.findUserInfo(tmp.getComplainantUserId()).getUsername()+"</td><td>"+tmp.getResponseTime()+"</td><td>"+tmp.getResponseText()+"</td><td>"+tmp.getAckTime()+"</td><td>"+tmp.getAssignTime()+"</td>");
			
			if(tmp.isEnded()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			if(tmp.isAck()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			if(tmp.isReported()){
				res = res.concat("<td><p style=\"color:green;\"><span class=\"glyphicon glyphicon-ok\"></span></p></td>");
			}else{
				res = res.concat("<td><p style=\"color:red;\"><span class=\"glyphicon glyphicon-remove\"></span></p></td>");
			}
			
			res = res.concat("<td><a href=\"listCompProcess?id="+tmp.getId()+"\" ");
			
			if (tmp.getSupportUserId() == null) {	
				res = res.concat(" class=\"btn btn-danger btn-xs\">");
			}else{
				res = res.concat(" class=\"btn btn-success btn-xs\">");
			}
			
			res = res.concat("<span class=\"glyphicon glyphicon-plus\"></span> Şikayet geçmişi</a></td></tr>");
		}
		return res;
	}
	
	@RequestMapping(value = "/listCompProcess", method = RequestMethod.GET)
	public String listCompProcess(Model model,Principal principal, @RequestParam("id") Integer id) {
		ComplaintInfo main = this.complaintDAO.findComplaintInfo(id);
		List<ComplaintInfo> tree = this.complaintDAO.listComplaintProcess(id);
		for (ComplaintInfo t : tree) {
			t.setLocationInfo(locationDAO.findLocationInfo(t.getLocationId()));
			t.setSupportTypeInfo(supportTypeDAO.findSupportTypeInfo(t.getSupportTypeId()));
			t.setComplainantUserInfo(userDAO.findUserInfo(t.getComplainantUserId()));
			t.setSupportUserInfo(userDAO.findUserInfo(t.getSupportUserId()));
		}
		model.addAttribute("tree", tree);
		model.addAttribute("main", main);
		return "listCompProcess";
	}
	
	@RequestMapping(value = "/assignedComplaint", method = RequestMethod.POST)
	public String assignedComplaint(Model model,  //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("compMsgSuccess", "Hatalı giriş");
			System.out.println("Hata!");
			return "redirect:/assignComplaints";
		}
		
		this.complaintDAO.assingComplaint(complaintInfo.getId(), complaintInfo.getSupportUserId());

		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsgSuccess", "Şikayet atandı.");

//		return "redirect:/deptList";
		return "redirect:/assignComplaints";
	}
	
	@RequestMapping(value = "/transferedComplaint", method = RequestMethod.POST)
	public String transferedComplaint(Model model, Principal principal, //
			@ModelAttribute("complaintForm") @Validated ComplaintInfo complaintInfo, //
			@RequestParam("file2") MultipartFile file,BindingResult result, //
			final RedirectAttributes redirectAttributes) {
			
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("compMsgError", "Hatalı giriş");
			System.out.println("Hata!");
			if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("MANAGER")) {
				return "redirect:/reportedComplaints";
			}
			else {
				return "redirect:/supporter";
			}
		}
		String decodedToUTF8;
		String decodedToUTF82;
		try {
			decodedToUTF8 = new String(complaintInfo.getResponseText().getBytes("ISO-8859-1"), "UTF-8");
			decodedToUTF82 = new String(complaintInfo.getComplaintText().getBytes("ISO-8859-1"), "UTF-8");
			complaintInfo.setResponseText(decodedToUTF8);
			complaintInfo.setComplaintText(decodedToUTF82);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Complaint name cannot converted.");
			e.printStackTrace();
		}
		if (file.isEmpty()) {
			this.complaintDAO.transferComplaint(complaintInfo.getId(),complaintInfo.getResponseText(),complaintInfo.getLocationId(),complaintInfo.getSupportTypeId(),complaintInfo.getComplaintText(),complaintInfo.isEnded(),complaintInfo.getComplaintFile(),complaintInfo.getResponseFile());

            System.out.println("file empty!");
            if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("MANAGER")) {
    			return "redirect:/reportedComplaints";
    		}
    		else {
    			return "redirect:/supporter";
    		}
        }
//		Byte[] bytes2 = new Byte[file.getBytes().length];
//		byte[] bytes;
		else {
			try {
	
	            // Get the file and save it somewhere
	        	byte[] bytes = file.getBytes();
	        	this.complaintDAO.transferComplaint(complaintInfo.getId(),complaintInfo.getResponseText(),complaintInfo.getLocationId(),complaintInfo.getSupportTypeId(),complaintInfo.getComplaintText(),complaintInfo.isEnded(),complaintInfo.getComplaintFile(),bytes);

	//            int i=0;    
	            // Associating Byte array values with bytes. (byte[] to Byte[])
	//            for(byte b: bytes)
	//               bytes2[i++] = b;  // Autoboxing.
	//            Path path = Paths.get("D://temp//" + file.getOriginalFilename());
	//            Files.write(path, bytes);
	            redirectAttributes.addFlashAttribute("message",
	                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
	            System.out.println("kaydettim");
	            
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		System.out.println("detay: "+complaintInfo.getId()+" "+complaintInfo.getLocationId()+" "+complaintInfo.getSupportTypeId()+" "+complaintInfo.getSupportUserId()+" "+complaintInfo.getComplaintText()+" "+complaintInfo.getResponseText()+" "+complaintInfo.isEnded());
		
		// Important!!: Need @EnableWebMvc
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("compMsgSuccess", "Şikayet yönlendirildi.");

//		return "redirect:/deptList";
		if (userRoleDAO.getUserRoles(userDAO.findLoginUserInfo(principal.getName()).getId()).contains("MANAGER")) {
			return "redirect:/reportedComplaints";
		}
		else {
			return "redirect:/supporter";
		}
	}
	
	
}
