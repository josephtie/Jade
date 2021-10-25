package com.ashergadiel.manage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ashergadiel.manage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailsStockTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailsStock.class);
        DetailsStock detailsStock1 = new DetailsStock();
        detailsStock1.setId(1L);
        DetailsStock detailsStock2 = new DetailsStock();
        detailsStock2.setId(detailsStock1.getId());
        assertThat(detailsStock1).isEqualTo(detailsStock2);
        detailsStock2.setId(2L);
        assertThat(detailsStock1).isNotEqualTo(detailsStock2);
        detailsStock1.setId(null);
        assertThat(detailsStock1).isNotEqualTo(detailsStock2);
    }
}
