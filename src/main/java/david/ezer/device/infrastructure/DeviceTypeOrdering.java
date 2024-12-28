package david.ezer.device.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DEVICE_TYPE_ORDERING", schema = "PUBLIC")
public class DeviceTypeOrdering {

  @Id private String deviceType;
  private int orderKey;
}
