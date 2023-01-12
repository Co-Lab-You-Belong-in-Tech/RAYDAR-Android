package ukponahiunsijeffery.example.raydar.screens

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.maps.model.LatLng
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

        val source: ImageDecoder.Source = ImageDecoder.createSource(
            resources, R.drawable.loading_gif
        )

        val drawable: Drawable = ImageDecoder.decodeDrawable(source)
        binding.loadingGif.setImageDrawable(drawable)
        (drawable as? AnimatedImageDrawable)?.start()


        val ratingGeneralArrayList = requireArguments().getStringArrayList("RATING_GENERAL_ARRAYLIST")
        val type = ratingGeneralArrayList?.get(0).toString()
        val dist = ratingGeneralArrayList?.get(1).toString()
        val passedLat = ratingGeneralArrayList?.get(2).toString()
        val passedLong = ratingGeneralArrayList?.get(3).toString()
        val passedPrice = ratingGeneralArrayList?.get(4).toString()
        val passedRating = ratingGeneralArrayList?.get(5).toString()

        val mapFragment = childFragmentManager.findFragmentById(R.id.main_map_fragment) as? SupportMapFragment
        mapFragment?.view?.visibility = View.GONE
        binding.internetError.visibility = View.GONE
        binding.gifLayout.visibility = View.VISIBLE


        coroutineScope.launch {
            var nearbyPlaceArrayList = getArrayListPlaces(type, dist, passedLat, passedLong,
                passedPrice, passedRating)

            if (nearbyPlaceArrayList.size == 1 && nearbyPlaceArrayList.get(0).vicinity == "dbdbdb"){

                (drawable as? AnimatedImageDrawable)?.stop()
                binding.gifLayout.visibility = View.GONE
                mapFragment?.view?.visibility = View.GONE
                binding.internetError.visibility = View.VISIBLE
            }

            else if(nearbyPlaceArrayList.size == 0){

                (drawable as? AnimatedImageDrawable)?.stop()
                binding.gifLayout.visibility = View.GONE
                binding.internetError.visibility = View.GONE
                mapFragment?.view?.visibility = View.VISIBLE

                mapFragment?.getMapAsync {
                    Toast.makeText(context, "No place available for your selected preference", Toast.LENGTH_LONG).show()
                }

            }


            else{
                mapFragment?.getMapAsync { googleMap ->
                    googleMap.isMyLocationEnabled = true

                    (drawable as? AnimatedImageDrawable)?.stop()
                    binding.gifLayout.visibility = View.GONE
                    binding.internetError.visibility = View.GONE
                    mapFragment.view?.visibility = View.VISIBLE

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

            try {
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
            catch (t: Throwable){
                val samplePlaceArrayList = ArrayList<Place>()

                val latitude = 0.0
                val longitude = 0.0
                val latLng = LatLng(latitude,longitude)
                val name: String = "Sample"
                val rating: Double = 0.0
                val vicinity: String = "dbdbdb"

                val placeSample = Place(latLng, name, rating, vicinity)
                samplePlaceArrayList.add(placeSample)

                return samplePlaceArrayList
            }

        }

        else{

            try {
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
            catch (t: Throwable){
                val samplePlaceArrayList = ArrayList<Place>()

                val latitude = 0.0
                val longitude = 0.0
                val latLng = LatLng(latitude,longitude)
                val name: String = "Sample"
                val rating: Double = 0.0
                val vicinity: String = "dbdbdb"

                val placeSample = Place(latLng, name, rating, vicinity)
                samplePlaceArrayList.add(placeSample)

                return samplePlaceArrayList
            }

        }


    }



}