package org.cages.moulinette.service;

import java.util.List;

import org.cages.moulinette.model.Service;

public interface IServiceReferentialService {

	public List<Service> getAllServices();

	public Service getServiceById(long id);

	public List<Service> getServicesByEntitePublicRefsId(long id);

	public void deleteService(long srvId);

	public Service getServiceByCode(String code);

	public void updateService(Service service);

	public void createService(Service serviceToCreate);
}
