package com.szx.ssm.dao;

import com.szx.ssm.domain.Permission;
import com.szx.ssm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IRoleDao {
    //根据用户id获取所有的角色,通过封装结果集来将权限信息包含在角色信息中
    @Select("select * from role where id in(select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions", column = "id",javaType = java.util.List.class,many = @Many(select = "com.szx.ssm.dao.IPermissionDao.findPermissionByRoleId"))
    })
    public List<Role> findRoleByUserId(String userId) throws Exception;

    /**
     * 查询全部角色信息
     * @return
     */
    @Select("select * from role")
    List<Role> findAll() throws Exception;
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role);
    @Select("select * from role where id = #{roleId}")
    Role findById(String roleId);
    @Select("select * from permission where id not in (select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findOtherPermissions(String roleId);
    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId);
}
