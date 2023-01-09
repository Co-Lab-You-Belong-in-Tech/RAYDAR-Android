package ukponahiunsijeffery.example.raydar.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import ukponahiunsijeffery.example.raydar.R
import ukponahiunsijeffery.example.raydar.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_welcome, container, false)

        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,onBackPressedCallback)

        binding.goButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_welcomeFragment2_to_activityTypeFragment)
        }

        return binding.root
    }




}

