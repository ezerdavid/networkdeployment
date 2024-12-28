package david.ezer.device;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record RegisterDevice(DevicePort deploymentRepository) {

  public String handle(Device device) {
    return deploymentRepository.registerDevice(device);
  }
}
