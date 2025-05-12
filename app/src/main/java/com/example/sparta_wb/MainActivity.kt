package com.example.sparta_wb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.sparta_wb.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Полностью скрыть тулбар, если не используешь вообще
        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        // Удаляем эту строку, чтобы ActionBar не подключался заново:
        // setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment,
                R.id.login_or_SignUpFragment,
                R.id.detailsFragment,
                R.id.registrationFragment -> {
                    navView.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }
    }
}
