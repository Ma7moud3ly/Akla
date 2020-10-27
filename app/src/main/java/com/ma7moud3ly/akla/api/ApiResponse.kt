/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.api

data class ApiResponse(
    var recipes: List<Recipe>?,
    var count: Int = 0,
    var page: Int = 0,
    var max_page: Int = 0
)


