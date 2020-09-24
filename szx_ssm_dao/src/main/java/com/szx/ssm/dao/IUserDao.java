package com.szx.ssm.dao;

import com.szx.ssm.domain.Role;
import com.szx.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {
    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id",javaType = java.util.List.class,many = @Many(select = "com.szx.ssm.dao.IRoleDao.findRoleByUserId"))
            /*根据用户的id查询出所有的角色，注意数据库里是没有roles的信息的，之前只是通过username来查出来信息封装到userInfo然后取出来用户名和密码*/
    })
    public UserInfo findByUsername(String username) throws Exception;

    @Select("select * from users")
    List<UserInfo> findAll() throws Exception;
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status}) ")
    void save(UserInfo userInfo) throws Exception;

    /**
     * 用户详情
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from users where id =#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id",javaType = java.util.List.class,many = @Many(select = "com.szx.ssm.dao.IRoleDao.findRoleByUserId"))
            //查询用户时，把用户对应的角色，和角色对应的权限全都查出来
    }

    )
    UserInfo findById(String id) throws Exception;

    @Select("select * from role where id not in (select roleId from users_role where userId=#{userId})")
    List<Role> findOtherRoles(String userId);
    @Insert("insert into users_role(userID,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);
    //多个参数的情况下，要表明对应关系，否则就成为userId的userId属性和roleId属性
}
