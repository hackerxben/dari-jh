package tn.dari.service;

import tn.dari.service.dto.FournitureAdDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link tn.dari.domain.FournitureAd}.
 */
public interface FournitureAdService {

    /**
     * Save a fournitureAd.
     *
     * @param fournitureAdDTO the entity to save.
     * @return the persisted entity.
     */
    FournitureAdDTO save(FournitureAdDTO fournitureAdDTO);

    /**
     * Get all the fournitureAds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FournitureAdDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fournitureAd.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FournitureAdDTO> findOne(Long id);

    /**
     * Delete the "id" fournitureAd.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
