package david.ezer.device.infrastructure;

import david.ezer.device.DevicePort;
import david.ezer.device.GetAllDevices;
import david.ezer.device.GetDevice;
import david.ezer.device.GetDevicesTopology;
import david.ezer.device.GetSingleDeviceTopology;
import david.ezer.device.RegisterDevice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public DevicePort devicePort(DeviceInMemoryAdapter deviceInMemoryAdapter) {
    return deviceInMemoryAdapter;
  }

  @Bean
  public RegisterDevice registerDevice(DevicePort devicePort) {
    return new RegisterDevice(devicePort);
  }

  @Bean
  public GetAllDevices getAllDevices(DevicePort devicePort) {
    return new GetAllDevices(devicePort);
  }

  @Bean
  public GetDevice getDevice(DevicePort devicePort) {
    return new GetDevice(devicePort);
  }

  @Bean
  public GetDevicesTopology getDevicesTopology(DevicePort devicePort) {
    return new GetDevicesTopology(devicePort);
  }

  @Bean
  public GetSingleDeviceTopology getSingleDeviceTopology(DevicePort devicePort) {
    return new GetSingleDeviceTopology(devicePort);
  }
}
