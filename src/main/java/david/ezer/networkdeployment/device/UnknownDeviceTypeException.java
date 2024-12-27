package david.ezer.networkdeployment.device;

import java.io.Serial;
import org.springframework.http.HttpStatus;

public class UnknownDeviceTypeException extends RuntimeException {

  @Serial private static final long serialVersionUID = 1L;
  private final String deviceType;
  public static final HttpStatus STATUS_CODE = HttpStatus.BAD_REQUEST;

  public UnknownDeviceTypeException(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getErrorMessage() {
    return "Unknown device type: " + deviceType;
  }
}
