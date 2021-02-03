package com.ran.reactive.tweet.stream.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "locations")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class GeoLocation {
	private double latitude;
    private double longitude;
    
    public static GeoLocation fromTwitter4jGeoLocation(twitter4j.GeoLocation geoLocation) {
    	if(geoLocation == null) {
    		return null;
    	}
		GeoLocation location = new GeoLocation();
		location.setLatitude(geoLocation.getLatitude());
		location.setLongitude(geoLocation.getLongitude());
		return location;
	}

}
