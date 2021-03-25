package tn.dari.web.rest;

import tn.dari.service.FournitureAdService;
import tn.dari.web.rest.errors.BadRequestAlertException;
import tn.dari.service.dto.FournitureAdDTO;
import tn.dari.service.dto.FournitureAdCriteria;
import tn.dari.service.FournitureAdQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link tn.dari.domain.FournitureAd}.
 */
@RestController
@RequestMapping("/api")
public class FournitureAdResource {

    private final Logger log = LoggerFactory.getLogger(FournitureAdResource.class);

    private static final String ENTITY_NAME = "fournitureAd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FournitureAdService fournitureAdService;

    private final FournitureAdQueryService fournitureAdQueryService;

    public FournitureAdResource(FournitureAdService fournitureAdService, FournitureAdQueryService fournitureAdQueryService) {
        this.fournitureAdService = fournitureAdService;
        this.fournitureAdQueryService = fournitureAdQueryService;
    }

    /**
     * {@code POST  /fourniture-ads} : Create a new fournitureAd.
     *
     * @param fournitureAdDTO the fournitureAdDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fournitureAdDTO, or with status {@code 400 (Bad Request)} if the fournitureAd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fourniture-ads")
    public ResponseEntity<FournitureAdDTO> createFournitureAd(@RequestBody FournitureAdDTO fournitureAdDTO) throws URISyntaxException {
        log.debug("REST request to save FournitureAd : {}", fournitureAdDTO);
        if (fournitureAdDTO.getId() != null) {
            throw new BadRequestAlertException("A new fournitureAd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FournitureAdDTO result = fournitureAdService.save(fournitureAdDTO);
        return ResponseEntity.created(new URI("/api/fourniture-ads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fourniture-ads} : Updates an existing fournitureAd.
     *
     * @param fournitureAdDTO the fournitureAdDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fournitureAdDTO,
     * or with status {@code 400 (Bad Request)} if the fournitureAdDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fournitureAdDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fourniture-ads")
    public ResponseEntity<FournitureAdDTO> updateFournitureAd(@RequestBody FournitureAdDTO fournitureAdDTO) throws URISyntaxException {
        log.debug("REST request to update FournitureAd : {}", fournitureAdDTO);
        if (fournitureAdDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FournitureAdDTO result = fournitureAdService.save(fournitureAdDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fournitureAdDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fourniture-ads} : get all the fournitureAds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fournitureAds in body.
     */
    @GetMapping("/fourniture-ads")
    public ResponseEntity<List<FournitureAdDTO>> getAllFournitureAds(FournitureAdCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FournitureAds by criteria: {}", criteria);
        Page<FournitureAdDTO> page = fournitureAdQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fourniture-ads/count} : count all the fournitureAds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fourniture-ads/count")
    public ResponseEntity<Long> countFournitureAds(FournitureAdCriteria criteria) {
        log.debug("REST request to count FournitureAds by criteria: {}", criteria);
        return ResponseEntity.ok().body(fournitureAdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fourniture-ads/:id} : get the "id" fournitureAd.
     *
     * @param id the id of the fournitureAdDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fournitureAdDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fourniture-ads/{id}")
    public ResponseEntity<FournitureAdDTO> getFournitureAd(@PathVariable Long id) {
        log.debug("REST request to get FournitureAd : {}", id);
        Optional<FournitureAdDTO> fournitureAdDTO = fournitureAdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fournitureAdDTO);
    }

    /**
     * {@code DELETE  /fourniture-ads/:id} : delete the "id" fournitureAd.
     *
     * @param id the id of the fournitureAdDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fourniture-ads/{id}")
    public ResponseEntity<Void> deleteFournitureAd(@PathVariable Long id) {
        log.debug("REST request to delete FournitureAd : {}", id);
        fournitureAdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
