package com.example.blog_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var fab: FloatingActionButton
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout) // Inicializar drawerLayout

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this) // Establecer el escuchador en la actividad

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.shorts -> replaceFragment(UbicationFragment())
                R.id.subscriptions -> replaceFragment(PersonalFragment())
                R.id.library -> replaceFragment(AboutFragment())
            }
            true
        }

        // Verificar el estado de inicio de sesión al abrir la actividad
        val sharedPreferences = getSharedPreferences("MY_APP_PREFS", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (!isLoggedIn) {
            startLoginActivity()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                replaceFragment(HomeFragment())
            }
            R.id.nav_settings -> {
                replaceFragment(UbicationFragment())
            }
            R.id.nav_share -> {
                replaceFragment(PersonalFragment())
            }
            R.id.nav_about -> {
                replaceFragment(AboutFragment())
            }
            R.id.nav_logout -> {
                // Eliminar estado de inicio de sesión en SharedPreferences
                val sharedPreferences = getSharedPreferences("MY_APP_PREFS", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", false)
                editor.apply()

                startLoginActivity()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun startLoginActivity() {
        val intent = Intent(this@MainActivity, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}