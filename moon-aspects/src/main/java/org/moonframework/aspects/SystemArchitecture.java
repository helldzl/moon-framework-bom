package org.moonframework.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <p>When working with enterprise applications, you often want to refer to modules of the application and particular sets of operations from within several aspects. We recommend defining a "SystemArchitecture" aspect that captures common pointcut expressions for this purpose. A typical such aspect would look as follows:</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/5
 */
@Aspect
@Component
public interface SystemArchitecture {
}
