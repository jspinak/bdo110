package com.bdo.automation.logging;

import io.github.jspinak.brobot.action.ActionResult;
import io.github.jspinak.brobot.action.ObjectCollection;
import io.github.jspinak.brobot.model.match.Match;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Formats action logs in a concise, consistent manner for all Brobot actions.
 * Provides one-line summaries with essential information only.
 */
@Slf4j
@UtilityClass
public class ActionLogFormatter {

    /**
     * Log a completed action with its results
     */
    public static void logAction(String actionType, ActionResult result, ObjectCollection... collections) {
        if (result == null) return;

        String target = getTargetDescription(collections);
        String location = getLocationString(result);
        double similarity = getHighestSimilarity(result);
        long duration = result.getDuration() != null ? result.getDuration().toMillis() : 0;

        if (result.isSuccess()) {
            log.info("✓ {} {} | loc:{} | sim:{:.2f} | {}ms",
                actionType, target, location, similarity, duration);
        } else {
            log.warn("✗ {} {} | NOT FOUND | {}ms",
                actionType, target, duration);
        }
    }

    /**
     * Log an action attempt before execution
     */
    public static void logAttempt(String actionType, ObjectCollection... collections) {
        String target = getTargetDescription(collections);
        log.debug("→ {} {}", actionType, target);
    }

    private static String getTargetDescription(ObjectCollection... collections) {
        if (collections == null || collections.length == 0) return "[no target]";

        ObjectCollection first = collections[0];
        if (!first.getStateImages().isEmpty()) {
            return first.getStateImages().get(0).getName();
        } else if (!first.getLocations().isEmpty()) {
            return first.getLocations().get(0).toString();
        } else if (!first.getRegions().isEmpty()) {
            return "region:" + first.getRegions().get(0).toString();
        }
        return "[unknown]";
    }

    private static String getLocationString(ActionResult result) {
        if (result.getMatchList().isEmpty()) return "none";

        Match firstMatch = result.getMatchList().get(0);
        return String.format("(%d,%d)",
            firstMatch.getTarget().x(),
            firstMatch.getTarget().y());
    }

    private static double getHighestSimilarity(ActionResult result) {
        return result.getMatchList().stream()
            .mapToDouble(Match::getScore)
            .max()
            .orElse(0.0);
    }
}