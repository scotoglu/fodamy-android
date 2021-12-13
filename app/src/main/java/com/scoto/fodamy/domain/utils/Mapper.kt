package com.scoto.fodamy.domain.utils

@FunctionalInterface
interface Mapper<in R, out T> {
    suspend fun mapTo(item: R): T
}