package david.ezer.networkdeployment.device.infrastructure;

import david.ezer.networkdeployment.device.DeploymentRepository;
import david.ezer.networkdeployment.device.GetAllDevices;
import david.ezer.networkdeployment.device.GetDevice;
import david.ezer.networkdeployment.device.RegisterDevice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public DeploymentRepository deploymentRepository(
      DeploymentInMemoryRepository deploymentInMemoryRepository) {
    return deploymentInMemoryRepository;
  }

  @Bean
  public RegisterDevice registerDevice(DeploymentRepository deploymentRepository) {
    return new RegisterDevice(deploymentRepository);
  }

  @Bean
  public GetAllDevices getAllDevices(DeploymentRepository deploymentRepository) {
    return new GetAllDevices(deploymentRepository);
  }

  @Bean
  public GetDevice getDevice(DeploymentRepository deploymentRepository) {
    return new GetDevice(deploymentRepository);
  }
}
