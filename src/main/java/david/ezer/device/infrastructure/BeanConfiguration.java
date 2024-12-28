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
  public DevicePort deploymentRepository(DeviceInMemoryAdapter deploymentInMemoryRepository) {
    return deploymentInMemoryRepository;
  }

  @Bean
  public RegisterDevice registerDevice(DevicePort deploymentRepository) {
    return new RegisterDevice(deploymentRepository);
  }

  @Bean
  public GetAllDevices getAllDevices(DevicePort deploymentRepository) {
    return new GetAllDevices(deploymentRepository);
  }

  @Bean
  public GetDevice getDevice(DevicePort deploymentRepository) {
    return new GetDevice(deploymentRepository);
  }

  @Bean
  public GetDevicesTopology getDevicesTopology(DevicePort deploymentRepository) {
    return new GetDevicesTopology(deploymentRepository);
  }

  @Bean
  public GetSingleDeviceTopology getSingleDeviceTopology(DevicePort deploymentRepository) {
    return new GetSingleDeviceTopology(deploymentRepository);
  }
}
