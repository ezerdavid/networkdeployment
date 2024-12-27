package david.ezer.networkdeployment.device;

import java.util.List;

public interface DeploymentRepository {

  String registerDevice(Device device);

  List<Device> getAllDevices(int deploymentId);

  Device getDevice(String macAddress);
}
