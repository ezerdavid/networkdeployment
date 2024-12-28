package david.ezer.device;

import java.util.Set;
import java.util.regex.Pattern;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record Device(
    DeviceType deviceType,
    String macAddress,
    String upLinkMacAddress,
    Set<Device> connectedDevices) {

  public Device {
    if (!isMacAddressValid(macAddress)) {
      throw new InvalidMacAddressException(macAddress);
    }

    if (upLinkMacAddress != null && !isMacAddressValid(upLinkMacAddress)) {
      throw new InvalidMacAddressException(upLinkMacAddress);
    }
  }

  private boolean isMacAddressValid(String macAddress) {
    var regex = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    var p = Pattern.compile(regex);

    if (macAddress == null) {
      return false;
    }

    var m = p.matcher(macAddress);
    return m.matches();
  }
}
