package david.ezer.networkdeployment.device;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DeviceController {

    @PostMapping("{deploymentId}/devices")
    public ResponseEntity<Void> registerDevice(@PathVariable("deploymentId") String deploymentId, @RequestBody Device device) {
        log.atInfo()
                .addKeyValue("deploymentId", deploymentId)
                .log("Registering device {}", device);

        return ResponseEntity.ok().build();
    }
}
