package ukponahiunsijeffery.example.raydar.screens

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import ukponahiunsijeffery.example.raydar.R
import ukponahiunsijeffery.example.raydar.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentSplashScreenBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_splash_screen, container, false)

        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_welcomeFragment2)

        }, 2000)

        return binding.root
    }
}