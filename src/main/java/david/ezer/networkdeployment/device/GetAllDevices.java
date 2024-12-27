package david.ezer.networkdeployment.device;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record GetAllDevices(DeploymentRepository deploymentRepository) {

  public List<Device> handle(int deploymentId) {
    return deploymentRepository.getAllDevices(deploymentId);
  }
}
