package david.ezer.device;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class DeviceTypeTest {

  @ParameterizedTest
  @MethodSource
  void createDeviceTypeFromText(String deviceType, DeviceType expected) {
    // WHEN
    var actual = DeviceType.fromText(deviceType);

    // THEN
    assertEquals(expected, actual, "Expected " + expected + " but got " + actual);
  }

  private static Stream<Arguments> createDeviceTypeFromText() {
    return Stream.of(
        Arguments.of(DeviceType.GATEWAY_VALUE, DeviceType.GATEWAY),
        Arguments.of(DeviceType.SWITCH_VALUE, DeviceType.SWITCH),
        Arguments.of(DeviceType.ACCESS_POINT_VALUE, DeviceType.ACCESS_POINT));
  }

  @ParameterizedTest
  @ValueSource(strings = {"SWITCH", "switch", "SwitcH", "Duck Tape"})
  @NullAndEmptySource
  void throwOnInvalidDeviceType(String deviceType) {
    assertThrows(UnknownDeviceTypeException.class, () -> DeviceType.fromText(deviceType));
  }
}
