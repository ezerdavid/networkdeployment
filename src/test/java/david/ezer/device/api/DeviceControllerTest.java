package david.ezer.device.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import david.ezer.device.DeviceType;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.util.CollectionUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceControllerTest {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void registeringDeviceToNetworkDeployment() {
    // GIVEN
    var macAddress = "00:00:00:00:00:00";
    var registerDeviceRequest =
        new HttpEntity<>(new RegisterDeviceRequest(DeviceType.GATEWAY_VALUE, macAddress, null));

    // WHEN
    var location = restTemplate.postForLocation("/devices", registerDeviceRequest);

    // THEN
    assertNotNull(location, "Expected location to be created");
    assertEquals(URI.create(String.format("/devices/%s", macAddress)), location, "Location didn't match expected format");
  }

  @Test
  void retrievingAllRegisteredDevices() {
    // GIVEN
    var gatewayMacAddress = "00:00:00:00:00:00";
    var switchMacAddress = "00:00:00:00:00:01";
    var accessPointMacAddress = "00:00:00:00:00:02";
    var registerGatewayRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.GATEWAY_VALUE, gatewayMacAddress, null));
    var registerSwitchRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.SWITCH_VALUE, switchMacAddress, gatewayMacAddress));
    var registerAccessPointRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.ACCESS_POINT_VALUE, accessPointMacAddress, gatewayMacAddress));
    restTemplate.postForLocation("/devices", registerGatewayRequest);
    restTemplate.postForLocation("/devices", registerSwitchRequest);
    restTemplate.postForLocation("/devices", registerAccessPointRequest);

    // WHEN
    var response = restTemplate.getForObject("/devices", RegisteredDeviceResponse[].class);

    // THEN
    assertEquals(3, response.length, "Expected 3 devices");
    var first = response[0];
    assertEquals(gatewayMacAddress, first.macAddress(), "Unexpected gateway mac address");
    assertEquals(DeviceType.GATEWAY_VALUE, first.deviceType(), "Unexpected device type");
    var second = response[1];
    assertEquals(switchMacAddress, second.macAddress(), "Unexpected switch mac address");
    assertEquals(DeviceType.SWITCH_VALUE, second.deviceType(), "Unexpected device type");
    var third = response[2];
    assertEquals(accessPointMacAddress, third.macAddress(), "Unexpected access point mac address");
    assertEquals(DeviceType.ACCESS_POINT_VALUE, third.deviceType(), "Unexpected device type");
  }

  @Test
  void retrievingNetworkDeploymentDeviceByMacAddress() {
    // GIVEN
    var gatewayMacAddress = "00:00:00:00:00:00";
    var registerGatewayRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.GATEWAY_VALUE, gatewayMacAddress, null));
    var location = restTemplate.postForLocation("/devices", registerGatewayRequest);

    // WHEN
    var response = restTemplate.getForObject(location, RegisteredDeviceResponse.class);

    // THEN
    assertEquals(gatewayMacAddress, response.macAddress(), "Unexpected mac address");
    assertEquals(DeviceType.GATEWAY_VALUE, response.deviceType(), "Unexpected device type");
  }

  @Test
  void retrieveAllRegisteredDevicesTopology() {
    // GIVEN
    var gatewayMacAddress = "00:00:00:00:00:00";
    var switchMacAddress = "00:00:00:00:00:01";
    var gateway2MacAddress = "00:00:00:00:00:02";
    var registerGatewayRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.GATEWAY_VALUE, gatewayMacAddress, null));
    var registerSwitchRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.SWITCH_VALUE, switchMacAddress, gatewayMacAddress));
    var registerAccessPointRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.GATEWAY_VALUE, gateway2MacAddress, null));
    restTemplate.postForLocation("/devices", registerGatewayRequest);
    restTemplate.postForLocation("/devices", registerSwitchRequest);
    restTemplate.postForLocation("/devices", registerAccessPointRequest);

    // WHEN
    var response = restTemplate.getForObject("/devices/topology", DeviceTopologyResponse[].class);

    // THEN
    assertEquals(2, response.length, "Expected 2 root devices");
    var first = response[0];
    assertEquals(gatewayMacAddress, first.macAddress(), "Unexpected mac address");
    var firstLinkedDevice = first.linkedDevices();
    assertEquals(1, firstLinkedDevice.size(), "Expected 1 linked device");
    var linkedDevice = firstLinkedDevice.iterator().next();
    assertEquals(switchMacAddress, linkedDevice.macAddress(), "Unexpected mac address");
    assertTrue(CollectionUtils.isEmpty(linkedDevice.linkedDevices()), "Expected no linked devices");

    var second = response[1];
    assertEquals(gateway2MacAddress, second.macAddress(), "Unexpected mac address");
    assertTrue(CollectionUtils.isEmpty(second.linkedDevices()), "Expected no linked devices");
  }

  @Test
  void retrieveNetworkDeviceTopologyFromDevice() {
    // GIVEN
    var gatewayMacAddress = "00:00:00:00:00:00";
    var switchMacAddress = "00:00:00:00:00:01";
    var accessPointMacAddress = "00:00:00:00:00:02";
    var registerGatewayRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.GATEWAY_VALUE, gatewayMacAddress, null));
    var registerSwitchRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.SWITCH_VALUE, switchMacAddress, gatewayMacAddress));
    var registerAccessPointRequest =
            new HttpEntity<>(new RegisterDeviceRequest(DeviceType.ACCESS_POINT_VALUE, accessPointMacAddress, switchMacAddress));
    restTemplate.postForLocation("/devices", registerGatewayRequest);
    restTemplate.postForLocation("/devices", registerSwitchRequest);
    restTemplate.postForLocation("/devices", registerAccessPointRequest);

    // WHEN
    var response = restTemplate.getForObject(String.format("/devices/topology/%s", switchMacAddress), DeviceTopologyResponse.class);

    // THEN
    assertEquals(switchMacAddress, response.macAddress(), "Unexpected mac address");
    var firstLinkedDevice = response.linkedDevices();
    assertEquals(1, firstLinkedDevice.size(), "Expected 1 linked device");
    var linkedDevice = firstLinkedDevice.iterator().next();
    assertEquals(accessPointMacAddress, linkedDevice.macAddress(), "Unexpected mac address for linked device");
  }
}
