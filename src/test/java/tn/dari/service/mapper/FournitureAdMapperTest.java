package tn.dari.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FournitureAdMapperTest {

    private FournitureAdMapper fournitureAdMapper;

    @BeforeEach
    public void setUp() {
        fournitureAdMapper = new FournitureAdMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fournitureAdMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fournitureAdMapper.fromId(null)).isNull();
    }
}
