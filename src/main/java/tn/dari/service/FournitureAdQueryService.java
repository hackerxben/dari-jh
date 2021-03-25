package tn.dari.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import tn.dari.domain.FournitureAd;
import tn.dari.domain.*; // for static metamodels
import tn.dari.repository.FournitureAdRepository;
import tn.dari.service.dto.FournitureAdCriteria;
import tn.dari.service.dto.FournitureAdDTO;
import tn.dari.service.mapper.FournitureAdMapper;

/**
 * Service for executing complex queries for {@link FournitureAd} entities in the database.
 * The main input is a {@link FournitureAdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FournitureAdDTO} or a {@link Page} of {@link FournitureAdDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FournitureAdQueryService extends QueryService<FournitureAd> {

    private final Logger log = LoggerFactory.getLogger(FournitureAdQueryService.class);

    private final FournitureAdRepository fournitureAdRepository;

    private final FournitureAdMapper fournitureAdMapper;

    public FournitureAdQueryService(FournitureAdRepository fournitureAdRepository, FournitureAdMapper fournitureAdMapper) {
        this.fournitureAdRepository = fournitureAdRepository;
        this.fournitureAdMapper = fournitureAdMapper;
    }

    /**
     * Return a {@link List} of {@link FournitureAdDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FournitureAdDTO> findByCriteria(FournitureAdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FournitureAd> specification = createSpecification(criteria);
        return fournitureAdMapper.toDto(fournitureAdRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FournitureAdDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FournitureAdDTO> findByCriteria(FournitureAdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FournitureAd> specification = createSpecification(criteria);
        return fournitureAdRepository.findAll(specification, page)
            .map(fournitureAdMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FournitureAdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FournitureAd> specification = createSpecification(criteria);
        return fournitureAdRepository.count(specification);
    }

    /**
     * Function to convert {@link FournitureAdCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FournitureAd> createSpecification(FournitureAdCriteria criteria) {
        Specification<FournitureAd> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FournitureAd_.id));
            }
            if (criteria.getNameFa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameFa(), FournitureAd_.nameFa));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), FournitureAd_.price));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), FournitureAd_.description));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), FournitureAd_.address));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), FournitureAd_.created));
            }
        }
        return specification;
    }
}
