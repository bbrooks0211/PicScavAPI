package brooks.api.controllers;


import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import brooks.api.data.GameDAO;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameModel;
import brooks.api.utility.TimeUtility;

/**
 * For testing purposes only. Will likely never be removed, as it helps in debugging issues with cloud deployment
 * @author Brendan Brooks
 *
 */
@Controller
@RequestMapping("/test")
public class TestController 
{
	private DataAccessInterface<GameModel> dao;
	/**
	 * Simple Hello World Controller that returns a String in the response body
	 * Invoke using /test1 URI.
	 */
	@RequestMapping(path="/test", method=RequestMethod.GET)		//OR could use @GetMapping("test1")
	@ResponseBody
	public String printHello()
	{
		//return a string in the response body (must use @ResponseBody annotation)
		return "Hello World!";
	}
	
	@Autowired
	private void setGameDAO(DataAccessInterface<GameModel> dao) {
		this.dao = dao;
	}
}

/*
Copyright 2019, Brendan Brooks.  

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