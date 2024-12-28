package david.ezer.device.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEVICE_TYPE_ORDERING", schema = "PUBLIC")
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTypeOrdering {

  @Id private String deviceType;
  private int orderKey;
}
