package brooks.api.controllers;

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
	
	@RequestMapping(path= { "/", "/main" }, method=RequestMethod.GET)
	public ModelAndView displayAdminPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin");
		mv.addObject("item", new ItemModel());
		return mv;
	}
	
	@RequestMapping(path="/addItem", method=RequestMethod.POST)
	public ModelAndView loginUser(@ModelAttribute("item")ItemModel item) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect: main");
		item.setCreatorID(1);
		boolean status = false;
		try {
			status = itemService.addItem(item);
		} catch (ItemAlreadyExistsException e) {
			// TODO Auto-generated catch block
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
		
		return mv;
	}
	
	@Autowired
	public void setItemService(ItemReferenceBusinessServiceInterface service) {
		this.itemService = service;
	}
}
