package com.bdo.automation.logging;

import io.github.jspinak.brobot.action.ActionResult;
import io.github.jspinak.brobot.action.ObjectCollection;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP aspect that intercepts all Brobot action executions to provide
 * consistent, concise logging across all action types.
 */
@Aspect
@Component
@Slf4j
public class ActionLoggingAspect {

    /**
     * Intercept Action.perform() method calls to add consistent logging
     */
    @Around("execution(* io.github.jspinak.brobot.action.Action.perform(..)) && args(actionType, objectCollections)")
    public ActionResult logActionPerform(ProceedingJoinPoint joinPoint,
                                        Object actionType,
                                        ObjectCollection... objectCollections) throws Throwable {

        String action = actionType.toString();

        // Log the attempt
        ActionLogFormatter.logAttempt(action, objectCollections);

        // Execute the action
        ActionResult result = (ActionResult) joinPoint.proceed();

        // Log the result
        ActionLogFormatter.logAction(action, result, objectCollections);

        return result;
    }

    /**
     * Intercept direct action method calls (find, click, type) for consistent logging
     */
    @Around("execution(* io.github.jspinak.brobot.action.Action.find(..)) || " +
            "execution(* io.github.jspinak.brobot.action.Action.click(..)) || " +
            "execution(* io.github.jspinak.brobot.action.Action.type(..))")
    public ActionResult logDirectActionMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName().toUpperCase();
        Object[] args = joinPoint.getArgs();

        // Extract ObjectCollections from args
        ObjectCollection collection = null;
        if (args.length > 0 && args[0] instanceof ObjectCollection) {
            collection = (ObjectCollection) args[0];
        }

        // Log attempt
        if (collection != null) {
            ActionLogFormatter.logAttempt(methodName, collection);
        }

        // Execute
        ActionResult result = (ActionResult) joinPoint.proceed();

        // Log result
        if (collection != null) {
            ActionLogFormatter.logAction(methodName, result, collection);
        }

        return result;
    }
}