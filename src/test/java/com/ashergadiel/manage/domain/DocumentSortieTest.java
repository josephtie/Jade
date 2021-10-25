package com.ashergadiel.manage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ashergadiel.manage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentSortieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentSortie.class);
        DocumentSortie documentSortie1 = new DocumentSortie();
        documentSortie1.setId(1L);
        DocumentSortie documentSortie2 = new DocumentSortie();
        documentSortie2.setId(documentSortie1.getId());
        assertThat(documentSortie1).isEqualTo(documentSortie2);
        documentSortie2.setId(2L);
        assertThat(documentSortie1).isNotEqualTo(documentSortie2);
        documentSortie1.setId(null);
        assertThat(documentSortie1).isNotEqualTo(documentSortie2);
    }
}
