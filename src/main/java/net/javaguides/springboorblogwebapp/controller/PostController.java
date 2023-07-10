package net.javaguides.springboorblogwebapp.controller;

import jakarta.validation.Valid;
import net.javaguides.springboorblogwebapp.dto.PostDto;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    public static final String ADMIN = "/admin";
    public static final String ADMIN_POSTS = ADMIN + "/posts";
    public static final String ADMIN_POSTS_PAGE = ADMIN_POSTS + "/{pageNo}";
    public static final String ADMIN_POSTS_NEW = ADMIN_POSTS + "/new-post";
    public static final String ADMIN_NEW_POST = ADMIN + "/create-post";

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(ADMIN_POSTS)
    public String posts(Model model) {
        return allPostsPaginated(1, model);
    }

    @GetMapping(ADMIN_POSTS_PAGE)
    public String allPostsPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        Page<PostDto> page = postService.findPaginated(pageNo, 5);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("posts", page.getContent());
        return ADMIN_POSTS;
    }

    @GetMapping(ADMIN_POSTS_NEW)
    public String newPost(Model model) {
        model.addAttribute("post", new PostDto());
        return ADMIN_NEW_POST;
    }

    @PostMapping(ADMIN_POSTS)
    public String createPost(@ModelAttribute("post") @Valid PostDto postDto,
                             BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("post", postDto);
            return ADMIN_NEW_POST;
        }
        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.createPost(postDto);
        return "redirect:" + ADMIN_POSTS;
    }

    private static String getUrl(String postTitle) {
        return postTitle.trim().toLowerCase().replaceAll("\\s+", "-")
                .replaceAll("[^A-Za-z0-9]", "-");
    }
}
