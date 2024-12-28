package david.ezer.device;

import java.util.List;

public interface DevicePort {

  String registerDevice(Device device);

  List<Device> getAllDevices();

  Device getDevice(String macAddress);

  List<Device> getDevicesTopology();

  Device getSingleDeviceTopology(String macAddress);
}
