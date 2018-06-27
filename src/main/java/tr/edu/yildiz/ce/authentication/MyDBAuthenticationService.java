package tr.edu.yildiz.ce.authentication;
 
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tr.edu.yildiz.ce.dao.BanDAO;
import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.model.UserInfo;
 
@Service
public class MyDBAuthenticationService implements UserDetailsService {
	
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private BanDAO banDAO;
    
    @Autowired
    private UserRoleDAO userRoleDAO;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = this.userDAO.findLoginUserInfo(username);
        System.out.println("UserInfo= " + userInfo);
        
        if (userInfo == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
         
        // [USER,ADMIN,..]
        List<String> roles= userRoleDAO.getUserRoles(userInfo.getId());
         
        List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
        if(roles!= null)  {
            for(String role: roles)  {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                grantList.add(authority);
                System.out.println("Yetkiler: "+role);
            }
        }
        
   /*     UserDetails userDetails = (UserDetails) new User(userInfo.getUsername(), //
                userInfo.getPassword(),grantList);
        if (!userInfo.isEnabled()) {
	        System.out.println("Kullanıcı yasaklı!!!");
	        return null;
        }*/
        UserDetails userDetails = buildUserForAuthentication(userInfo, grantList);
        return userDetails;
    }
    
    private User buildUserForAuthentication(UserInfo userInfo,List<GrantedAuthority> authorities) {
    	if (!userInfo.isEnabled()) {
    		userInfo.setEnabled(!banDAO.isBanned(userInfo.getId()));
    		userDAO.saveUser(userInfo);
    	}
    	return new User(userInfo.getUsername(), userInfo.getPassword(),
				userInfo.isEnabled(), true, true, true, authorities);
    }
     
}