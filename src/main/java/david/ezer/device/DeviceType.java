package david.ezer.device;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeviceType {
  GATEWAY(DeviceType.GATEWAY_VALUE),
  SWITCH(DeviceType.SWITCH_VALUE),
  ACCESS_POINT(DeviceType.ACCESS_POINT_VALUE),;

  public static final String GATEWAY_VALUE = "Gateway";
  public static final String SWITCH_VALUE = "Switch";
  public static final String ACCESS_POINT_VALUE = "Access Point";


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
    return switch (value) {
      case DeviceType.GATEWAY_VALUE -> GATEWAY;
      case DeviceType.SWITCH_VALUE -> SWITCH;
      case DeviceType.ACCESS_POINT_VALUE -> ACCESS_POINT;
      default -> throw new UnknownDeviceTypeException(value);
    };
  }

  public int orderNumber() {
    return switch (value) {
      case DeviceType.GATEWAY_VALUE -> 1;
      case DeviceType.SWITCH_VALUE -> 2;
      case DeviceType.ACCESS_POINT_VALUE -> 3;
      default -> throw new UnknownDeviceTypeException(value);
    };
  }
}
