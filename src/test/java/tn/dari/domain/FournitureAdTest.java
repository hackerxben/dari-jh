package tn.dari.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tn.dari.web.rest.TestUtil;

public class FournitureAdTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FournitureAd.class);
        FournitureAd fournitureAd1 = new FournitureAd();
        fournitureAd1.setId(1L);
        FournitureAd fournitureAd2 = new FournitureAd();
        fournitureAd2.setId(fournitureAd1.getId());
        assertThat(fournitureAd1).isEqualTo(fournitureAd2);
        fournitureAd2.setId(2L);
        assertThat(fournitureAd1).isNotEqualTo(fournitureAd2);
        fournitureAd1.setId(null);
        assertThat(fournitureAd1).isNotEqualTo(fournitureAd2);
    }
}
