package ukponahiunsijeffery.example.raydar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import ukponahiunsijeffery.example.raydar.model.Place

class MarkerInfoWindowAdapter(private val context:
                              Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View? {

        val newPlace = marker.tag as? Place ?: return null
        val view = LayoutInflater.from(context).inflate(R.layout.marker_info_contents, null)
        view.findViewById<TextView>(R.id.name).text = newPlace.name
        view.findViewById<TextView>(R.id.rating).text = newPlace.rating.toString()
        view.findViewById<TextView>(R.id.vicinity).text = newPlace.vicinity

        val markerInfoRatingBar = view.findViewById<RatingBar>(R.id.marker_info_window_rating_bar)
        markerInfoRatingBar.rating = newPlace.rating.toFloat()

        return view
    }


    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

}