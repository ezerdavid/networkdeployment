package david.ezer.networkdeployment.device;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record GetDevice(DeploymentRepository deploymentRepository) {

  public Device handle(String macAddress) {
    return deploymentRepository.getDevice(macAddress);
  }
}
