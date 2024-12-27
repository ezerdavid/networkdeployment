package david.ezer.networkdeployment.device;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record RegisterDevice(DeploymentRepository deploymentRepository) {

    public String handle(Device device) {
        return deploymentRepository.registerDevice(device);
    }
}
