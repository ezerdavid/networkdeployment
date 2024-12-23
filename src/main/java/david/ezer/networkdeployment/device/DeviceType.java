package david.ezer.networkdeployment.device;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeviceType {
    GATEWAY("Gateway"), SWITCH("Switch"), ACCESS_POINT("Access Point");

    DeviceType(String value) {
        this.value = value;
    }

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static DeviceType fromText(String value) {
        return switch(value) {
            case "Gateway" -> GATEWAY;
            case "Switch" -> SWITCH;
            case "Access Point" -> ACCESS_POINT;
            default -> throw new IllegalArgumentException("Unknown device type: " + value);
        };
    }
}

