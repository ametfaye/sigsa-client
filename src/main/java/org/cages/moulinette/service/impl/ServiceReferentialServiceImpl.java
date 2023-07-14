package org.cages.moulinette.service.impl;

import java.util.List;

import org.cages.moulinette.model.Service;
import org.cages.moulinette.repository.ServiceReferentialRepository;
import org.cages.moulinette.service.IServiceReferentialService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service("serviceReferentialService")
public class ServiceReferentialServiceImpl implements IServiceReferentialService {

	@Autowired
	private ServiceReferentialRepository serviceRepo;

	@Override
	public List<Service> getAllServices() {
		return serviceRepo.findAll();
	}

	@Override
	public Service getServiceById(long id) {
		return serviceRepo.findOne(id);
	}

	@Override
	public List<Service> getServicesByEntitePublicRefsId(long id) {
		return serviceRepo.findByEntitePublicRefId(id);
	}

	@Override
	public void deleteService(long srvId) {
		serviceRepo.delete(srvId);
	}

	@Override
	public Service getServiceByCode(String code) {
		return serviceRepo.findByCode(code);
	}

	@Override
	public void updateService(Service service) {
		serviceRepo.save(service);
	}

	@Override
	public void createService(Service serviceToCreate) {
		serviceRepo.save(serviceToCreate);
	}
}
