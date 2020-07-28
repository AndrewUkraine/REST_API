package com.NightClubsAndTheirVisitors.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;
import java.util.Set;


//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "night_club")
public class NightClub {

    @Id
    @ApiModelProperty(hidden= true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "night_club_id", length = 6, nullable = false)
    private Long id;

    @Column(name = "night_club_name", length = 30, nullable = false)
    @Size(min = 2, max = 30)
    @NotNull
    @ApiModelProperty(
            position = 1, required = true,
            name = "Set NightClub Name",
            example = "IbizaClub"
    )
    private String nightClubName;

    @Column(name = "quantity_of_visits", length = 10, nullable = false)
    @ApiModelProperty(hidden= true)
    private int quantityOfVisits;

    @Column(name = "active", nullable = false, columnDefinition = "BOOLEAN DEFAULT false" )
    @ApiModelProperty(position = 2)
    private boolean active;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(hidden= true)
    private Date created;

    @Column(name = "updated_date")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(hidden= true)
    private Date updated;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "nightClubs")
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<NightClubVisitor> nightClubVisitors;

    public NightClub(){}

    public NightClub(String nightClubName){
        this.nightClubName = nightClubName;
        this.quantityOfVisits = 0;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNightClubName() {
        return nightClubName;
    }

    public void setNightClubName(String nightClubName) {
        this.nightClubName = nightClubName;
    }

    public int getQuantityOfVisits() {
        return quantityOfVisits;
    }

    public void setQuantityOfVisits(int quantityOfVisits) {
        this.quantityOfVisits = quantityOfVisits;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<NightClubVisitor> getNightClubVisitors() {
        return nightClubVisitors;
    }

    public void setNightClubVisitors(Set<NightClubVisitor> nightClubVisitors) {
        this.nightClubVisitors = nightClubVisitors;
    }


    public void addNightClubVisitor(NightClubVisitor nightClubVisitor) {
        this.nightClubVisitors.add(nightClubVisitor);
    }

    public void removeNightClubVisitor(NightClubVisitor nightClubVisitor) {
        this.nightClubVisitors.remove(nightClubVisitor);
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NightClub nightClub = (NightClub) o;
        return id.equals(nightClub.id) &&
                nightClubName.equals(nightClub.nightClubName);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        NightClub that = (NightClub) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nightClubName);
    }

    @Override
    public String toString() {
        return "NightClub{" +
                "id=" + id +
                ", nightClubName='" + nightClubName + '\'' +
                ", quantityOfVisits=" + quantityOfVisits +
                ", active=" + active +
                '}';
    }
}
