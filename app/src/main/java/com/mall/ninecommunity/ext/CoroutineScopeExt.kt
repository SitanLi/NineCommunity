package com.mall.ninecommunity.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*

fun CoroutineScope.lifeRecycle(lifecycle: Lifecycle) {
    LifeRecycleControl(lifecycle).bind(this)
}

fun Job.lifeRecycle(lifecycle: Lifecycle) {
    LifeRecycleControl(lifecycle).bind(this)
}

fun <T> Deferred<T>.lifeRecycle(lifecycle: Lifecycle) {
    LifeRecycleControl(lifecycle).bind(this)
}

class LifeRecycleControl(private val lifecycle: Lifecycle) {
    fun bind(job: Job) {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    job.cancelChildren()
                    job.cancel()
                }
            }
        })
    }

    fun <T> bind(deferred: Deferred<T>) {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    deferred.cancelChildren()
                    deferred.cancel()
                }
            }
        })
    }

    fun bind(coroutineScope: CoroutineScope) {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    coroutineScope.cancel()
                }
            }
        })
    }
}