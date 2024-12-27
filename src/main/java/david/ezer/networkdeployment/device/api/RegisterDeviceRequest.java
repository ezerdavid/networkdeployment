package david.ezer.networkdeployment.device.api;

import david.ezer.networkdeployment.device.Device;
import david.ezer.networkdeployment.device.DeviceType;

public record RegisterDeviceRequest(String deviceType, String macAddress, String upLinkMacAddress) {

    public Device toDevice(int deploymentId) {
        return new Device(DeviceType.fromText(deviceType), macAddress, upLinkMacAddress, deploymentId);
    }
}
