package brooks.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	public ModelAndView loginUser(@Valid @ModelAttribute("item")ItemModel item, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect: main");
		item.setCreatorID(1);
		boolean status = false;
		if(result.hasErrors())
		{
			mv.setViewName("admin");
			mv.addObject("item", item);
			return mv;
		}
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

/*
This file is part of PicScav.

PicScav is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PicScav is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PicScav.  If not, see <https://www.gnu.org/licenses/>.
*/