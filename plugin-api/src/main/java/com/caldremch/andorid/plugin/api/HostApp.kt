package com.caldremch.andorid.plugin.api

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-30
 *
 * @email caldremch@163.com
 *
 * @describe 宿主app的注解 注解在application上, for java
 *
 **/
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class HostApp {
}