package com.bdo.automation.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages action sessions for correlated logging.
 * Uses SLF4J MDC to add session and sequence information to all logs.
 */
@Slf4j
@Component
public class ActionSession {

    private static final String SESSION_KEY = "session";
    private static final String SEQUENCE_KEY = "seq";
    private static final String TASK_KEY = "task";

    private final AtomicInteger sequenceCounter = new AtomicInteger(0);
    private String currentSession;

    /**
     * Start a new action session with a descriptive name
     */
    public void startSession(String taskName) {
        currentSession = UUID.randomUUID().toString().substring(0, 8);
        sequenceCounter.set(0);

        MDC.put(SESSION_KEY, currentSession);
        MDC.put(TASK_KEY, taskName);

        log.info("=== Starting Task: {} | Session: {} ===", taskName, currentSession);
    }

    /**
     * Increment and set the sequence number for the next action
     */
    public void nextAction() {
        int seq = sequenceCounter.incrementAndGet();
        MDC.put(SEQUENCE_KEY, String.format("%03d", seq));
    }

    /**
     * End the current session
     */
    public void endSession() {
        String task = MDC.get(TASK_KEY);
        log.info("=== Completed Task: {} | Session: {} | Total Actions: {} ===",
            task, currentSession, sequenceCounter.get());

        MDC.clear();
        currentSession = null;
    }

    /**
     * Execute a task with automatic session management
     */
    public void executeWithSession(String taskName, Runnable task) {
        try {
            startSession(taskName);
            task.run();
        } finally {
            endSession();
        }
    }
}