package br.com.praise.portal.repository.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import br.com.praise.portal.model.Device;

public class DeviceRepositoryImpl implements DevicesRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Device> getAll() {
        return mongoTemplate.findAll(Device.class);
    }

}
