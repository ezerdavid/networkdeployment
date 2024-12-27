package david.ezer.networkdeployment.device.infrastructure;

import david.ezer.networkdeployment.device.DeploymentRepository;
import david.ezer.networkdeployment.device.GetAllRegisteredDevices;
import david.ezer.networkdeployment.device.RegisterDevice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DeploymentRepository deploymentRepository(DeploymentInMemoryRepository deploymentInMemoryRepository) {
        return deploymentInMemoryRepository;
    }

    @Bean
    public RegisterDevice registerDevice(DeploymentRepository deploymentRepository) {
        return new RegisterDevice(deploymentRepository);
    }

    @Bean
    public GetAllRegisteredDevices getAllRegisteredDevices(DeploymentRepository deploymentRepository) {
    return new GetAllRegisteredDevices(deploymentRepository);
    }

}
