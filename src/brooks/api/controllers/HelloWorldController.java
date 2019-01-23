package brooks.api.controllers;



import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import brooks.api.data.GameDAO;
import brooks.api.models.GameModel;

//For testing purposes only
@Controller
@RequestMapping("/hello")
public class HelloWorldController 
{
	/**
	 * Simple Hello World Controller that returns a String in the response body
	 * Invoke using /test1 URI.
	 * 
	 * @return Hello World test string
	 */
	@RequestMapping(path="/test1", method=RequestMethod.GET)		//OR could use @GetMapping("test1")
	@ResponseBody
	public String printHello()
	{
		GameDAO d = new GameDAO();
		GameModel g = new GameModel();
		g.setHostID(1);
		g.setHostUsername("test");
		g.setLobbyName("TEST LOBBY");
		g.setCategory("tech");
		g.setTimeLimit(3);
		Date da = new Date();
		g.setStartTime(new Timestamp(da.getTime()));
		g.setEndTime(new Timestamp(da.getTime() + 3));
		d.create(g);
		//return a string in the response body (must use @ResponseBody annotation)
		return "Hello World!";
	}
}
