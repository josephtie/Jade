package com.ashergadiel.manage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ashergadiel.manage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentAchatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentAchat.class);
        DocumentAchat documentAchat1 = new DocumentAchat();
        documentAchat1.setId(1L);
        DocumentAchat documentAchat2 = new DocumentAchat();
        documentAchat2.setId(documentAchat1.getId());
        assertThat(documentAchat1).isEqualTo(documentAchat2);
        documentAchat2.setId(2L);
        assertThat(documentAchat1).isNotEqualTo(documentAchat2);
        documentAchat1.setId(null);
        assertThat(documentAchat1).isNotEqualTo(documentAchat2);
    }
}
