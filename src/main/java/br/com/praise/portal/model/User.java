package br.com.praise.portal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users_portal")
public class User {
    
    @Id
    @JsonProperty("_id")
    private String ID;
    private String name;
    private String key;
    private String email;
    private String password;

    public User(String id, String name, String key, String email, String password) {
        this.ID = id;
        this.name = name;
        this.key = key;
        this.email = email;
        this.password = password;
    }

    public User(String name, String key) {
        this.name = name;
        this.key = key;
        this.email = email;
        this.password = password;
    }

    public User() {}

}

