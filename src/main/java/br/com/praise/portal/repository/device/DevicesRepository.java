package br.com.praise.portal.repository.device;

import br.com.praise.portal.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DevicesRepository extends MongoRepository<Device, String> {

    Device findFirstByID(String _id);
    @Query("{chave :?0}")
    List<Device> findAllDevicesByKey(String chave);

    @Query("{ativo :?0}")
    List<Device> findAllDevicesByStatus(boolean ativo);
}
