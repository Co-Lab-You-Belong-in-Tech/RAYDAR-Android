package ukponahiunsijeffery.example.raydar.util

import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject
import ukponahiunsijeffery.example.raydar.model.Place


fun parsePlaceJsonStringToPlaceArrayList(rawJsonString: String, passedRating: String): ArrayList<Place>{

    val placeArrayList = ArrayList<Place>()

    val baseJsonObject = JSONObject(rawJsonString)
    val resultsJsonArray = baseJsonObject.getJSONArray("results")

    for (i in 0 until resultsJsonArray.length()) {

        val resultsObject = resultsJsonArray.getJSONObject(i)

        val geometry = resultsObject.getJSONObject("geometry")
        val location = geometry.getJSONObject("location")
        val lat = location.getDouble("lat")
        val lng = location.getDouble("lng")
        val name: String = resultsObject.getString("name")
        val vicinity: String = resultsObject.getString("vicinity")

        val selectedRating: String = passedRating
        val selectedRatingDouble: Double = selectedRating.toDouble()

        if (selectedRatingDouble == 0.0){

            var rating: Double = 0.0
            if (resultsObject.has("rating")){
                rating = resultsObject.getDouble("rating")
            }

            val place = Place(LatLng(lat,lng), name, rating, vicinity)
            placeArrayList.add(place)
        }

        if (selectedRatingDouble > 0.0){

            var rating: Double = 0.0
            if (resultsObject.has("rating")){
                rating = resultsObject.getDouble("rating")
            }

            if (rating >= selectedRatingDouble){
                val place = Place(LatLng(lat,lng), name, rating, vicinity)
                placeArrayList.add(place)
            }
        }

    }

    return placeArrayList
}

