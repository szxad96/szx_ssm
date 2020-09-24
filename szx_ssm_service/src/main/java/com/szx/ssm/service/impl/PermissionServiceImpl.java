package com.szx.ssm.service.impl;

import com.szx.ssm.dao.IPermissionDao;
import com.szx.ssm.domain.Permission;
import com.szx.ssm.service.IPermissionService;
import com.szx.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private IPermissionDao permissionDao;
    @Override
    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
        permissionDao.save(permission);
    }

}
