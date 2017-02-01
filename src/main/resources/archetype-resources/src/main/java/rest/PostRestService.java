package ${package}.rest;

import ${package}.model.CurrentUser;
import ${package}.model.Post;
import ${package}.repository.PostRepository;
import ${package}.service.PostService;
import ${package}.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.brunocvcunha.doormain.model.Post;
import org.brunocvcunha.doormain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/posts")
public class PostRestService {

    @Autowired
    private PostRepository repository;

    @RequestMapping(value = "/all")
    public List<Post> getPages() {
        Iterable<Post> findAll = repository.findAll();
        return StreamSupport.stream(findAll.spliterator(), false).collect(Collectors.toList());
    }

}
