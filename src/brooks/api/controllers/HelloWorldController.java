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
@RequestMapping("/hello")
public class HelloWorldController 
{
	private DataAccessInterface<GameModel> dao;
	/**
	 * Simple Hello World Controller that returns a String in the response body
	 * Invoke using /test1 URI.
	 */
	@RequestMapping(path="/test1", method=RequestMethod.GET)		//OR could use @GetMapping("test1")
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
