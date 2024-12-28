package david.ezer.device.infrastructure;

import david.ezer.device.Device;
import david.ezer.device.DeviceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEVICES", schema = "PUBLIC")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceEntity {

  public DeviceEntity(Device device) {
    macAddress = device.macAddress();
    deploymentId = device.deploymentId();
    deviceType = device.deviceType().toString();
    uplinkMacAddress = device.upLinkMacAddress();
  }

  @Id
  @Column(name = "MAC_ADDRESS")
  private String macAddress;

  private int deploymentId;

  private String deviceType;

  @Column(name = "UPLINK_MAC_ADDRESS")
  private String uplinkMacAddress;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "UPLINK_MAC_ADDRESS")
  private Set<DeviceEntity> linkedDevices;

  public Device toDevice() {
    var linked = this.linkedDevices
            .stream()
            .map(DeviceEntity::toDevice)
            .collect(Collectors.toSet());

    return new Device(DeviceType.fromText(deviceType), macAddress, uplinkMacAddress, deploymentId, linked);
  }
}
