package brooks.api.utility.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingInterceptor {
	public LoggingInterceptor()
	{}
	
	public Object runInterceptorLogic(ProceedingJoinPoint pjp) throws Throwable
	{
	    String packageName = pjp.getSignature().getDeclaringTypeName();
	    String methodName = pjp.getSignature().getName();
		System.out.println("Entering: " + packageName + "." + methodName);
		Object output = pjp.proceed();
		System.out.println("Exiting: " + packageName + "." + methodName);
		return output;
	}
}
