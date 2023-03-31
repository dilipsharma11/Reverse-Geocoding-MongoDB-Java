package com.turvo.document.heremaps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AdditionalData {
    private String key = "";
    private String value = "";

    public AdditionalData() {
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
