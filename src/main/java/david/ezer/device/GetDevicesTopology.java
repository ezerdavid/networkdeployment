package david.ezer.device;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record GetDevicesTopology(DevicePort deploymentRepository) {

  public List<Device> handle() {
    return deploymentRepository.getDevicesTopology();
  }
}
