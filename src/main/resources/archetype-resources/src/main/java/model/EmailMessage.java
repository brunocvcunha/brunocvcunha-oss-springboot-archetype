package ${package}.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Email Message Entity
 * 
 * @author brunovolpato
 *
 */
@Entity
@Data
public class EmailMessage {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String from;

    @Column(nullable = false)
    private String fromName;

    @Column(nullable = false)
    private String to;

    @Column(nullable = false, length = 32768)
    private String content;

    @Column
    private String attachment;

}
