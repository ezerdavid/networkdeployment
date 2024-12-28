package david.ezer.device.infrastructure;

import david.ezer.device.DeviceType;

public class DeviceTypeOrder {

  public DeviceTypeOrder(DeviceType deviceType) {
    this.deviceType = deviceType.toString();
  }


  private String deviceType;
  private int orderNum;

}
