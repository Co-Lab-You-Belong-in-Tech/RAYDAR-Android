package ukponahiunsijeffery.example.raydar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface PlacesApiService {

    @GET("nearbysearch/json")
    fun getPropertiesOpenNow(
        @Query("location") location: String,
        @Query("radius") radiusInMeters: Int,
        @Query("type") placeType: String,
        @QueryName currentlyOpen: String,
        @Query("key") apiKey: String):
            Deferred<String>


    @GET("nearbysearch/json")
    fun getPropertiesOpenNowWithPrice(
        @Query("location") location: String,
        @Query("maxprice") maxPrice: String,
        @Query("minprice") minPrice: String,
        @Query("radius") radiusInMeters: Int,
        @Query("type") placeType: String,
        @QueryName currentlyOpen: String,
        @Query("key") apiKey: String):
            Deferred<String>

}


object PlacesApi{

    val retrofitService: PlacesApiService by lazy {
        retrofit.create(PlacesApiService::class.java)
    }

}

