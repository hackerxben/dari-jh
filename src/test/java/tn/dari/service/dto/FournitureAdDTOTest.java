package tn.dari.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tn.dari.web.rest.TestUtil;

public class FournitureAdDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FournitureAdDTO.class);
        FournitureAdDTO fournitureAdDTO1 = new FournitureAdDTO();
        fournitureAdDTO1.setId(1L);
        FournitureAdDTO fournitureAdDTO2 = new FournitureAdDTO();
        assertThat(fournitureAdDTO1).isNotEqualTo(fournitureAdDTO2);
        fournitureAdDTO2.setId(fournitureAdDTO1.getId());
        assertThat(fournitureAdDTO1).isEqualTo(fournitureAdDTO2);
        fournitureAdDTO2.setId(2L);
        assertThat(fournitureAdDTO1).isNotEqualTo(fournitureAdDTO2);
        fournitureAdDTO1.setId(null);
        assertThat(fournitureAdDTO1).isNotEqualTo(fournitureAdDTO2);
    }
}
