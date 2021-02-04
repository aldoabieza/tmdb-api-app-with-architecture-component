package com.something.subfirstjetpack.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.something.subfirstjetpack.R
import com.something.subfirstjetpack.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navCtrl = findNavController(R.id.fragment)
        val configAppBar = AppBarConfiguration.Builder(
                R.id.movieFragment, R.id.tvShowFragment, R.id.favoriteFragment
        ).build()
        setupActionBarWithNavController(navCtrl, configAppBar)
        binding.navView.setupWithNavController(navCtrl)

    }
}
