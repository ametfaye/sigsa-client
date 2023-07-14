package org.cages.moulinette.service.impl;

import org.cages.moulinette.service.IFooterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("footerService")
public class FooterServiceImpl implements IFooterService {

	@Value( "${app.version}" )
	private String appVersion;

	@Override
	public String getAppVersion() {
		return appVersion;
	}
}
