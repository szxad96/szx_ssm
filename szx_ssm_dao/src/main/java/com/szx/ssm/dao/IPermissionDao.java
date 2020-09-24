package com.szx.ssm.dao;

import com.szx.ssm.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IPermissionDao {
    /**
     * 用户详情想要查到角色和对应的权限信息，创建PermissionDao,来通过角色id来获得权限信息
     * 在IRoleDao中使用
     * 查询用户时，把用户对应的角色，和角色对应的权限全都查出来
     */
    @Select("select * from permission where id in (select permissionId from role_permission where roleId =#{id})")
    public List<Permission> findPermissionByRoleId(String id) throws Exception;
    @Select("select * from permission")
    List<Permission> findAll() throws Exception;
    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    void save(Permission permission);
}
