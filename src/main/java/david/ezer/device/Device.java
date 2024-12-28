package david.ezer.device;

public record Device(
    DeviceType deviceType, String macAddress, String upLinkMacAddress, int deploymentId) {}
