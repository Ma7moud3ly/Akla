/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.api

const val PAGE_SIZE = 50
const val FIRST_PAGE = 1

data class SearchQuery(
    var isSearch: Boolean = false,
    var firstPage: Int = FIRST_PAGE,
    var page: Int? = FIRST_PAGE,
    var size: Int? = PAGE_SIZE,
    var name: String? = null,
    var category: String? = null,
    var cuisine: String? = null
)
