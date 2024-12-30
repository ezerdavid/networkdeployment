package david.ezer.device;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DeviceTest {

  @ParameterizedTest
  @MethodSource
  void createDeviceWithValidAddresses(String macAddress, String uplinkMacAddress) {
    // GIVEN
    var deviceType = DeviceType.GATEWAY;
    Set<Device> connectedDevices = Set.of();

    // WHEN
    var device = new Device(deviceType, macAddress, uplinkMacAddress, connectedDevices);

    // THEN
    assertEquals(macAddress, device.macAddress(), "Expected mac address to match");
    assertEquals(uplinkMacAddress, device.upLinkMacAddress(), "Expected uplink address to match");
  }

  private static Stream<Arguments> createDeviceWithValidAddresses() {
    return Stream.of(
        Arguments.of("00-B0-D0-63-C2-51", null),
        Arguments.of("00-b0-d0-63-c2-51", null),
        Arguments.of("00-B0-D0-63-C2-52", "00-B0-D0-63-C2-51"),
        Arguments.of("00-b0-d0-63-c2-52", "00-b0-d0-63-c2-51"));
  }

  @ParameterizedTest
  @MethodSource
  void throwOnInvalidMacAddress(String macAddress, String uplinkMacAddress, String message) {
    var deviceType = DeviceType.GATEWAY;
    Set<Device> connectedDevices = Set.of();
    assertThrows(
        InvalidMacAddressException.class,
        () -> new Device(deviceType, macAddress, uplinkMacAddress, connectedDevices),
        message);
  }

  private static Stream<Arguments> throwOnInvalidMacAddress() {
    return Stream.of(
        Arguments.of("00-B0-D0-63-C2", null, "Should throw on invalid mac address"),
        Arguments.of("00-b0-d0-63-c2-51-88", null, "Should throw on invalid mac address"),
        Arguments.of("", null, "Should throw on invalid mac address"),
        Arguments.of(null, null, "Should throw on invalid mac address"),
        Arguments.of("00-b0-d0-63-c2-7&", null, "Should throw on invalid mac address"),
        Arguments.of(
            "00-B0-D0-63-C2-51", "00-B0-D0-63-C2", "Should throw on invalid uplink address"),
        Arguments.of(
            "00-B0-D0-63-C2-51", "00-B0-D0-63-C2-51-88", "Should throw on invalid uplink address"),
        Arguments.of(
            "00-B0-D0-63-C2-51", "00-b0-d0-63-c2-7&", "Should throw on invalid uplink address"),
        Arguments.of("00-B0-D0-63-C2-51", "", "Should throw on invalid uplink address"));
  }
}
