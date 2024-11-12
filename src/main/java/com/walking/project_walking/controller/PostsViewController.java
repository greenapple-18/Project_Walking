package com.walking.project_walking.controller;

import com.walking.project_walking.domain.RecentPost;
import com.walking.project_walking.domain.Users;
import com.walking.project_walking.domain.dto.CommentResponseDto;
import com.walking.project_walking.domain.dto.PostResponseDto;
import com.walking.project_walking.domain.dto.PostCreateResponseDto;
import com.walking.project_walking.repository.RecentPostRepository;
import com.walking.project_walking.service.BoardService;
import com.walking.project_walking.service.CommentsService;
import com.walking.project_walking.service.PostsService;
import com.walking.project_walking.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor

public class PostsViewController {
    private final UserService userService;
    private final PostsService postsService;
    private final BoardService boardService;
    private final CommentsService commentsService;
    private final RecentPostRepository recentPostRepository;

    // 게시글 작성 페이지로 이동
    @GetMapping("/create")
    public String createPostPage(Model model) {
        model.addAttribute("boards", boardService.getAllBoards());
        return "create-post";
    }

    // 게시글 상세 페이지로 이동
    @GetMapping("/{postId}")
    public String viewPostDetail(@PathVariable Long postId, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            Long userId = (Long)session.getAttribute("userId");
            PostResponseDto post = postsService.getPostById(postId);
            if (userId != null) {
                Users user = userService.findById(userId);
                model.addAttribute("user", user);
            }

            if (post != null) {
                model.addAttribute("post", post);
                boolean isLiked = postsService.hasLiked(userId, postId);
                model.addAttribute("isLiked", isLiked);
            }

            if (userId != null) {
                RecentPost recentPost = new RecentPost(userId, postId);
                recentPostRepository.save(recentPost);
            }

            List<CommentResponseDto> commentLists = commentsService.getCommentsByPostIdOldest(postId);
            model.addAttribute("comments", commentLists);
            int commentListsSize = 0;
            for (CommentResponseDto comment : commentLists) {
                if (!comment.getIsDeleted())
                    commentListsSize++;
            }
            model.addAttribute("commentsSize", commentListsSize);

            return "post";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "해당 게시글을 찾을 수 없습니다.");
            return "redirect:/view/posts";  // 오류 시 목록 페이지로 리디렉션
        }
    }

    // 게시글 수정 페이지로 이동
    @GetMapping("/{postId}/edit")
    public String editPostPage(@PathVariable Long postId, Model model, RedirectAttributes redirectAttributes) {
        try {
            PostResponseDto post = postsService.getPostById(postId);
            model.addAttribute("post", post);
            return "edit-post";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "해당 게시글을 찾을 수 없습니다.");
            return "redirect:/view/posts";
        }
    }
}
