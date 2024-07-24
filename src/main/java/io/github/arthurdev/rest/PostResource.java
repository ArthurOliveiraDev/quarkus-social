package io.github.arthurdev.rest;

import java.util.stream.Collectors;

import io.github.arthurdev.domain.model.Post;
import io.github.arthurdev.domain.model.User;
import io.github.arthurdev.domain.reposiroty.PostRepository;
import io.github.arthurdev.domain.reposiroty.UserRepository;
import io.github.arthurdev.rest.dto.CreatePostRequest;
import io.github.arthurdev.rest.dto.PostResponse;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    private UserRepository userRepository;
    private PostRepository postRepository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest postRequest) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(postRequest.getText());
        post.setUser(user);
        postRepository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var query = postRepository.find("user", Sort.by("dateTime",Sort.Direction.Descending) ,user);

        var list = query.list();

        var postResponseList = list.stream()
        .map(post -> PostResponse.fromEntity(post))
        .collect(Collectors.toList());

        return Response.ok(postResponseList).build();
    }
}
