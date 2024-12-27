package david.ezer.networkdeployment.device.api;

import david.ezer.networkdeployment.device.GetAllRegisteredDevices;
import david.ezer.networkdeployment.device.RegisterDevice;
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

    private final GetAllRegisteredDevices getAllRegisteredDevices;
    private final RegisterDevice registerDevice;

    @PostMapping("{deploymentId}/devices")
    public ResponseEntity<Void> registerDevice(@PathVariable("deploymentId") int deploymentId, @RequestBody RegisterDeviceRequest registerDeviceRequest) {
        var device = registerDeviceRequest.toDevice(deploymentId);
        log.atInfo()
                .addKeyValue("deploymentId", deploymentId)
                .log("Registering device {}", device);

        var id = registerDevice.handle(device);
        var uri = URI.create(String.format("/%s/devices/%s", deploymentId, id));
        return ResponseEntity.created(uri)
                .build();
    }

    @GetMapping("{deploymentId}/devices")
    public ResponseEntity<List<RegisteredDeviceResponse>> getDevices(@PathVariable("deploymentId") int deploymentId) {
        var devices = getAllRegisteredDevices.handle(deploymentId)
                .stream()
                .map(RegisteredDeviceResponse::new)
                .toList();

        return ResponseEntity.ok(devices);
    }
}
