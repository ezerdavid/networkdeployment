package david.ezer.device;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record GetSingleDeviceTopology(DevicePort deploymentRepository) {

  public Device handle(String macAddress) {
    return deploymentRepository.getSingleDeviceTopology(macAddress);
  }
}
