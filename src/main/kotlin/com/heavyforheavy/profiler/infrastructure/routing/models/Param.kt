package com.heavyforheavy.profiler.infrastructure.routing.models

enum class Param(val key: String) {
  USER_ID("userId"),
  VIEWER_ID("viewerId"),
  ROLE_ID("roleId"),
  SERVICE_ID("serviceId"),
  OFFSET("offset"),
  LIMIT("limit"),
  SEARCH("search"),

  EMAIL("email"),
  CODE("code"),
  NEW_PASSWORD("newPassword"),

  URL("URL");

  override fun toString(): String = key
}