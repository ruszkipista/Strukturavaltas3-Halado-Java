package sportresults.model;

import lombok.Getter;

@Getter
public enum SportType {

    SWIMMING('s'),
    RUNNING('s'),
    POLE_VAULT('m'),
    HAMMER_THROWING('m');

    private final char measureUnit;

    SportType(char measureUnit) {
        this.measureUnit = measureUnit;
    }
}
