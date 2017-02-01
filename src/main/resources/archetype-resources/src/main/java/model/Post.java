package ${package}.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Data;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String text;
    
    @Column(nullable = false)
    private Date publishDate;

    @Column(nullable = false)
    private String uuid; // external id
    
    @Column
    private String fileName;

    
    @PrePersist
    void onPersist() {
        this.publishDate = new Date();
    }

}
