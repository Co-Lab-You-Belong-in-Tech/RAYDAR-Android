package ukponahiunsijeffery.example.raydar.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import ukponahiunsijeffery.example.raydar.R
import ukponahiunsijeffery.example.raydar.databinding.FragmentActivityTypeBinding


class ActivityTypeFragment :  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var activityType: String? = null
        val activityTypeBundle = Bundle()

        val binding: FragmentActivityTypeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_activity_type, container, false)

        binding.food.setOnClickListener { food ->
            food.isSelected = true
            binding.drinks.isSelected = false
            binding.concert.isSelected = false
            binding.mall.isSelected = false
            binding.music.isSelected = false
            binding.movies.isSelected = false
            binding.game.isSelected = false
            binding.lounge.isSelected = false
            binding.bar.isSelected = false

            activityType = "restaurant"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.drinks.setOnClickListener { drinks ->
            drinks.isSelected = true
            binding.food.isSelected = false
            binding.concert.isSelected = false
            binding.mall.isSelected = false
            binding.music.isSelected = false
            binding.movies.isSelected = false
            binding.game.isSelected = false
            binding.lounge.isSelected = false
            binding.bar.isSelected = false

            activityType = "restaurant"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.concert.setOnClickListener { concert ->
            concert.isSelected = true
            binding.drinks.isSelected = false
            binding.food.isSelected = false
            binding.mall.isSelected = false
            binding.music.isSelected = false
            binding.movies.isSelected = false
            binding.game.isSelected = false
            binding.lounge.isSelected = false
            binding.bar.isSelected = false

            activityType = "night_club"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.mall.setOnClickListener { mall ->
            mall.isSelected = true
            binding.drinks.isSelected = false
            binding.concert.isSelected = false
            binding.food.isSelected = false
            binding.music.isSelected = false
            binding.movies.isSelected = false
            binding.game.isSelected = false
            binding.lounge.isSelected = false
            binding.bar.isSelected = false

            activityType = "shopping_mall"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.music.setOnClickListener { music ->
            music.isSelected = true
            binding.drinks.isSelected = false
            binding.concert.isSelected = false
            binding.mall.isSelected = false
            binding.food.isSelected = false
            binding.movies.isSelected = false
            binding.game.isSelected = false
            binding.lounge.isSelected = false
            binding.bar.isSelected = false

            activityType = "night_club"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.movies.setOnClickListener { movies ->
            movies.isSelected = true
            binding.drinks.isSelected = false
            binding.concert.isSelected = false
            binding.mall.isSelected = false
            binding.music.isSelected = false
            binding.food.isSelected = false
            binding.game.isSelected = false
            binding.lounge.isSelected = false
            binding.bar.isSelected = false

            activityType = "movie_theater"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.game.setOnClickListener { game ->
            game.isSelected = true
            binding.drinks.isSelected = false
            binding.concert.isSelected = false
            binding.mall.isSelected = false
            binding.music.isSelected = false
            binding.movies.isSelected = false
            binding.food.isSelected = false
            binding.lounge.isSelected = false
            binding.bar.isSelected = false

            activityType = "amusement_park"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.lounge.setOnClickListener { lounge ->
            lounge.isSelected = true
            binding.drinks.isSelected = false
            binding.concert.isSelected = false
            binding.mall.isSelected = false
            binding.music.isSelected = false
            binding.movies.isSelected = false
            binding.game.isSelected = false
            binding.food.isSelected = false
            binding.bar.isSelected = false

            activityType = "lodging"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.bar.setOnClickListener { bar ->
            bar.isSelected = true
            binding.drinks.isSelected = false
            binding.concert.isSelected = false
            binding.mall.isSelected = false
            binding.music.isSelected = false
            binding.movies.isSelected = false
            binding.game.isSelected = false
            binding.lounge.isSelected = false
            binding.food.isSelected = false

            activityType = "bar"
            activityTypeBundle.putString("ACTIVITY_TYPE", activityType)
        }

        binding.nextButton.setOnClickListener{  view : View ->

            if (activityType == null){
                Toast.makeText(context, "Please choose an Activity", Toast.LENGTH_SHORT).show()
            }
            else{
                view.findNavController().navigate(R.id.
                action_activityTypeFragment_to_distanceFragment, activityTypeBundle)
            }

        }


        return binding.root
    }

}