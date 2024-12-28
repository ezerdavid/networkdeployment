package david.ezer.device;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record GetDevice(DevicePort deploymentRepository) {

  public Device handle(String macAddress) {
    return deploymentRepository.getDevice(macAddress);
  }
}
