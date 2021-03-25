package tn.dari.web.rest;

import tn.dari.DariApp;
import tn.dari.domain.FournitureAd;
import tn.dari.repository.FournitureAdRepository;
import tn.dari.service.FournitureAdService;
import tn.dari.service.dto.FournitureAdDTO;
import tn.dari.service.mapper.FournitureAdMapper;
import tn.dari.service.dto.FournitureAdCriteria;
import tn.dari.service.FournitureAdQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FournitureAdResource} REST controller.
 */
@SpringBootTest(classes = DariApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FournitureAdResourceIT {

    private static final String DEFAULT_NAME_FA = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FA = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED = LocalDate.ofEpochDay(-1L);

    @Autowired
    private FournitureAdRepository fournitureAdRepository;

    @Autowired
    private FournitureAdMapper fournitureAdMapper;

    @Autowired
    private FournitureAdService fournitureAdService;

    @Autowired
    private FournitureAdQueryService fournitureAdQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFournitureAdMockMvc;

    private FournitureAd fournitureAd;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FournitureAd createEntity(EntityManager em) {
        FournitureAd fournitureAd = new FournitureAd()
            .nameFa(DEFAULT_NAME_FA)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .created(DEFAULT_CREATED);
        return fournitureAd;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FournitureAd createUpdatedEntity(EntityManager em) {
        FournitureAd fournitureAd = new FournitureAd()
            .nameFa(UPDATED_NAME_FA)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .created(UPDATED_CREATED);
        return fournitureAd;
    }

    @BeforeEach
    public void initTest() {
        fournitureAd = createEntity(em);
    }

    @Test
    @Transactional
    public void createFournitureAd() throws Exception {
        int databaseSizeBeforeCreate = fournitureAdRepository.findAll().size();
        // Create the FournitureAd
        FournitureAdDTO fournitureAdDTO = fournitureAdMapper.toDto(fournitureAd);
        restFournitureAdMockMvc.perform(post("/api/fourniture-ads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fournitureAdDTO)))
            .andExpect(status().isCreated());

        // Validate the FournitureAd in the database
        List<FournitureAd> fournitureAdList = fournitureAdRepository.findAll();
        assertThat(fournitureAdList).hasSize(databaseSizeBeforeCreate + 1);
        FournitureAd testFournitureAd = fournitureAdList.get(fournitureAdList.size() - 1);
        assertThat(testFournitureAd.getNameFa()).isEqualTo(DEFAULT_NAME_FA);
        assertThat(testFournitureAd.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFournitureAd.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFournitureAd.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testFournitureAd.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createFournitureAdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fournitureAdRepository.findAll().size();

        // Create the FournitureAd with an existing ID
        fournitureAd.setId(1L);
        FournitureAdDTO fournitureAdDTO = fournitureAdMapper.toDto(fournitureAd);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournitureAdMockMvc.perform(post("/api/fourniture-ads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fournitureAdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FournitureAd in the database
        List<FournitureAd> fournitureAdList = fournitureAdRepository.findAll();
        assertThat(fournitureAdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFournitureAds() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList
        restFournitureAdMockMvc.perform(get("/api/fourniture-ads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournitureAd.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameFa").value(hasItem(DEFAULT_NAME_FA)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getFournitureAd() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get the fournitureAd
        restFournitureAdMockMvc.perform(get("/api/fourniture-ads/{id}", fournitureAd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fournitureAd.getId().intValue()))
            .andExpect(jsonPath("$.nameFa").value(DEFAULT_NAME_FA))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }


    @Test
    @Transactional
    public void getFournitureAdsByIdFiltering() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        Long id = fournitureAd.getId();

        defaultFournitureAdShouldBeFound("id.equals=" + id);
        defaultFournitureAdShouldNotBeFound("id.notEquals=" + id);

        defaultFournitureAdShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFournitureAdShouldNotBeFound("id.greaterThan=" + id);

        defaultFournitureAdShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFournitureAdShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFournitureAdsByNameFaIsEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where nameFa equals to DEFAULT_NAME_FA
        defaultFournitureAdShouldBeFound("nameFa.equals=" + DEFAULT_NAME_FA);

        // Get all the fournitureAdList where nameFa equals to UPDATED_NAME_FA
        defaultFournitureAdShouldNotBeFound("nameFa.equals=" + UPDATED_NAME_FA);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByNameFaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where nameFa not equals to DEFAULT_NAME_FA
        defaultFournitureAdShouldNotBeFound("nameFa.notEquals=" + DEFAULT_NAME_FA);

        // Get all the fournitureAdList where nameFa not equals to UPDATED_NAME_FA
        defaultFournitureAdShouldBeFound("nameFa.notEquals=" + UPDATED_NAME_FA);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByNameFaIsInShouldWork() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where nameFa in DEFAULT_NAME_FA or UPDATED_NAME_FA
        defaultFournitureAdShouldBeFound("nameFa.in=" + DEFAULT_NAME_FA + "," + UPDATED_NAME_FA);

        // Get all the fournitureAdList where nameFa equals to UPDATED_NAME_FA
        defaultFournitureAdShouldNotBeFound("nameFa.in=" + UPDATED_NAME_FA);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByNameFaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where nameFa is not null
        defaultFournitureAdShouldBeFound("nameFa.specified=true");

        // Get all the fournitureAdList where nameFa is null
        defaultFournitureAdShouldNotBeFound("nameFa.specified=false");
    }
                @Test
    @Transactional
    public void getAllFournitureAdsByNameFaContainsSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where nameFa contains DEFAULT_NAME_FA
        defaultFournitureAdShouldBeFound("nameFa.contains=" + DEFAULT_NAME_FA);

        // Get all the fournitureAdList where nameFa contains UPDATED_NAME_FA
        defaultFournitureAdShouldNotBeFound("nameFa.contains=" + UPDATED_NAME_FA);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByNameFaNotContainsSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where nameFa does not contain DEFAULT_NAME_FA
        defaultFournitureAdShouldNotBeFound("nameFa.doesNotContain=" + DEFAULT_NAME_FA);

        // Get all the fournitureAdList where nameFa does not contain UPDATED_NAME_FA
        defaultFournitureAdShouldBeFound("nameFa.doesNotContain=" + UPDATED_NAME_FA);
    }


    @Test
    @Transactional
    public void getAllFournitureAdsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where price equals to DEFAULT_PRICE
        defaultFournitureAdShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the fournitureAdList where price equals to UPDATED_PRICE
        defaultFournitureAdShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where price not equals to DEFAULT_PRICE
        defaultFournitureAdShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the fournitureAdList where price not equals to UPDATED_PRICE
        defaultFournitureAdShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultFournitureAdShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the fournitureAdList where price equals to UPDATED_PRICE
        defaultFournitureAdShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where price is not null
        defaultFournitureAdShouldBeFound("price.specified=true");

        // Get all the fournitureAdList where price is null
        defaultFournitureAdShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where price is greater than or equal to DEFAULT_PRICE
        defaultFournitureAdShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the fournitureAdList where price is greater than or equal to UPDATED_PRICE
        defaultFournitureAdShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where price is less than or equal to DEFAULT_PRICE
        defaultFournitureAdShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the fournitureAdList where price is less than or equal to SMALLER_PRICE
        defaultFournitureAdShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where price is less than DEFAULT_PRICE
        defaultFournitureAdShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the fournitureAdList where price is less than UPDATED_PRICE
        defaultFournitureAdShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where price is greater than DEFAULT_PRICE
        defaultFournitureAdShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the fournitureAdList where price is greater than SMALLER_PRICE
        defaultFournitureAdShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllFournitureAdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where description equals to DEFAULT_DESCRIPTION
        defaultFournitureAdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the fournitureAdList where description equals to UPDATED_DESCRIPTION
        defaultFournitureAdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where description not equals to DEFAULT_DESCRIPTION
        defaultFournitureAdShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the fournitureAdList where description not equals to UPDATED_DESCRIPTION
        defaultFournitureAdShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFournitureAdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the fournitureAdList where description equals to UPDATED_DESCRIPTION
        defaultFournitureAdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where description is not null
        defaultFournitureAdShouldBeFound("description.specified=true");

        // Get all the fournitureAdList where description is null
        defaultFournitureAdShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllFournitureAdsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where description contains DEFAULT_DESCRIPTION
        defaultFournitureAdShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the fournitureAdList where description contains UPDATED_DESCRIPTION
        defaultFournitureAdShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where description does not contain DEFAULT_DESCRIPTION
        defaultFournitureAdShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the fournitureAdList where description does not contain UPDATED_DESCRIPTION
        defaultFournitureAdShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllFournitureAdsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where address equals to DEFAULT_ADDRESS
        defaultFournitureAdShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the fournitureAdList where address equals to UPDATED_ADDRESS
        defaultFournitureAdShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where address not equals to DEFAULT_ADDRESS
        defaultFournitureAdShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the fournitureAdList where address not equals to UPDATED_ADDRESS
        defaultFournitureAdShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultFournitureAdShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the fournitureAdList where address equals to UPDATED_ADDRESS
        defaultFournitureAdShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where address is not null
        defaultFournitureAdShouldBeFound("address.specified=true");

        // Get all the fournitureAdList where address is null
        defaultFournitureAdShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllFournitureAdsByAddressContainsSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where address contains DEFAULT_ADDRESS
        defaultFournitureAdShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the fournitureAdList where address contains UPDATED_ADDRESS
        defaultFournitureAdShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where address does not contain DEFAULT_ADDRESS
        defaultFournitureAdShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the fournitureAdList where address does not contain UPDATED_ADDRESS
        defaultFournitureAdShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllFournitureAdsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where created equals to DEFAULT_CREATED
        defaultFournitureAdShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the fournitureAdList where created equals to UPDATED_CREATED
        defaultFournitureAdShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where created not equals to DEFAULT_CREATED
        defaultFournitureAdShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the fournitureAdList where created not equals to UPDATED_CREATED
        defaultFournitureAdShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultFournitureAdShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the fournitureAdList where created equals to UPDATED_CREATED
        defaultFournitureAdShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where created is not null
        defaultFournitureAdShouldBeFound("created.specified=true");

        // Get all the fournitureAdList where created is null
        defaultFournitureAdShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where created is greater than or equal to DEFAULT_CREATED
        defaultFournitureAdShouldBeFound("created.greaterThanOrEqual=" + DEFAULT_CREATED);

        // Get all the fournitureAdList where created is greater than or equal to UPDATED_CREATED
        defaultFournitureAdShouldNotBeFound("created.greaterThanOrEqual=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where created is less than or equal to DEFAULT_CREATED
        defaultFournitureAdShouldBeFound("created.lessThanOrEqual=" + DEFAULT_CREATED);

        // Get all the fournitureAdList where created is less than or equal to SMALLER_CREATED
        defaultFournitureAdShouldNotBeFound("created.lessThanOrEqual=" + SMALLER_CREATED);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where created is less than DEFAULT_CREATED
        defaultFournitureAdShouldNotBeFound("created.lessThan=" + DEFAULT_CREATED);

        // Get all the fournitureAdList where created is less than UPDATED_CREATED
        defaultFournitureAdShouldBeFound("created.lessThan=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFournitureAdsByCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        // Get all the fournitureAdList where created is greater than DEFAULT_CREATED
        defaultFournitureAdShouldNotBeFound("created.greaterThan=" + DEFAULT_CREATED);

        // Get all the fournitureAdList where created is greater than SMALLER_CREATED
        defaultFournitureAdShouldBeFound("created.greaterThan=" + SMALLER_CREATED);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFournitureAdShouldBeFound(String filter) throws Exception {
        restFournitureAdMockMvc.perform(get("/api/fourniture-ads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournitureAd.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameFa").value(hasItem(DEFAULT_NAME_FA)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));

        // Check, that the count call also returns 1
        restFournitureAdMockMvc.perform(get("/api/fourniture-ads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFournitureAdShouldNotBeFound(String filter) throws Exception {
        restFournitureAdMockMvc.perform(get("/api/fourniture-ads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFournitureAdMockMvc.perform(get("/api/fourniture-ads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFournitureAd() throws Exception {
        // Get the fournitureAd
        restFournitureAdMockMvc.perform(get("/api/fourniture-ads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFournitureAd() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        int databaseSizeBeforeUpdate = fournitureAdRepository.findAll().size();

        // Update the fournitureAd
        FournitureAd updatedFournitureAd = fournitureAdRepository.findById(fournitureAd.getId()).get();
        // Disconnect from session so that the updates on updatedFournitureAd are not directly saved in db
        em.detach(updatedFournitureAd);
        updatedFournitureAd
            .nameFa(UPDATED_NAME_FA)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .created(UPDATED_CREATED);
        FournitureAdDTO fournitureAdDTO = fournitureAdMapper.toDto(updatedFournitureAd);

        restFournitureAdMockMvc.perform(put("/api/fourniture-ads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fournitureAdDTO)))
            .andExpect(status().isOk());

        // Validate the FournitureAd in the database
        List<FournitureAd> fournitureAdList = fournitureAdRepository.findAll();
        assertThat(fournitureAdList).hasSize(databaseSizeBeforeUpdate);
        FournitureAd testFournitureAd = fournitureAdList.get(fournitureAdList.size() - 1);
        assertThat(testFournitureAd.getNameFa()).isEqualTo(UPDATED_NAME_FA);
        assertThat(testFournitureAd.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFournitureAd.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFournitureAd.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testFournitureAd.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingFournitureAd() throws Exception {
        int databaseSizeBeforeUpdate = fournitureAdRepository.findAll().size();

        // Create the FournitureAd
        FournitureAdDTO fournitureAdDTO = fournitureAdMapper.toDto(fournitureAd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournitureAdMockMvc.perform(put("/api/fourniture-ads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fournitureAdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FournitureAd in the database
        List<FournitureAd> fournitureAdList = fournitureAdRepository.findAll();
        assertThat(fournitureAdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFournitureAd() throws Exception {
        // Initialize the database
        fournitureAdRepository.saveAndFlush(fournitureAd);

        int databaseSizeBeforeDelete = fournitureAdRepository.findAll().size();

        // Delete the fournitureAd
        restFournitureAdMockMvc.perform(delete("/api/fourniture-ads/{id}", fournitureAd.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FournitureAd> fournitureAdList = fournitureAdRepository.findAll();
        assertThat(fournitureAdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
