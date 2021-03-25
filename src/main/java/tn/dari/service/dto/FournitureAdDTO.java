package tn.dari.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link tn.dari.domain.FournitureAd} entity.
 */
public class FournitureAdDTO implements Serializable {
    
    private Long id;

    private String nameFa;

    private Float price;

    private String description;

    private String address;

    private LocalDate created;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFa() {
        return nameFa;
    }

    public void setNameFa(String nameFa) {
        this.nameFa = nameFa;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FournitureAdDTO)) {
            return false;
        }

        return id != null && id.equals(((FournitureAdDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FournitureAdDTO{" +
            "id=" + getId() +
            ", nameFa='" + getNameFa() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
