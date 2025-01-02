package org.ne.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.ne.springboot.pojo.Category;

import java.util.List;

/**
 * @author en
 * @date 2025/1/1 下午6:14 1月
 **/
@Mapper
public interface CategoryMapper {


    @Insert("insert into category(category_name, category_alias, create_user, create_time, update_time, fk_category_user) " +
            "values(#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime}, #{createUser})")
    void add(Category category);


    @Select("select * from category where create_user = #{userId}")
    List<Category> list(Integer userId);

    @Select("select * from category where create_user=#{userId} and id=#{id}")
    Category findById(@Param("id") Integer id,@Param("userId") Integer userId);

//        @Update("update user set user_pic=#{userPic}, update_time=now() where id=#{id}")
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);
}
