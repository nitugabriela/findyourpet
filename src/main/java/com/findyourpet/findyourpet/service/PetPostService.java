package com.findyourpet.findyourpet.service;

import com.findyourpet.findyourpet.model.PetPost;
import com.findyourpet.findyourpet.repository.PetPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetPostService {

    private final PetPostRepository repository;

    public PetPostService(PetPostRepository repository) {
        this.repository = repository;
    }

    public PetPost savePost(PetPost post) {
        return repository.save(post);
    }

    public List<PetPost> getPendingPosts() {
        return repository.findByApprovedIsNull();
    }

    public List<PetPost> getApprovedPosts() {
        return repository.findByApprovedTrue();
    }

    public void approvePost(Long id) {
        PetPost post = repository.findById(id).orElseThrow();
        post.setApproved(true);
        repository.save(post);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public void rejectPost(Long id) {
        PetPost post = repository.findById(id).orElseThrow();
        post.setApproved(false);
        repository.save(post);
    }


}
