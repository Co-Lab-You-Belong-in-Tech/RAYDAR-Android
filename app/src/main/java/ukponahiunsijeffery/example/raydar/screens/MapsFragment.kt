package ukponahiunsijeffery.example.raydar.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ukponahiunsijeffery.example.raydar.BuildConfig
import ukponahiunsijeffery.example.raydar.MarkerInfoWindowAdapter
import ukponahiunsijeffery.example.raydar.R
import ukponahiunsijeffery.example.raydar.api.PlacesApi
import ukponahiunsijeffery.example.raydar.databinding.FragmentMapsBinding
import ukponahiunsijeffery.example.raydar.model.Place
import ukponahiunsijeffery.example.raydar.util.parsePlaceJsonStringToPlaceArrayList


class MapsFragment : Fragment() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentMapsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_maps, container, false)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_mapsFragment2_to_welcomeFragment2)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,onBackPressedCallback)


        val ratingGeneralArrayList = requireArguments().getStringArrayList("RATING_GENERAL_ARRAYLIST")
        val type = ratingGeneralArrayList?.get(0).toString()
        val dist = ratingGeneralArrayList?.get(1).toString()
        val passedLat = ratingGeneralArrayList?.get(2).toString()
        val passedLong = ratingGeneralArrayList?.get(3).toString()
        val passedPrice = ratingGeneralArrayList?.get(4).toString()
        val passedRating = ratingGeneralArrayList?.get(5).toString()

        val mapFragment = childFragmentManager.findFragmentById(R.id.main_map_fragment) as? SupportMapFragment

        mapFragment?.getMapAsync { googleMap ->
            googleMap.isMyLocationEnabled = true
            coroutineScope.launch {

                var nearbyPlaceArrayList = getArrayListPlaces(type, dist, passedLat, passedLong,
                    passedPrice, passedRating)

                if(nearbyPlaceArrayList.size == 0){
                    Toast.makeText(context, "No place available for your selected preference", Toast.LENGTH_SHORT).show()
                }

                else{
                    googleMap.setOnMapLoadedCallback {
                        val bounds = LatLngBounds.builder()
                        nearbyPlaceArrayList.forEach { bounds.include(it.latLng) }
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
                    }

                    addMarkers(nearbyPlaceArrayList, googleMap)
                    googleMap.setInfoWindowAdapter(MarkerInfoWindowAdapter(requireContext()))
                }

            }

        }


        return binding.root
    }



    private fun addMarkers(nearbyPlaces: ArrayList<Place>, googleMap: GoogleMap) {

        nearbyPlaces.forEach { newPlace ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .position(newPlace.latLng)
                    .title(newPlace.name)
            )

            marker?.tag = newPlace
        }
    }



    private suspend fun getArrayListPlaces(activityType: String, distance: String,
                                           passedLatitude: String, passedLongitude:String,
                                           passedPrice: String, passedRating: String): ArrayList<Place>{

        var selectedPrice: String = passedPrice
        val selectedPriceInt: Int = selectedPrice.toInt()

        if (selectedPriceInt == 0){

            var getPropertiesDeferred = PlacesApi.retrofitService.getPropertiesOpenNow(
                "$passedLatitude, $passedLongitude",
                distance.toInt(),
                activityType,
                "opennow",
                BuildConfig.GOOGLE_MAPS_API_KEY)

            val placeJsonArrayList = getPropertiesDeferred.await()
            val placeArrayList: ArrayList<Place> = parsePlaceJsonStringToPlaceArrayList(placeJsonArrayList, passedRating)

            return placeArrayList
        }

        else{
            var getPropertiesWithPriceDeferred = PlacesApi.retrofitService.getPropertiesOpenNowWithPrice(
                "$passedLatitude, $passedLongitude",
                selectedPrice,
                selectedPrice,
                distance.toInt(),
                activityType,
                "opennow",
                BuildConfig.GOOGLE_MAPS_API_KEY)

            val placeJsonArrayList = getPropertiesWithPriceDeferred.await()
            val placeArrayList: ArrayList<Place> = parsePlaceJsonStringToPlaceArrayList(placeJsonArrayList, passedRating)

            return placeArrayList
        }


    }



}