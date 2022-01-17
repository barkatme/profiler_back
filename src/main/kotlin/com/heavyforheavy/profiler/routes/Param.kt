package com.heavyforheavy.profiler.routes

enum class Param(val key: String) {
    USER_ID("userId"),
    VIEWER_ID("viewerId"),
    ROLE_ID("roleId"),
    SERVICE_ID("serviceId"),
    OFFSET("offset"),
    LIMIT("limit"),
    SEARCH("search"),
    LINK("link");

    override fun toString(): String = key
}

fun Param.urlString(value: String) = "$name=$value"