package david.ezer.device.api;

import david.ezer.device.Device;

public record RegisteredDeviceResponse(String deviceType, String macAddress) {

  public RegisteredDeviceResponse(Device device) {
    this(device.deviceType().toString(), device.macAddress());
  }
}
