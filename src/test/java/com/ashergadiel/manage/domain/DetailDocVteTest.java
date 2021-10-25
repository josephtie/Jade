package com.ashergadiel.manage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ashergadiel.manage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailDocVteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailDocVte.class);
        DetailDocVte detailDocVte1 = new DetailDocVte();
        detailDocVte1.setId(1L);
        DetailDocVte detailDocVte2 = new DetailDocVte();
        detailDocVte2.setId(detailDocVte1.getId());
        assertThat(detailDocVte1).isEqualTo(detailDocVte2);
        detailDocVte2.setId(2L);
        assertThat(detailDocVte1).isNotEqualTo(detailDocVte2);
        detailDocVte1.setId(null);
        assertThat(detailDocVte1).isNotEqualTo(detailDocVte2);
    }
}
