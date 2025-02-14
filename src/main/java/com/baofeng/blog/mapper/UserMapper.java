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
    int updateLoginInfo(Long id);
    int incrementLoginAttempts(Long id);
    int updateUserSelective(User user);
} 