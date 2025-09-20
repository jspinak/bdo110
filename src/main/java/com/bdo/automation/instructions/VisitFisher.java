package com.bdo.automation.instructions;

import com.bdo.automation.states.MainScreenState;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.ActionResult;
import io.github.jspinak.brobot.action.ActionType;
import io.github.jspinak.brobot.model.element.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisitFisher {

    private final Action action;
    private final MainScreenState mainScreenState;

    public void execute() {
        ActionResult actionResult = action.perform(ActionType.FIND, mainScreenState.getFisher());
        if (actionResult.getBestMatch().isPresent() && actionResult.getBestLocation().isPresent()) {
            Region foundRegion = actionResult.getBestMatch().get().getRegion();
            action.perform(ActionType.HIGHLIGHT, foundRegion);
            action.click(actionResult.getBestLocation().get());
            action.perform(ActionType.HIGHLIGHT, foundRegion);
            action.type(mainScreenState.getGo());
            action.perform(ActionType.HIGHLIGHT, foundRegion);
        }
    }

}
