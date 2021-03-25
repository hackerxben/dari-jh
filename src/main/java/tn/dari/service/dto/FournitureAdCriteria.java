package tn.dari.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link tn.dari.domain.FournitureAd} entity. This class is used
 * in {@link tn.dari.web.rest.FournitureAdResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fourniture-ads?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FournitureAdCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameFa;

    private FloatFilter price;

    private StringFilter description;

    private StringFilter address;

    private LocalDateFilter created;

    public FournitureAdCriteria() {
    }

    public FournitureAdCriteria(FournitureAdCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nameFa = other.nameFa == null ? null : other.nameFa.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.created = other.created == null ? null : other.created.copy();
    }

    @Override
    public FournitureAdCriteria copy() {
        return new FournitureAdCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNameFa() {
        return nameFa;
    }

    public void setNameFa(StringFilter nameFa) {
        this.nameFa = nameFa;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public LocalDateFilter getCreated() {
        return created;
    }

    public void setCreated(LocalDateFilter created) {
        this.created = created;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FournitureAdCriteria that = (FournitureAdCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nameFa, that.nameFa) &&
            Objects.equals(price, that.price) &&
            Objects.equals(description, that.description) &&
            Objects.equals(address, that.address) &&
            Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nameFa,
        price,
        description,
        address,
        created
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FournitureAdCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nameFa != null ? "nameFa=" + nameFa + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
            "}";
    }

}
