package com.sanket.feastofooddelivery.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.customer.CLogoutFragment
import com.sanket.feastofooddelivery.customer.CProfileFragment
import com.sanket.feastofooddelivery.customer.CartFragment
import com.sanket.feastofooddelivery.customer.HomeFragment
import com.sanket.feastofooddelivery.customer.MyOrdersFragment

class CustomerActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        bottomNavigation.itemIconTintList = null
        navigationView.itemIconTintList = null


        val goToHome = intent.getBooleanExtra("goToHome", false)

        if (goToHome) {
            replaceFragment(HomeFragment())
        } else {
            replaceFragment(HomeFragment()) // default fragment
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_orders -> {
                    replaceFragment(MyOrdersFragment())
                    true
                }
                R.id.nav_cart -> {
                    replaceFragment(CartFragment())
                    true
                }
                else -> false
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_Cprofile -> {
                    replaceFragment(CProfileFragment())
                }
                R.id.nav_Clogout -> {
                    replaceFragment(CLogoutFragment())
                }
            }
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }

    fun openDrawerFromFragment() {
        drawerLayout.openDrawer(GravityCompat.END)
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.customerFragmentContainer, fragment)
            .commit()

        if (fragment is HomeFragment) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }
}
