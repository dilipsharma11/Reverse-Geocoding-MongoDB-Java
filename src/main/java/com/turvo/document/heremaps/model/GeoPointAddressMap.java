package com.turvo.document.heremaps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoPointAddressMap {

    public Gps geoname_gps = new Gps();
    public AddressGeoPoints Address = new AddressGeoPoints(); //addressGeoPoints
    public String locationId = "";
    public Gps gps = new Gps();


}
