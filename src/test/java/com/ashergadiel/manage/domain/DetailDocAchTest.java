package com.ashergadiel.manage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ashergadiel.manage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailDocAchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailDocAch.class);
        DetailDocAch detailDocAch1 = new DetailDocAch();
        detailDocAch1.setId(1L);
        DetailDocAch detailDocAch2 = new DetailDocAch();
        detailDocAch2.setId(detailDocAch1.getId());
        assertThat(detailDocAch1).isEqualTo(detailDocAch2);
        detailDocAch2.setId(2L);
        assertThat(detailDocAch1).isNotEqualTo(detailDocAch2);
        detailDocAch1.setId(null);
        assertThat(detailDocAch1).isNotEqualTo(detailDocAch2);
    }
}
