package com.sanket.feastofooddelivery.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.activities.CustomerActivity


class HomeFragment : Fragment() {

    private lateinit var dessertImg: ImageView
    private lateinit var cakeImg: ImageView
    private lateinit var pizzaImg: ImageView
    private lateinit var burgerImg: ImageView
    private lateinit var vegImg: ImageView
    private lateinit var nonVegImg: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        dessertImg = view.findViewById(R.id.desertimg)
        cakeImg = view.findViewById(R.id.cakeimg)
        pizzaImg = view.findViewById(R.id.pizzaimg)
        burgerImg = view.findViewById(R.id.burgerimg)
        vegImg = view.findViewById(R.id.vegimg)
        nonVegImg = view.findViewById(R.id.nonvegimg)

        dessertImg.setOnClickListener {
            openCategoryFragment(DessertFragment())
        }

        cakeImg.setOnClickListener {
            openCategoryFragment(CakeFragment())
        }

        pizzaImg.setOnClickListener {
            openCategoryFragment(PizzaFragment())
        }

        burgerImg.setOnClickListener {
            openCategoryFragment(BurgerFragment())
        }

        vegImg.setOnClickListener {
            openCategoryFragment(VegFragment())
        }

        nonVegImg.setOnClickListener {
            openCategoryFragment(NonVegFragment())
        }

        val navImg = view.findViewById<ImageView>(R.id.navimg)
        navImg.setOnClickListener {
            (activity as? CustomerActivity)?.openDrawerFromFragment()
        }

        val notification = view.findViewById<ImageView>(R.id.notification)
        notification.setOnClickListener {
            (activity as? CustomerActivity)?.replaceFragment(NotificationFragment())
        }

        return view
    }

    private fun openCategoryFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.customerFragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
