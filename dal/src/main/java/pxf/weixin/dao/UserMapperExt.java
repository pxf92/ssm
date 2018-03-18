package pxf.weixin.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import pxf.weixin.model.User;

import java.util.List;

/**
 * @Author pxf
 * @Date 2018/3/17
 * @Description
 */
public interface UserMapperExt extends UserMapper {

    public User selectUserByName(@Param("name")String name);

    @Delete("delete from USER where name =#{name}")
    public int deleteByName(String name);

    @Update("update USER set password=#{password},age=#{age} where name =#{name}")
    public int updateByName(User user);

}
