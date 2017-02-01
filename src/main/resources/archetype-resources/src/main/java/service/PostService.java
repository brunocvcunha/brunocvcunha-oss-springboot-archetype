package ${package}.service;

import ${package}.model.Post;
import ${package}.model.User;

import java.util.List;

public interface PostService {

    List<Post> listAll(User user);

    Post publish(Post post, Long subscriptionId);

}
