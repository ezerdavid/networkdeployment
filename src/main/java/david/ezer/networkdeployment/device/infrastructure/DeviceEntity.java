package david.ezer.networkdeployment.device.infrastructure;

import david.ezer.networkdeployment.device.Device;
import david.ezer.networkdeployment.device.DeviceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
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

  public DeviceEntity(Device device, DeviceEntity uplinkMacAddress) {
    macAddress = device.macAddress();
    deploymentId = device.deploymentId();
    deviceType = device.deviceType().toString();
    this.uplinkMacAddress = uplinkMacAddress;
  }

  @Id
  @Column(name = "MAC_ADDRESS")
  private String macAddress;

  private int deploymentId;

  private String deviceType;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "UPLINK_MAC_ADDRESS", referencedColumnName = "MAC_ADDRESS")
  private DeviceEntity uplinkMacAddress;

  @OneToMany(mappedBy = "uplinkMacAddress", fetch = FetchType.LAZY)
  private Set<DeviceEntity> connectedDevices;

  public Device toDevice() {
    var uplink = uplinkMacAddress == null ? null : uplinkMacAddress.getMacAddress();
    return new Device(DeviceType.fromText(deviceType), macAddress, uplink, deploymentId);
  }
}
