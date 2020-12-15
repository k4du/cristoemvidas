package br.com.cristoemvidas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "user")
@CompoundIndex(name = "username", def = "{'username': 1}", background = true, unique = true)
@CompoundIndex(name = "username_active", def = "{'username': 1, 'active': 1}", background = true)
@CompoundIndex(name = "email", def = "{'email': 1}", background = true, unique = true)
public class User {

    @Id
    private String id;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private Boolean active;

    @JsonIgnore
    private List<Role> roles;

    @Transient
    private List<String> roleList;

    public List<String> getRoleList() {
        if(!CollectionUtils.isEmpty(this.getRoles())){
            return this.getRoles().stream().map(Role::getRole).collect(Collectors.toList());
        }
        return null;
    }
}
