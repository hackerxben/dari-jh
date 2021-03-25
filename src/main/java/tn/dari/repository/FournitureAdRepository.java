package tn.dari.repository;

import tn.dari.domain.FournitureAd;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FournitureAd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournitureAdRepository extends JpaRepository<FournitureAd, Long>, JpaSpecificationExecutor<FournitureAd> {
}
