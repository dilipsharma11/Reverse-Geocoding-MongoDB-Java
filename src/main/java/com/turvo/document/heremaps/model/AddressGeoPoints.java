package com.turvo.document.heremaps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressGeoPoints {
    public AdditionalData additionalData = new AdditionalData();
    public String State = "";
    public String Label = "";
    public String Country = "";
    public String PostalCode = "";
    public String City = "";
    public String County = "";

}
