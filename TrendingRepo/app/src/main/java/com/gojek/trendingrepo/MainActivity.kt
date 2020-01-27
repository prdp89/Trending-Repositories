package com.gojek.trendingrepo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.gojek.trendingrepo.home.RepoListFragment
import com.gojek.trendingrepo.utils.ActivityUtils
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (null == savedInstanceState)
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager
                , RepoListFragment.newInstance()
                , R.id.container
                , true
                , RepoListFragment::class.simpleName
            )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
