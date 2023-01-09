package ukponahiunsijeffery.example.raydar.model

import com.google.android.gms.maps.model.LatLng

data class Place(
    val latLng: LatLng,
    val name: String,
    val rating: Double,
    val vicinity: String)


