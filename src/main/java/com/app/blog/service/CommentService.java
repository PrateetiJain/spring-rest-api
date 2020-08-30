package com.app.blog.service;

import com.app.blog.model.Comment;

import java.util.Optional;

public interface CommentService {

    void saveComment(Comment comment);
    void deleteCommentById(Long commentId);
    Comment findCommentById(Long commentId);
    boolean commentExist(Long commentId);
}
