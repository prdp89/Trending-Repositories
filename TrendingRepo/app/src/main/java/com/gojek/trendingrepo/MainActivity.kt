package com.gojek.trendingrepo

import android.os.Bundle
import com.gojek.trendingrepo.utils.ActivityUtils
import com.gojek.trendingrepo.view.home.RepoListFragment
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
                , false
                , RepoListFragment::class.simpleName
            )
    }
}
