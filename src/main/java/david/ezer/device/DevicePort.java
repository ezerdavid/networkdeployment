package david.ezer.device;

import java.util.List;

public interface DevicePort {

  String registerDevice(Device device);

  List<Device> getAllDevices(int deploymentId);

  Device getDevice(String macAddress);
}
