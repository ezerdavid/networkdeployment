package david.ezer.device.infrastructure;

import java.io.Serial;
import org.springframework.http.HttpStatus;

public class DeviceNotFoundException extends RuntimeException {

  @Serial private static final long serialVersionUID = 1L;
  public static final HttpStatus STATUS_CODE = HttpStatus.NOT_FOUND;
  private final String macAddress;

  public DeviceNotFoundException(String macAddress) {
    this.macAddress = macAddress;
  }

  public String getErrorMessage() {
    return "Mac address " + macAddress + " not found";
  }
}
