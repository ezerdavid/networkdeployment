package david.ezer.networkdeployment.device.api;

import david.ezer.networkdeployment.device.Device;

public record RegisteredDeviceResponse(String deviceType, String macAddress) {

  public RegisteredDeviceResponse(Device device) {
    this(device.deviceType().toString(), device.macAddress());
  }
}
