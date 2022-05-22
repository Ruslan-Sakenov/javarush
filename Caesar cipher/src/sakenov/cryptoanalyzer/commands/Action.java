package sakenov.cryptoanalyzer.commands;

import sakenov.cryptoanalyzer.entity.Result;

public interface Action {
    Result run(String[] parameters);
}

