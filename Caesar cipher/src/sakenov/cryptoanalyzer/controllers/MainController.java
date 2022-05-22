package sakenov.cryptoanalyzer.controllers;

import sakenov.cryptoanalyzer.commands.Action;
import sakenov.cryptoanalyzer.entity.Result;

public class MainController {

    public Result doAction(String actionName, String[] parameters) {
        Action action = Activity.find(actionName);
        return action.run(parameters);
    }
}
