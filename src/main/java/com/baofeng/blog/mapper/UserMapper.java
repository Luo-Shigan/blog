package com.baofeng.blog.mapper;

import com.baofeng.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
    User selectById(int id);
    
    List<User> selectByCondition(@Param("username") String username, 
                               @Param("email") String email,
                               @Param("status") User.Status status);
    
    List<User> selectByPage(@Param("offset") int offset, 
                          @Param("pageSize") int pageSize);
    
    User selectByUsernameOrEmail(String account);
    int updateLoginInfo(int id);
    int incrementLoginAttempts(int id);
    int updateUserSelective(User user);
    int updatePassword(String username,@Param("password") String newPassword);
    //@Param("username")必须指定，因为resultmap定义好了映射java对象password
} 