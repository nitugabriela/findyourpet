package com.findyourpet.findyourpet.controller;

import com.findyourpet.findyourpet.model.PetPost;
import com.findyourpet.findyourpet.service.PetPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PetPostController {

    private final PetPostService service;

    public PetPostController(PetPostService service) {
        this.service = service;
    }

    @PostMapping
    public PetPost createPost(@RequestBody PetPost post) {
        return service.savePost(post);
    }

    @GetMapping("/pending")
    public List<PetPost> getPendingPosts() {
        return service.getPendingPosts();
    }

    @GetMapping("/approved")
    public List<PetPost> getApprovedPosts() {
        return service.getApprovedPosts();
    }

    @PutMapping("/approve/{id}")
    public void approvePost(@PathVariable Long id) {
        service.approvePost(id);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        service.deletePost(id);
    }

    @PutMapping("/reject/{id}")
    public void rejectPost(@PathVariable Long id) {
        service.rejectPost(id);
    }


}
