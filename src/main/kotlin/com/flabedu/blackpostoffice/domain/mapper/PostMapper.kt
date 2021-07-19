package com.flabedu.blackpostoffice.domain.mapper

import com.flabedu.blackpostoffice.domain.model.Post
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface PostMapper {

    @Insert(
        """
        INSERT INTO post (post_id, email, title, content, created_at)
        VALUES (#{postId}, #{email}, #{title}, #{content}, #{createdAt});
        """
    )
    fun createMyPost(post: Post)

    @Select(
        """
        SELECT title, content
        FROM post as i
                 JOIN (SELECT post_id
                       FROM post
                       WHERE email = #{email}
                       ORDER BY created_at DESC LIMIT #{pageSize}
                       OFFSET #{pageNo}) as temp on temp.post_id = i.post_id
        """
    )
    fun getPosts(email: String, pageNo: Int, pageSize: Int): List<Post>

    @Update(
        """
        UPDATE post
        SET content = #{content}, title = #{title}
        WHERE post_id = #{postId}            
        """
    )
    fun updateMyPost(post: Post)

    @Delete(
        """
        DELETE FROM post 
        WHERE post_id = #{postId}
        """
    )
    fun deleteMyPost(post: Long)
}
