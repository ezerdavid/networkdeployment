package david.ezer.device;

import java.util.Set;

public record Device(
    DeviceType deviceType,
    String macAddress,
    String upLinkMacAddress,
    int deploymentId,
    Set<Device> connectedDevices) {}
