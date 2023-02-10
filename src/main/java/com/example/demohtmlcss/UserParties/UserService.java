package com.example.demohtmlcss.UserParties;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demohtmlcss.controller.Employee;

@Service
public class UserService {
	
	@Autowired
	private UserRepository up;
	
	public String insertUser(User u) {
		up.save(u);
		return "User added Successfully with Id: "+u.getId() ;
	}
	
	public String encodePassword(String a) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(a);
		return encodedPassword;
	}
	
	public List<User> getall() {
		
		
		List<User> list = up.findAll();
		if (list.size()>0) {
		return list;
		}
		else {
			return new ArrayList<User>();
		}
	}
	
	public List<User> findbymobile(String keyword){
    	return up.findByMobile(keyword);
    }
	
	public String deleteUser(Integer id) {
		up.deleteById(id);
		return "user deleted";
	}
	
	public Optional<User> getOne(Integer id){
        return up.findById(id);
    }
	
	public Integer getnoofusers() {
		return up.getnoofusers();
	}
	
	public Integer getnoofadmins() {
		return up.getnoofadmins();
	}
	
	public Integer getnoofcreater() {
		return up.getnoofcreaters();
	}
}
