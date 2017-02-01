package ${package}.repository;

import ${package}.model.EmailMessage;

import org.springframework.data.repository.CrudRepository;

public interface EmailMessageRepository extends CrudRepository<EmailMessage, Long> {
}
