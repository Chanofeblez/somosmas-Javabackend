package com.somosmas.blogs;

import com.somosmas.eventos.Evento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogController {

    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> getBlogs() {
        List<Blog> blogs =  blogService.getBlogs();
        if( blogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable("id") Long blogId) {
        Blog blog = blogService.getBlog(blogId);
        if (blog==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(blog);
    }

    @PostMapping("/blogs/create")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog b){
        Blog blog = blogService.createBlog(b);
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable("id") Long blogId){
        blogService.deleteBlog(blogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/blogs/{blogId}")
    public Blog updateEvento(@PathVariable("blogId") Long blogId, @RequestBody Blog b){
        return blogService.updateBlog(blogId, b);
    }
}
