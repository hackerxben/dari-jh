package tn.dari.service.impl;

import tn.dari.service.FournitureAdService;
import tn.dari.domain.FournitureAd;
import tn.dari.repository.FournitureAdRepository;
import tn.dari.service.dto.FournitureAdDTO;
import tn.dari.service.mapper.FournitureAdMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FournitureAd}.
 */
@Service
@Transactional
public class FournitureAdServiceImpl implements FournitureAdService {

    private final Logger log = LoggerFactory.getLogger(FournitureAdServiceImpl.class);

    private final FournitureAdRepository fournitureAdRepository;

    private final FournitureAdMapper fournitureAdMapper;

    public FournitureAdServiceImpl(FournitureAdRepository fournitureAdRepository, FournitureAdMapper fournitureAdMapper) {
        this.fournitureAdRepository = fournitureAdRepository;
        this.fournitureAdMapper = fournitureAdMapper;
    }

    @Override
    public FournitureAdDTO save(FournitureAdDTO fournitureAdDTO) {
        log.debug("Request to save FournitureAd : {}", fournitureAdDTO);
        FournitureAd fournitureAd = fournitureAdMapper.toEntity(fournitureAdDTO);
        fournitureAd = fournitureAdRepository.save(fournitureAd);
        return fournitureAdMapper.toDto(fournitureAd);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FournitureAdDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FournitureAds");
        return fournitureAdRepository.findAll(pageable)
            .map(fournitureAdMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FournitureAdDTO> findOne(Long id) {
        log.debug("Request to get FournitureAd : {}", id);
        return fournitureAdRepository.findById(id)
            .map(fournitureAdMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FournitureAd : {}", id);
        fournitureAdRepository.deleteById(id);
    }
}
