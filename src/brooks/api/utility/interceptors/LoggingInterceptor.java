package brooks.api.utility.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Interceptor using Spring AOP to log all entry and exit within methods throughout the application
 * @author Brendan Brooks
 *
 */
@Aspect
public class LoggingInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	public LoggingInterceptor(){}
	
	/**
	 * Interceptor for logging entry and exit into any method
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object runInterceptorLogic(ProceedingJoinPoint pjp) throws Throwable
	{
		//Get the package info and the method info
	    String packageName = pjp.getSignature().getDeclaringTypeName();
	    String methodName = pjp.getSignature().getName();
	    
	    //Log the entry into the method
		logger.info("[INFO] Entering: " + packageName + "." + methodName);
		
		//Complete execution of the method
		Object output = pjp.proceed();
		
		//Log exiting the method
		logger.info("[INFO] Exiting: " + packageName + "." + methodName);
		
		return output;
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