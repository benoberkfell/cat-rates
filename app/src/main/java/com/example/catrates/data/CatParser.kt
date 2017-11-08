package com.example.catrates.data

import com.example.catrates.catapi.CatResponse
import com.nytimes.android.external.store3.base.Parser
import okio.BufferedSource
import org.simpleframework.xml.core.Persister

class CatParser : Parser<BufferedSource, CatResponse> {
    override fun apply(raw: BufferedSource): CatResponse {
        return Persister().read(CatResponse::class.java, raw.inputStream())
    }
}