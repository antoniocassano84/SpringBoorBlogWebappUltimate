package net.javaguides.springboorblogwebapp.controller;

import lombok.AllArgsConstructor;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class BlogController {

    public static final String BLOG_VIEW_POSTS = "blog/view-posts";
    public static final String PAGE_SEARCH = "/page/search";
    public static final String POST_URL = "/post/{postUrl}";

    private final PostService postService;

    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        model.addAttribute("posts", postService.findAllPosts());
        return BLOG_VIEW_POSTS;
    }

//    @GetMapping(POST_URL)
//    public String showPost(@PathVariable Variable("postUrl") String postUrl, Model model) {
//        model.addAttribute("comment", new CommentDto());
//        model.addAttribute("post", postService.findPostByUrl(postUrl));
//        return BLOG_POST;
//    }

    @GetMapping(PAGE_SEARCH)
    public String searchPosts(@RequestParam(name = "query") String query, Model model) {
        model.addAttribute("posts", postService.searchPosts(query));
        return BLOG_VIEW_POSTS;
    }

}
