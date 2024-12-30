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
import java.util.HashSet;
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
    deviceType = device.deviceType().toString();
    uplinkMacAddress = device.upLinkMacAddress();
  }

  @Id
  @Column(name = "MAC_ADDRESS")
  private String macAddress;

  private String deviceType;

  @Column(name = "UPLINK_MAC_ADDRESS")
  private String uplinkMacAddress;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "UPLINK_MAC_ADDRESS")
  private Set<DeviceEntity> linkedDevices;

  public Device toDevice() {
    Set<Device> linked = new HashSet<>();
    if (linkedDevices != null) {
      linked = this.linkedDevices.stream().map(DeviceEntity::toDevice).collect(Collectors.toSet());
    }

    return new Device(DeviceType.fromText(deviceType), macAddress, uplinkMacAddress, linked);
  }

  public Device toDeviceWithoutLinkedDevices() {
    return new Device(DeviceType.fromText(deviceType), macAddress, uplinkMacAddress, new HashSet<>());
  }
}
