package david.ezer.networkdeployment.device.infrastructure;

import david.ezer.networkdeployment.device.DeploymentRepository;
import david.ezer.networkdeployment.device.Device;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DeploymentInMemoryRepository implements DeploymentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public String registerDevice(Device device) {
        var uplink = device.upLinkMacAddress() == null ? null : em.find(DeviceEntity.class, device.upLinkMacAddress());
        var entity = new DeviceEntity(device, uplink);
        var saved = em.merge(entity);

        return saved.getMacAddress();
    }

    @Override
    public List<Device> getAllDevices(int deploymentId) {
        var query = em.createQuery("SELECT d FROM DeviceEntity d WHERE d.deploymentId = :deploymentId", DeviceEntity.class);
        return query.setParameter("deploymentId", deploymentId)
                .getResultList()
                .stream()
                .map(DeviceEntity::toDevice)
                .toList();
    }
}
