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
import ukponahiunsijeffery.example.raydar.databinding.FragmentPriceBinding

class PriceFragment : Fragment() {

    var currentPriceLevel = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentPriceBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_price, container, false)

        val priceGeneralArrayList = requireArguments().getStringArrayList("DISTANCE_GENERAL_ARRAYLIST")

        if (priceGeneralArrayList?.size == 5){
            currentPriceLevel = 1
            priceGeneralArrayList.removeAt(4)
        }

        if (priceGeneralArrayList?.size == 6){
            currentPriceLevel = 1
            priceGeneralArrayList.removeAt(4)
            priceGeneralArrayList.removeAt(4)
        }

        binding.priceHomeImage.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_priceFragment_to_welcomeFragment2)
        }

        binding.priceIncrementButton.setOnClickListener { view ->

            binding.priceIncrementButton.updateButtonState()

            if (currentPriceLevel < 3){
                currentPriceLevel = currentPriceLevel + 1
            }
            else{
                currentPriceLevel = 1
            }

            if (priceGeneralArrayList?.size == 4){
                priceGeneralArrayList.add(currentPriceLevel.toString())
            }

            else{
                priceGeneralArrayList?.removeAt(4)
                priceGeneralArrayList?.add(currentPriceLevel.toString())
            }

        }

        binding.iDontMindPriceButton.setOnClickListener { view ->
            var iDontMindPriceLevel = 0

            if (priceGeneralArrayList?.size == 5){
                priceGeneralArrayList.removeAt(4)
                priceGeneralArrayList.add(iDontMindPriceLevel.toString())

                val firstIDontMindGeneralBundle = bundleOf("PRICE_GENERAL_ARRAYLIST" to priceGeneralArrayList)

                view.findNavController().navigate(R.id.action_priceFragment_to_ratingFragment
                    , firstIDontMindGeneralBundle)
            }

            else{
                priceGeneralArrayList?.add(iDontMindPriceLevel.toString())

                val secondIDontMindGeneralBundle = bundleOf("PRICE_GENERAL_ARRAYLIST" to priceGeneralArrayList)

                view.findNavController().navigate(R.id.action_priceFragment_to_ratingFragment
                    , secondIDontMindGeneralBundle)
            }

        }


        binding.priceNextButton.setOnClickListener { view ->

            var priceGeneralArrayListSize = priceGeneralArrayList?.size

            if (priceGeneralArrayListSize == 5){
                var generalBundle = bundleOf("PRICE_GENERAL_ARRAYLIST" to priceGeneralArrayList)
                view.findNavController().navigate(R.id.action_priceFragment_to_ratingFragment, generalBundle)
            }

            else{
                Toast.makeText(context, "Please choose a price or Click I don't mind", Toast.LENGTH_LONG).show()
            }

        }

        return binding.root
    }

}