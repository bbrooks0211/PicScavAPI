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
