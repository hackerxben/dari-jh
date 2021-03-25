package tn.dari.service.mapper;


import tn.dari.domain.*;
import tn.dari.service.dto.FournitureAdDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FournitureAd} and its DTO {@link FournitureAdDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FournitureAdMapper extends EntityMapper<FournitureAdDTO, FournitureAd> {



    default FournitureAd fromId(Long id) {
        if (id == null) {
            return null;
        }
        FournitureAd fournitureAd = new FournitureAd();
        fournitureAd.setId(id);
        return fournitureAd;
    }
}
