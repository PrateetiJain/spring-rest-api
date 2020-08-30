package com.app.blog.service;

import com.app.blog.model.Comment;
import com.app.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment) {
         this.commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Long commentId) {
        this.commentRepository.deleteById(commentId);
    }

    @Override
    public Comment findCommentById(Long commentId) {
        Comment comment = commentRepository.getOne(commentId);
        return comment;
    }

    @Override
    public boolean commentExist(Long commentId) {
        return commentRepository.existsById(commentId);
    }


}
