package david.ezer.device.infrastructure;

import david.ezer.device.Device;
import david.ezer.device.DevicePort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceInMemoryAdapter implements DevicePort {

  @PersistenceContext private EntityManager em;

  @Override
  @Transactional
  public String registerDevice(Device device) {
    var entity = new DeviceEntity(device);
    var saved = em.merge(entity);

    return saved.getMacAddress();
  }

  @Override
  public List<Device> getAllDevices(int deploymentId) {
    var query =
        em.createQuery(
            "SELECT d FROM DeviceEntity d "
                + "JOIN DeviceTypeOrdering dto ON d.deviceType = dto.deviceType "
                + "WHERE d.deploymentId = :deploymentId "
                + "ORDER BY dto.orderKey",
            DeviceEntity.class);
    return query.setParameter("deploymentId", deploymentId).getResultList().stream()
        .map(DeviceEntity::toDevice)
        .toList();
  }

  @Override
  public Device getDevice(String macAddress) {
    return em.find(DeviceEntity.class, macAddress).toDevice();
  }

  @Override
  public List<Device> getDevicesTopology() {
    var deviceEntityGraph = em.createEntityGraph(DeviceEntity.class);
    var query =
        em.createQuery(
            "SELECT d FROM DeviceEntity d WHERE d.uplinkMacAddress IS NULL", DeviceEntity.class);
    deviceEntityGraph.addAttributeNodes(DeviceEntity_.linkedDevices);
    query.setHint("jakarta.persistence.fetchgraph", deviceEntityGraph);

    return query.getResultList().stream().map(DeviceEntity::toDevice).toList();
  }

  @Override
  public Device getSingleDeviceTopology(String macAddress) {
    var deviceEntityGraph = em.createEntityGraph(DeviceEntity.class);
    deviceEntityGraph.addAttributeNodes(DeviceEntity_.linkedDevices);
    return em.find(
            DeviceEntity.class,
            macAddress,
            Collections.singletonMap("jakarta.persistence.fetchgraph", deviceEntityGraph))
        .toDevice();
  }
}
