package brooks.api.utility.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	public LoggingInterceptor(){}
	
	public Object runInterceptorLogic(ProceedingJoinPoint pjp) throws Throwable
	{
	    String packageName = pjp.getSignature().getDeclaringTypeName();
	    String methodName = pjp.getSignature().getName();
		System.out.println("Entering: " + packageName + "." + methodName);
		logger.info("[INFO] Entering: " + packageName + "." + methodName);
		Object output = pjp.proceed();
		System.out.println("Exiting: " + packageName + "." + methodName);
		logger.error("[INFO] Exiting: " + packageName + "." + methodName);
		return output;
	}
}
