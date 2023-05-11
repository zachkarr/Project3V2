package disburse.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@Aspect
public class LoggerAspect {
    private Logger logger = Logger.getLogger(getClass());
    //after execution of a method starting with a name gethighestamount that returns any type
    @After("execution (* getHighestAmount*())")
    public void logGetHighestAmount(JoinPoint jp) throws AccessDeniedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
			throw new AccessDeniedException("You cannot access this method");
		}
    	logger.info("Before advice implementation - " + jp.getTarget().getClass() + " - Executing before " +
                     jp.getSignature().getName() + "() method");
    	System.out.println("Executing Before");
    	
    }
    
    @Before("execution(@org.springframework.web.bind.annotation.GetMapping * *.*(..)) || "
    		+ "execution(@org.springframework.web.bind.annotation.PostMapping * *.*(..)) || "
    		+ "execution(@org.springframework.web.bind.annotation.RequestMapping * *.*(..))")
    public void logAllRESTattempts(JoinPoint joinPoint) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	String username;
    	if(principal instanceof UserDetails) {
    		username = ((UserDetails)principal).getUsername();
    	} else {
    		username = principal.toString();
    	}
    	logger.info(username + " accessing - " + joinPoint.getTarget().getClass() + 
    			" ; Executing " + joinPoint.getSignature().getName() + "() method");
    }
        
}
