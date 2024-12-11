package com.example.PersonalFinance.Entity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.UUID;
@Entity
@Table(name = "tbl_authority")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getAuthority() {
        return name;
    }
}
