package david.ezer.device.api;

import david.ezer.device.Device;
import java.util.Set;
import java.util.stream.Collectors;

public record DeviceTopologyResponse(String macAddress, Set<DeviceTopologyResponse> linkedDevices) {

  public DeviceTopologyResponse(Device device) {
    this(device.macAddress(), linkedDevicesFrom(device));
  }

  public static Set<DeviceTopologyResponse> linkedDevicesFrom(Device device) {
    return device.connectedDevices().stream()
        .map(DeviceTopologyResponse::new)
        .collect(Collectors.toSet());
  }
}
