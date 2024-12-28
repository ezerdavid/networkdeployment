package david.ezer.device.infrastructure;

import david.ezer.device.Device;
import david.ezer.device.DeviceType;
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
import org.hibernate.annotations.Formula;

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
    sortingKey = device.deviceType().orderNumber();
    this.uplinkMacAddress = uplinkMacAddress;
  }

  @Id
  @Column(name = "MAC_ADDRESS")
  private String macAddress;

  private int deploymentId;

  private String deviceType;

  private int sortingKey;

  @Formula("case division when 'BRONZE' then 0 when 'SILVER' then 1 ... end")
  private int deviceSortingWeight;

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
