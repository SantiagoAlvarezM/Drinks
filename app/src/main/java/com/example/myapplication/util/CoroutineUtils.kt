package com.example.myapplication.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> throttleLatest(
    delayMs: Long = 300L,
    coroutineScope: CoroutineScope,
    function: (T) -> Unit
): (T) -> Unit {
    var job: Job? = null
    var latest: T
    return { param: T ->
        latest = param
        if (job?.isCompleted != false) {
            job = coroutineScope.launch {
                delay(delayMs)
                latest.let(function)
            }
        }
    }
}