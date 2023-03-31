package com.turvo.document.heremaps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoNameGps {

    //@JsonProperty()
    private List<Cordinates> cordinatesList;
    private String type;
}
