package david.ezer.device.infrastructure;

import david.ezer.device.InvalidMacAddressException;
import david.ezer.device.UnknownDeviceTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class DeviceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = UnknownDeviceTypeException.class)
  public ResponseEntity<Object> handle(UnknownDeviceTypeException e, WebRequest request) {
    log.atError().log(e.getErrorMessage(), e);
    var errorResponse = new ErrorResponse(e.getErrorMessage());
    return handleExceptionInternal(
        e, errorResponse, new HttpHeaders(), UnknownDeviceTypeException.STATUS_CODE, request);
  }

  @ExceptionHandler(value = InvalidMacAddressException.class)
  public ResponseEntity<Object> handle(InvalidMacAddressException e, WebRequest request) {
    log.atError().log(e.getErrorMessage(), e);
    var errorResponse = new ErrorResponse(e.getErrorMessage());
    return handleExceptionInternal(
        e, errorResponse, new HttpHeaders(), InvalidMacAddressException.STATUS_CODE, request);
  }

  @ExceptionHandler(value = DeviceNotFoundException.class)
  public ResponseEntity<Object> handle(DeviceNotFoundException e, WebRequest request) {
    log.atError().log(e.getErrorMessage(), e);
    var errorResponse = new ErrorResponse(e.getErrorMessage());
    return handleExceptionInternal(
        e, errorResponse, new HttpHeaders(), DeviceNotFoundException.STATUS_CODE, request);
  }

  @ExceptionHandler(value = DataIntegrityViolationException.class)
  public ResponseEntity<Object> handle(DataIntegrityViolationException e, WebRequest request) {
    log.atError().log(e.getMessage(), e);
    var errorResponse = new ErrorResponse("Data integrity violated");
    return handleExceptionInternal(
        e, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
