package com.plcoding.weatherapp.data.remote;

import com.plcoding.weatherapp.data.remote.NominatimLocationDto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CityCoordinatesApi {
    @GET("search")
    Call<List<NominatimLocationDto>> getCityCoordinates(
            @Query("q") String cityName,
            @Query("format") String format,
            @Query("limit") int limit,
            @Query("featuretype") String featuretype
    );
}
