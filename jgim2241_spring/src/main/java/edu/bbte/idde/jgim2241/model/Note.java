package edu.bbte.idde.jgim2241.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Note extends BaseEntity {
    @Column(nullable = false, length = 512)
    private String content;

    @Column(nullable = false)
    private Date createdAt;

    //the default fetch type is eager
    @ManyToOne(optional = false)
    @JsonIgnore
    private Contact contact;
}
