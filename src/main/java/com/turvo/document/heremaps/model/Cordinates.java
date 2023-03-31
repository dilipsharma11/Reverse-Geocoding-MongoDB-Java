package com.turvo.document.heremaps.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cordinates {
    public Double lat;
    public Double lon;

    public Cordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
