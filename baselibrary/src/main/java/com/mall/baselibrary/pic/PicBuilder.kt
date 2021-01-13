@file:Suppress("UNREACHABLE_CODE")

package com.mall.baselibrary.pic

import java.io.File

class PicBuilder(
        var cacheFile: File? = null,
        var isCrop: Boolean? = false,
        var cropWidth: Int? = 0,
        var cropHeight: Int? = 0,
        var providerAuthority: String? = ""
) {

    fun builder(): PicBuilder {
        requireNotNull(cacheFile) { "cacheFile no found..." }
        return this
    }

}