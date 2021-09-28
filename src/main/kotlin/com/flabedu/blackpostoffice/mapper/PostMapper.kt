package com.flabedu.blackpostoffice.mapper

import com.flabedu.blackpostoffice.model.post.Post
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface PostMapper {

    @Insert(
        """
        INSERT INTO post (email, title, content, created_at)
        VALUES (#{email}, #{createPost.title}, #{createPost.content}, now());
        """
    )
    fun createMyPost(email: String, createPost: Post)

    @Select(
        """
        SELECT title, content
        FROM post as i
                 JOIN (SELECT post_id
                       FROM post
                       WHERE email = #{email}
                       ORDER BY post_id DESC LIMIT #{pageSize}
                       OFFSET #{pageNo}) as temp on temp.post_id = i.post_id 
        """
    )
    fun getUserPosts(email: String, pageNo: Int, pageSize: Int): List<Post>

    @Select(
        """
        SELECT title, content
        FROM post as i
                 JOIN (SELECT post_id
                       FROM post
                       ORDER BY post_id DESC LIMIT #{pageSize}
                       OFFSET #{pageNo}) as temp on temp.post_id = i.post_id 
        """
    )
    fun getUsersPosts(pageNo: Int, pageSize: Int): List<Post>

    @Update(
        """
        UPDATE post
        SET content = #{updatePost.content}, title = #{updatePost.title}
        WHERE post_id = #{postId}            
        """
    )
    fun updateMyPost(postId: Long, updatePost: Post)

    @Delete(
        """
        DELETE FROM post 
        WHERE post_id = #{postId}
        """
    )
    fun deleteMyPost(postId: Long)
}
