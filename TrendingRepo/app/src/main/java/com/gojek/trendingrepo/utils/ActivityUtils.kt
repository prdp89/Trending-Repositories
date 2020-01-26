package com.gojek.trendingrepo.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class ActivityUtils {

    companion object {

        fun addFragmentToActivity(
            fragmentManager: FragmentManager
            , fragment: Fragment, frameId: Int
            , isAddToBackStack: Boolean, tag: String?
        ) {
            val transaction = fragmentManager.beginTransaction()
            if (null != tag) {
                transaction.replace(frameId, fragment, tag)
            } else {
                transaction.replace(frameId, fragment)
            }
            if (isAddToBackStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commitAllowingStateLoss()
        }
    }
}