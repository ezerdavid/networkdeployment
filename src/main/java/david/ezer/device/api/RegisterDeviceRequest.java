package david.ezer.device.api;

import david.ezer.device.Device;
import david.ezer.device.DeviceType;
import java.util.HashSet;

public record RegisterDeviceRequest(String deviceType, String macAddress, String upLinkMacAddress) {

  public Device toDevice(int deploymentId) {
    return new Device(
        DeviceType.fromText(deviceType),
        macAddress,
        upLinkMacAddress,
        deploymentId,
        new HashSet<>());
  }
}
