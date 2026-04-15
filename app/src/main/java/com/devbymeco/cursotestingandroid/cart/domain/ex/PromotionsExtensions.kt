package com.devbymeco.cursotestingandroid.cart.domain.ex

import com.devbymeco.cursotestingandroid.productlist.domain.model.Promotion
import java.time.Instant

fun List<Promotion>.activeAt(now: Instant):List<Promotion> = this.filter {
    it.startTime <= now && it.endTime >= now
}