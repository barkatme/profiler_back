package com.heavyforheavy.profiler.infrastructure.routing.models

import io.ktor.http.*


enum class ProfilerRoute(
  val url: String,
  val method: HttpMethod = HttpMethod.Get,
  val requireAuth: Boolean = false
) {
  //auth
  AUTH_SIGN_IN("auth/sign_in", HttpMethod.Post),
  AUTH_SIGN_UP("auth/sign_up", HttpMethod.Post),
  AUTH_SIGN_OUT("auth/sign_out", HttpMethod.Get, requireAuth = true),
  AUTH_FORGOT_PASSWORD("auth/forgot_password/{${Param.EMAIL}}", HttpMethod.Get),
  AUTH_RESTORE_PASSWORD("auth/restore_password", HttpMethod.Get),

  //user
  USER("user", requireAuth = true),
  UPDATE_USER("user", requireAuth = true, method = HttpMethod.Post),
  USER_BY_ID("user/{${Param.USER_ID}}"),

  //roles
  ROLE_PERMISSIONS("role/permissions", requireAuth = true),
  ROLE_PERMISSIONS_BY_ID("role/{${Param.ROLE_ID}}/permissions"),

  //viewers
  VIEWERS("viewers", requireAuth = true),
  VIEWERS_BY_ID("viewers/{${Param.USER_ID}}", requireAuth = true),
  DELETE_VIEWERS("viewers/{${Param.USER_ID}}", method = HttpMethod.Delete, requireAuth = true),
  DELETE_VIEWERS_BY_ID(
    "viewers/{${Param.USER_ID}}/{${Param.VIEWER_ID}}",
    method = HttpMethod.Delete,
    requireAuth = true
  ),

  //service info
  SERVICE_INFO("serviceInfo/{${Param.SERVICE_ID}}"),
  SERVICES_INFO("serviceInfo"),
  ADD_SERVICE_INFO("serviceInfo", method = HttpMethod.Put, requireAuth = true),
  UPDATE_SERVICE_INFO("serviceInfo", method = HttpMethod.Post, requireAuth = true),
  DELETE_SERVICE_INFO("serviceInfo", method = HttpMethod.Delete, requireAuth = true),
  DELETE_SERVICE_INFO_BY_ID(
    "serviceInfo/{${Param.SERVICE_ID}}",
    method = HttpMethod.Delete,
    requireAuth = true
  ),

  //saved user
  SAVED_USERS("savedUsers", requireAuth = true),
  SAVED_USERS_BY_ID("savedUsers/{${Param.USER_ID}}", requireAuth = true),
  SAVE_USER("saveUser/{${Param.USER_ID}}", requireAuth = true),
  DELETE_SAVED_USER(
    "savedUsers/{${Param.USER_ID}}",
    method = HttpMethod.Delete,
    requireAuth = true
  ),

  //user's services
  USER_AND_SERVICES("userServices", requireAuth = true),
  USER_AND_SERVICES_BY_ID("userServices/{${Param.USER_ID}}"),
  USER_SERVICES("services", requireAuth = true),
  USER_SERVICES_BY_ID("services/{${Param.USER_ID}}"),
  ADD_SERVICE("service", method = HttpMethod.Put, requireAuth = true),
  UPDATE_SERVICE("service", method = HttpMethod.Post, requireAuth = true),
  DELETE_SERVICE(
    url = "service/{${Param.SERVICE_ID}}",
    method = HttpMethod.Delete,
    requireAuth = true
  ),

  @Suppress("unused")
  //TODO: for admin usage:
  // URL Permission management
  GET_URL_PERMISSIONS(
    url = "urlPermission/{${Param.URL}}",
    method = HttpMethod.Get,
    requireAuth = true
  ),

  @Suppress("unused")
  ADD_URL_PERMISSION(
    url = "urlPermission/{${Param.URL}}",
    method = HttpMethod.Put,
    requireAuth = true
  ),

  @Suppress("unused")
  DELETE_URL_PERMISSION(
    url = "urlPermission/{${Param.URL}}",
    method = HttpMethod.Delete,
    requireAuth = true
  ),

  //TODO: for admin usage:
  // Role Permission management
  @Suppress("unused")
  GET_ROLE_PERMISSIONS(
    url = "rolePermission/{${Param.ROLE_ID}}",
    method = HttpMethod.Get,
    requireAuth = true
  ),

  @Suppress("unused")
  ADD_ROLE_PERMISSION(
    url = "rolePermission/{${Param.ROLE_ID}}",
    method = HttpMethod.Put,
    requireAuth = true
  ),

  @Suppress("unused")
  DELETE_ROLE_PERMISSION(
    url = "rolePermission/{${Param.ROLE_ID}}",
    method = HttpMethod.Delete,
    requireAuth = true
  ),
  ;
}