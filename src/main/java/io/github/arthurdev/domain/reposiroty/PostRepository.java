package io.github.arthurdev.domain.reposiroty;

import io.github.arthurdev.domain.model.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post>{
    
}
