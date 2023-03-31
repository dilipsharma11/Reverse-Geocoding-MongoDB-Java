package com.turvo.document.heremaps.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gps {

    //@JsonProperty()
    //public List<Cordinates> cordinatesList = new ArrayList<>();
    public List<Double> coordinates = new ArrayList<>();
    public String type = "";
}
