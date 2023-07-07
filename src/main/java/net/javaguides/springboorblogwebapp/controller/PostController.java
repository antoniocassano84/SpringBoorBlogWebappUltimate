package net.javaguides.springboorblogwebapp.controller;

import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    public static final String ADMIN = "/admin";
    public static final String ADMIN_POSTS = ADMIN + "/posts";
    public static final String ADMIN_POSTS_NEW = ADMIN_POSTS + "/new-post";
    public static final String ADMIN_NEW_POST_VIEW = ADMIN + "/create-post";

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(ADMIN_POSTS)
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAllPosts());
        return ADMIN_POSTS;
    }

    @GetMapping(ADMIN_POSTS_NEW)
    public String newPost(Model model) {
        model.addAttribute("post", new PostDto());
        return ADMIN_NEW_POST_VIEW;
    }
}
