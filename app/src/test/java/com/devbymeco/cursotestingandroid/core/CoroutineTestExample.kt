package com.devbymeco.cursotestingandroid.core

import kotlinx.coroutines.test.runTest
import org.junit.Test

class CoroutineTestExample {
    private suspend fun coroutineSum(a: Int, b: Int): Int{
        return a + b
    }

    @Test
    fun coroutieSum_returnsCorrectSum() = runTest{
        val result = coroutineSum(2,2)

        assert(result == 4)
    }
}