package com.mindtree.igxbridge.traderapp.vo

import com.gojek.trendingrepo.vo.Status

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?, val any: Any?) {
    companion object {
        fun <T> success(msg: String, data: T?, any: Any?): Resource<T> {
            return Resource(Status.SUCCESS, data, msg, any)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg, null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }
    }
}

