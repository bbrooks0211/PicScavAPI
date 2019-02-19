package brooks.api.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import brooks.api.business.interfaces.ItemReferenceBusinessServiceInterface;
import brooks.api.models.ItemModel;
import brooks.api.utility.exceptions.ItemAlreadyExistsException;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	ItemReferenceBusinessServiceInterface itemService;
	
	private int token = 123456789;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping(path= { "/", "/main" }, method=RequestMethod.GET)
	public ModelAndView displayAdminPage() {
		ModelAndView mv = new ModelAndView();
		httpSession.removeAttribute("token");
		httpSession.setAttribute("token", token);
		mv.setViewName("admin");
		mv.addObject("item", new ItemModel());
		return mv;
	}
	
	@RequestMapping(path="/addItem", method=RequestMethod.POST)
	public ModelAndView loginUser(@ModelAttribute("item")ItemModel item) {
		ModelAndView mv = new ModelAndView();
		if(!tokenIsValid(true)) {
			mv.setViewName("error");
			return mv;
		}

		mv.setViewName("redirect: main");
		item.setCreatorID(1);
		boolean status = false;
		try {
			status = itemService.addItem(item);
		} catch (ItemAlreadyExistsException e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@RequestMapping(path="/login", method=RequestMethod.GET)
	public ModelAndView displayLogin() {
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}
	
	@RequestMapping(path="/sendLoginRequest", method=RequestMethod.POST)
	public ModelAndView loginUser() {
		ModelAndView mv = new ModelAndView();
		if(!tokenIsValid(true)) {
			mv.setViewName("error");
			return mv;
		}
		return mv;
	}
	
	private boolean tokenIsValid(boolean deleteTokenAfter) {
		int retrievedToken = 0;
		if (httpSession.getAttribute("token") != null) {
			System.out.println("NOT NULL");
			retrievedToken = (int) httpSession.getAttribute("token");	
		}
		if (retrievedToken != token)
			return false;	
		if (deleteTokenAfter)
			httpSession.removeAttribute("token");
		System.out.println("token is valid: " + token);
		return true;
	} 
	
	@Autowired
	public void setItemService(ItemReferenceBusinessServiceInterface service) {
		this.itemService = service;
	}
}
