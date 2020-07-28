package com.NightClubsAndTheirVisitors.project.entities;

import com.NightClubsAndTheirVisitors.project.entities.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.glass.ui.View;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Entity
@Table(name = "night_club_visitor")
public class NightClubVisitor implements UserDetails {

    @Id
    @ApiModelProperty(hidden= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitor_id", length = 6, nullable = false)
    private Long id;

    @ApiModelProperty(
            position = 1, required = true,
            name = "Set Your Name",
            example = "Andrii"
    )
    @Column(name = "visitor_name", nullable = false)
    private String username;


    @ApiModelProperty(hidden= true)
    @Column(name = "created_date", updatable = false)
    private Date created;

    @ApiModelProperty(hidden= true)
    @Column(name = "updated_date")
    @UpdateTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @ApiModelProperty(
            position = 2, required = true,
            name = "Set Password",
            example = "qwerty123"
    )
    @Column(name = "password", nullable = false)
   // @JsonIgnore //not serialisation
    private String password;

    @ApiModelProperty(
            position = 3, required = true,
            name = "Set Your Email",
            example = "andrii@gmail.com"
    )
    @Column(name = "email", nullable = false, length = 30)
    @Email
    @NotBlank
    private String email;

    @ApiModelProperty(hidden= true)
    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "visitor_id"))
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Set<RoleType> roles;

    @ApiModelProperty(hidden= true)
    public Set<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleType> roles) {
        this.roles = roles;
    }

    @Column(name = "active", nullable = false, columnDefinition = "BOOLEAN DEFAULT false" )
    private boolean active;

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "night_club_id", nullable = false)*/
    @ManyToMany(fetch = FetchType.LAZY/*, cascade = {CascadeType.ALL}*/)
    @JoinTable(
            name = "visitor_and_nightClub",
            joinColumns = { @JoinColumn(name = "visitor_id") },
            inverseJoinColumns = { @JoinColumn(name = "night_club_id") }
    )
    @JsonIgnore
    //@Fetch(value = FetchMode.SELECT)
    //@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<NightClub> nightClubs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    @ApiModelProperty(hidden= true)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden= true)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden= true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden= true)
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    @ApiModelProperty(hidden= true)
    protected void onCreate() {
        created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    @ApiModelProperty(hidden= true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<NightClub> getNightClubs() {
        return nightClubs;
    }

    public void setNightClubs(Set<NightClub> nightClubs) {
        this.nightClubs = nightClubs;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addNightClub(Set<NightClub> nightClub) {
        this.nightClubs.addAll(nightClub);
    }

    public void removeNightClub(NightClub nightClub) {
        this.nightClubs.remove(nightClub);
    }


    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public NightClubVisitor() {
    }

    public NightClubVisitor(String username, String password, String email, Set<RoleType> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.active = true;
    }

    @Override
    public String toString() {
        return "NightClubVisitor{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NightClubVisitor that = (NightClubVisitor) o;
        return username.equals(that.username) &&
                password.equals(that.password) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }
}
