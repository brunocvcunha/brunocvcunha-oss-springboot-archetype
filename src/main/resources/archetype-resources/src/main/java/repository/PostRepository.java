package ${package}.repository;

import ${package}.model.Post;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByPublishDateGreaterThan(Date publishDate);

}
