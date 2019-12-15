package com.goda.marvel.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.goda.marvel.R
import com.goda.marvel.presentation.main.character_list.newCharacterListFragment

class MainActivity : AppCompatActivity() {
    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }
    private val characterListFragment by lazy { newCharacterListFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 /*       if (savedInstanceState == null)
            replaceFragment(R.id.container, cryptoListFragment, CRYPTO_LIST_FRAGMENT_TAG)*/
    }
}
