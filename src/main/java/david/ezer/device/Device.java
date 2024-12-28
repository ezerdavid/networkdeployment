package david.ezer.device;

import java.util.Set;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record Device(
    DeviceType deviceType,
    String macAddress,
    String upLinkMacAddress,
    Set<Device> connectedDevices) {}
