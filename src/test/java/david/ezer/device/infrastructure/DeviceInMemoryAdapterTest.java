package david.ezer.device.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import david.ezer.device.Device;
import david.ezer.device.DevicePort;
import david.ezer.device.DeviceType;
import jakarta.transaction.Transactional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class DeviceInMemoryAdapterTest {

  @Autowired private DevicePort devicePort;

  @Test
  void testDevicesOrder() {
    // GIVEN
    var gatewayDevice = new Device(DeviceType.GATEWAY, "00:00:00:00:00:00", null, Set.of());
    var switchDevice = new Device(DeviceType.SWITCH, "00:00:00:00:00:01", null, Set.of());
    var accessPointDevice = new Device(DeviceType.ACCESS_POINT, "00:00:00:00:00:02", null, Set.of());
    devicePort.registerDevice(switchDevice);
    devicePort.registerDevice(accessPointDevice);
    devicePort.registerDevice(gatewayDevice);

    // WHEN
    var devices = devicePort.getAllDevices();

    // THEN
    assertEquals(3, devices.size(), "Expected 3 device");
    assertEquals(DeviceType.GATEWAY, devices.getFirst().deviceType(), "Expected gateway device to be first");
    assertEquals(DeviceType.SWITCH, devices.get(1).deviceType(), "Expected switch device to be second");
    assertEquals(DeviceType.ACCESS_POINT, devices.get(2).deviceType(), "Expected access point device to be third");
  }

}
