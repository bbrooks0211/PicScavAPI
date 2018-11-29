package brooks.api.controllers;



import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
		//return a string in the response body (must use @ResponseBody annotation)
		return "Hello World!";
	}
}
