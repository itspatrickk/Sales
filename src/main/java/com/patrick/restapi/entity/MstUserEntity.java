package com.patrick.restapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "MST_USER")
public class MstUserEntity  implements UserDetails {
    @Id
    @SequenceGenerator(name = "MST_USER_GENERATOR" , sequenceName = "SEQMSTUSER" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "MST_USER_GENERATOR")
    @Column(name = "USER_ID" , unique = true , nullable = false , precision = 10 , scale = 0)
    private Integer id;
    @Column(name = "USERNAME")
    private String username;

    private String password;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE_MST",
            joinColumns = {@JoinColumn(name = "USER_ID" , referencedColumnName="USER_ID")
                    , @JoinColumn(name = "USER_NAME" , referencedColumnName = "USERNAME") },
            inverseJoinColumns = {@JoinColumn(name = "USER_ROLE_ID" , referencedColumnName="USER_ROLE_ID")
                    , @JoinColumn(name = "ROLE_NAME" , referencedColumnName = "ROLE_ACCESS")})
    private List<MstRoleEntity> roles = new ArrayList<>();
    private Date timestamp;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleAccess()))
                .collect(Collectors.toList());
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
