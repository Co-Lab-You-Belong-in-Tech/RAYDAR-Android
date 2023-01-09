package ukponahiunsijeffery.example.raydar.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.gms.location.*
import ukponahiunsijeffery.example.raydar.R
import ukponahiunsijeffery.example.raydar.databinding.FragmentDistanceBinding

class DistanceFragment : Fragment() {

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var latitudeStringValue: String? = null
    private var longitudeStringValue: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentDistanceBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_distance, container, false)

        val activityTypeBundle = requireArguments().getString("ACTIVITY_TYPE")
        val generalArrayList: ArrayList<String> = ArrayList<String>()
        var meterDistance: Int? = null

        generalArrayList.add(activityTypeBundle.toString())

        binding.distanceHomeImage.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_distanceFragment_to_welcomeFragment2)
        }

        binding.slider.addOnChangeListener { slider, value, fromUser ->

            var kmDistance = value.toInt()

            binding.distanceText.text = "$kmDistance KM"
            meterDistance = kmDistance * 1000

            if (generalArrayList.size == 1){
                generalArrayList.add(meterDistance.toString())
            }

            else{
                generalArrayList.removeAt(1)
                generalArrayList.add(meterDistance.toString())
            }

        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        binding.distanceNextButton.setOnClickListener { view : View ->

            var generalArrayListSize = generalArrayList.size

            if (generalArrayListSize == 2){

                if (latitudeStringValue != null && longitudeStringValue != null ){
                    generalArrayList.add(latitudeStringValue!!)
                    generalArrayList.add(longitudeStringValue!!)

                    var generalBundle = bundleOf("DISTANCE_GENERAL_ARRAYLIST" to generalArrayList)
                    view.findNavController().navigate(R.id.action_distanceFragment_to_priceFragment,generalBundle )
                }

                else{
                    Toast.makeText(context, "Getting Location...", Toast.LENGTH_SHORT).show()
                }

            }

            else{
                Toast.makeText(context, "Please choose a distance", Toast.LENGTH_SHORT).show()
            }

        }


        return binding.root
    }



    @SuppressLint("MissingPermission")
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when{

            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) &&
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) ->{

                if (isLocationEnabled()) {

                    mFusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null){
                                latitudeStringValue = location.latitude.toString()
                                longitudeStringValue = location.longitude.toString()
                            }
                            else{
                                requestNewLocationData()
                            }
                    }
                        .addOnFailureListener {
                            requestNewLocationData()
                        }

                }

                else{
                    showLocationNotEnabledDialog(requireContext())
                    requestNewLocationData()
                }

            }
            else -> {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    }



    private fun showLocationNotEnabledDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.location))
            .setMessage(context.getString(R.string.required_for_this_app))
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.enable_now)) { _, _ ->
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .show()
    }



    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }



    private val mLocationCallback: LocationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult != null){
                val mLastLocation = locationResult.lastLocation

                latitudeStringValue= mLastLocation.latitude.toString()
                longitudeStringValue = mLastLocation.longitude.toString()
            }

            else{
                Toast.makeText(context, "Location could not be gotten", Toast.LENGTH_SHORT).show()
            }
        }

    }



    private fun isLocationEnabled(): Boolean{

        val locationManager: LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }



}