package com.szx.ssm.dao;

import com.szx.ssm.domain.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ISysLogDao {
    @Insert("insert into syslog(visitTime,username,url,executionTime,method) values(#{visitTime},#{username},#{url},#{executionTime},#{method})")
    public void save(SysLog sysLog) throws Exception;
    @Select("select * from sysLog")
    List<SysLog> findAll() throws Exception;
}
