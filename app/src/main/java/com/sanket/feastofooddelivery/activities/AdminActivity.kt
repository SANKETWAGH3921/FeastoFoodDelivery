package com.sanket.feastofooddelivery.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.sanket.feastofooddelivery.R
import com.sanket.feastofooddelivery.admin.AddItemFragment
import com.sanket.feastofooddelivery.admin.AdminDashboardFragment
import com.sanket.feastofooddelivery.admin.LogoutFragment
import com.sanket.feastofooddelivery.admin.OrdersFragment
import com.sanket.feastofooddelivery.admin.ProfileFragment


class AdminActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        bottomNavigation.itemIconTintList = null
        navigationView.itemIconTintList = null
        replaceFragment(AdminDashboardFragment())

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    replaceFragment(AdminDashboardFragment())
                    true
                }
                R.id.nav_add_item -> {
                    replaceFragment(AddItemFragment())
                    true
                }
                R.id.nav_orders -> {
                    replaceFragment(OrdersFragment())
                    true
                }
                else -> false
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                }
                R.id.nav_logout -> {
                    replaceFragment(LogoutFragment())
                }
            }
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }

    fun openDrawerFromFragment() {
        drawerLayout.openDrawer(GravityCompat.END)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.adminFragmentContainer, fragment)
            .commit()

        // Enable drawer only in AdminDashboardFragment
        if (fragment is AdminDashboardFragment) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }
}