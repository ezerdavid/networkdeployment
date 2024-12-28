package david.ezer.device.api;

import david.ezer.device.GetAllDevices;
import david.ezer.device.GetDevice;
import david.ezer.device.RegisterDevice;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DeviceController {

  private final GetAllDevices getAllDevices;
  private final GetDevice getDevice;
  private final RegisterDevice registerDevice;

  @PostMapping("{deploymentId}/devices")
  public ResponseEntity<Void> registerDevice(
      @PathVariable("deploymentId") int deploymentId,
      @RequestBody RegisterDeviceRequest registerDeviceRequest) {
    var device = registerDeviceRequest.toDevice(deploymentId);
    log.atInfo().addKeyValue("deploymentId", deploymentId).log("Registering device {}", device);

    var id = registerDevice.handle(device);
    var uri = URI.create(String.format("/%s/devices/%s", deploymentId, id));
    return ResponseEntity.created(uri).build();
  }

  @GetMapping("{deploymentId}/devices")
  public ResponseEntity<List<RegisteredDeviceResponse>> getDevices(
      @PathVariable("deploymentId") int deploymentId) {
    var devices =
        getAllDevices.handle(deploymentId).stream().map(RegisteredDeviceResponse::new).toList();

    return ResponseEntity.ok(devices);
  }

  @GetMapping("{deploymentId}/devices/{macAddress}")
  public ResponseEntity<RegisteredDeviceResponse> getDevice(
      @PathVariable String macAddress, @PathVariable String deploymentId) {
    log.atInfo()
        .addKeyValue("deploymentId", deploymentId)
        .addKeyValue("macAddress", macAddress)
        .log("Getting device for deployment");

    var device = getDevice.handle(macAddress);
    var response = new RegisteredDeviceResponse(device);

    return ResponseEntity.ok(response);
  }
}
