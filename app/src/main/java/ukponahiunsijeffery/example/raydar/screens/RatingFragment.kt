package ukponahiunsijeffery.example.raydar.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import ukponahiunsijeffery.example.raydar.R
import ukponahiunsijeffery.example.raydar.databinding.FragmentRatingBinding

class RatingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentRatingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_rating, container, false
        )

        val ratingGeneralArrayList = requireArguments().getStringArrayList("PRICE_GENERAL_ARRAYLIST")
        val type = ratingGeneralArrayList?.get(0)
        val dist = ratingGeneralArrayList?.get(1)
        val latitude = ratingGeneralArrayList?.get(2)
        val longitude = ratingGeneralArrayList?.get(3)
        val price = ratingGeneralArrayList?.get(4)

        binding.ratingHomeImage.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_ratingFragment_to_welcomeFragment2)
        }

        binding.iDontMindRatingButton.setOnClickListener { view ->

            var iDontMindRating = 0.0

            if (ratingGeneralArrayList?.size == 6){
                ratingGeneralArrayList.removeAt(5)
                ratingGeneralArrayList.add(iDontMindRating.toString())

                val firstIDontMindRatingGeneralBundle = bundleOf("RATING_GENERAL_ARRAYLIST" to ratingGeneralArrayList)

                view.findNavController().navigate(R.id.action_ratingFragment_to_mapsFragment2,
                    firstIDontMindRatingGeneralBundle)
            }

            else{
                ratingGeneralArrayList?.add(iDontMindRating.toString())

                val secondIDontMindRatingGeneralBundle = bundleOf("RATING_GENERAL_ARRAYLIST" to ratingGeneralArrayList)

                view.findNavController().navigate(R.id.action_ratingFragment_to_mapsFragment2,
                    secondIDontMindRatingGeneralBundle)
            }

        }



        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->

            if (ratingGeneralArrayList?.size == 6){
                ratingGeneralArrayList.removeAt(5)
                ratingGeneralArrayList.add(rating.toString())
            }

            else{
                ratingGeneralArrayList?.add(rating.toString())
            }

        }

        binding.viewResultsButton.setOnClickListener { view: View ->

            if (ratingGeneralArrayList?.size == 6){
                val generalBundle = bundleOf("RATING_GENERAL_ARRAYLIST" to ratingGeneralArrayList)

                view.findNavController().navigate(R.id.action_ratingFragment_to_mapsFragment2, generalBundle)
            }

            else{
                Toast.makeText(context, "Please choose a rating or Click I don't mind", Toast.LENGTH_LONG).show()
            }

        }

        return binding.root
    }


}
