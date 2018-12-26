package com.hzm.freestyle.mapper;

import com.hzm.freestyle.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Mapper
 *
 * @author Hezeming
 * @version 1.0
 * @date 2018/12/24
 */
@Mapper
public interface UserMapper {

    @Select("select user_id from t_user where user_id = #{0}")
    User getUserByUserId(String userId);
}
