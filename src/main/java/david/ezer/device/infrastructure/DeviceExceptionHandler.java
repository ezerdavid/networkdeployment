package david.ezer.device.infrastructure;

import david.ezer.device.UnknownDeviceTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DeviceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = UnknownDeviceTypeException.class)
  public ResponseEntity<Object> handle(UnknownDeviceTypeException e, WebRequest request) {
    var errorResponse = new ErrorResponse(e.getErrorMessage());
    return handleExceptionInternal(
        e, errorResponse, new HttpHeaders(), UnknownDeviceTypeException.STATUS_CODE, request);
  }

  @ExceptionHandler(value = DeviceNotFoundException.class)
  public ResponseEntity<Object> handle(DeviceNotFoundException e, WebRequest request) {
    var errorResponse = new ErrorResponse(e.getErrorMessage());
    return handleExceptionInternal(
            e, errorResponse, new HttpHeaders(), DeviceNotFoundException.STATUS_CODE, request);
  }
}
