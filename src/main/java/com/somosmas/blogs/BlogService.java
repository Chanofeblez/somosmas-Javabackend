package com.somosmas.blogs;

import com.somosmas.eventos.Evento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class BlogService {

    private BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Transactional(readOnly = true)
    public List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Blog getBlog(Long blogId){
        //Check si existe blog con ese id, si no, botamos un Error
        Blog blogExistente = blogRepository.findById(blogId)
                .orElseThrow(() -> new NoSuchElementException("Evento con ese id no existe, id: " + blogId));

        return blogExistente;
    }

    public Blog createBlog(Blog b){
        Blog blogGuardado = blogRepository.save(b);
        return blogGuardado;
    }

    public void deleteBlog(Long blogId){
        //Check si id existe, si no, imprimimos Warning
        boolean blogExiste = blogRepository.existsById(blogId);

        if (!blogExiste){
            throw new NoSuchElementException("Evento con id " + blogId + "no existe");
        }
        blogRepository.deleteById(blogId);
    }

    public Blog updateBlog(Long blogId, Blog blogAActualizar) {

        //Check si existe miembro con ese id, si no, botamos un Error
        Blog blogExistente = blogRepository.findById(blogId)
                .orElseThrow(() -> new NoSuchElementException("Blog con ese id no existe, id: " + blogId));

        //Actualizar miembro
        blogExistente.setNombre(blogAActualizar.getNombre());
        blogExistente.setImagenUrl(blogAActualizar.getImagenUrl());

        return blogExistente;
    }
}
