package com.vivatechrnd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivatechrnd.model.AccessControl;
import com.vivatechrnd.repository.AccessControlRepository;

@Service
public class AccessControlServiceImpl implements AccessControlService{

	@Autowired
	private AccessControlRepository accessControlRepository;
	
	@Override
	public AccessControl addAccessControl(AccessControl accessControl) {
		return accessControlRepository.save(accessControl);
	}

	@Override
	public List<AccessControl> getAccessControls() {
		List<AccessControl> accessControls = accessControlRepository.findAll();
		
		if(accessControls == null || accessControls.isEmpty())
			return new ArrayList<>();
		
		return accessControls;
	}

}
