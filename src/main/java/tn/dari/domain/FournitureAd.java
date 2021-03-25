package tn.dari.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A FournitureAd.
 */
@Entity
@Table(name = "fourniture_ad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FournitureAd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_fa")
    private String nameFa;

    @Column(name = "price")
    private Float price;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "created")
    private LocalDate created;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFa() {
        return nameFa;
    }

    public FournitureAd nameFa(String nameFa) {
        this.nameFa = nameFa;
        return this;
    }

    public void setNameFa(String nameFa) {
        this.nameFa = nameFa;
    }

    public Float getPrice() {
        return price;
    }

    public FournitureAd price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public FournitureAd description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public FournitureAd address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getCreated() {
        return created;
    }

    public FournitureAd created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FournitureAd)) {
            return false;
        }
        return id != null && id.equals(((FournitureAd) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FournitureAd{" +
            "id=" + getId() +
            ", nameFa='" + getNameFa() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
