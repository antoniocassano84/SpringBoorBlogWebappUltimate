package net.javaguides.springboorblogwebapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.springboorblogwebapp.dto.CommentDto;
import net.javaguides.springboorblogwebapp.service.CommentService;
import net.javaguides.springboorblogwebapp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class CommentController {

    public static final String POST_COMMENTS = "/{postUrl}/comments";

    CommentService commentService;
    PostService postService;

    @PostMapping(POST_COMMENTS)
    public String createComment(@Valid @ModelAttribute("comment")CommentDto commentDto,
                                BindingResult bindingResult,
                                Model model,
                                @PathVariable("postUrl") String postUrl) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("comment", commentDto);
            model.addAttribute("post", postService.findPostByUrl(postUrl));
            return "blog/blog_post";
        }
        commentService.createComment(postUrl, commentDto);
        return "redirect:/post/" + postUrl;
    }
}
