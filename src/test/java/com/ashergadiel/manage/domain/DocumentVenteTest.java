package com.ashergadiel.manage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ashergadiel.manage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentVenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentVente.class);
        DocumentVente documentVente1 = new DocumentVente();
        documentVente1.setId(1L);
        DocumentVente documentVente2 = new DocumentVente();
        documentVente2.setId(documentVente1.getId());
        assertThat(documentVente1).isEqualTo(documentVente2);
        documentVente2.setId(2L);
        assertThat(documentVente1).isNotEqualTo(documentVente2);
        documentVente1.setId(null);
        assertThat(documentVente1).isNotEqualTo(documentVente2);
    }
}
