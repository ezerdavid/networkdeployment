package david.ezer.device;

import java.io.Serial;
import org.springframework.http.HttpStatus;

public class InvalidMacAddressException extends RuntimeException {

  @Serial private static final long serialVersionUID = 1L;
  private final String macAddress;
  public static final HttpStatus STATUS_CODE = HttpStatus.BAD_REQUEST;

  public InvalidMacAddressException(String macAddress) {
    this.macAddress = macAddress;
  }

  public String getErrorMessage() {
    return macAddress + " is not valid";
  }
}
