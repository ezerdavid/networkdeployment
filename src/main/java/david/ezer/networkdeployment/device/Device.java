package david.ezer.networkdeployment.device;

public record Device(
    DeviceType deviceType, String macAddress, String upLinkMacAddress, int deploymentId) {}
