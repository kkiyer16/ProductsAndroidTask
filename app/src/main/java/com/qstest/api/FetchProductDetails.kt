package com.qstest.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class FetchProductDetails {
    public suspend fun fetchJson(url: String): List<String> {
        val httpClient: HttpClient = HttpClient()
        try {
            val response = httpClient.request<String> {
                url(url)
                method = HttpMethod.Get
            }
            return processData(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ArrayList<String>()
    }

    private fun processData(response: String): ArrayList<String> {
        var data = response.replace("[", "")
        data = data.replace("]", "")
        val rawIds = data.split(",")
        val productIds: ArrayList<String> = ArrayList()
        for (id in rawIds) {
            val idp = id.substring(1, id.length - 1)
            productIds.add(idp)
        }
        return productIds
    }
}