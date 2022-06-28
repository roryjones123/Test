
package com.masterwork.jlptest

import com.beust.klaxon.*

private fun <T> Klaxon.convert(k: kotlin.reflect.KClass<*>, fromJson: (JsonValue) -> T, toJson: (T) -> String, isUnion: Boolean = false) =
    this.converter(object: Converter {
        @Suppress("UNCHECKED_CAST")
        override fun toJson(value: Any)        = toJson(value as T)
        override fun fromJson(jv: JsonValue)   = fromJson(jv) as Any
        override fun canConvert(cls: Class<*>) = cls == k.java || (isUnion && cls.superclass == k.java)
    })

private val klaxon = Klaxon()
    .convert(FilterType::class,          { FilterType.fromValue(it.string!!) },          { "\"${it.value}\"" })
    .convert(FacetType::class,           { FacetType.fromValue(it.string!!) },           { "\"${it.value}\"" })
    .convert(BasicColor::class,          { BasicColor.fromValue(it.string!!) },          { "\"${it.value}\"" })
    .convert(Directorate::class,         { Directorate.fromValue(it.string!!) },         { "\"${it.value}\"" })
    .convert(DisplaySpecialOffer::class, { DisplaySpecialOffer.fromValue(it.string!!) }, { "\"${it.value}\"" })
    .convert(Currency::class,            { Currency.fromValue(it.string!!) },            { "\"${it.value}\"" })
    .convert(ProductType::class,         { ProductType.fromValue(it.string!!) },         { "\"${it.value}\"" })

data class ProductRangeData (
    val showInStockOnly: Boolean,
    val products: List<Product>,
    val facets: List<Facet>,
    val results: Long,
    val pagesAvailable: Long,
    val crumbs: List<Crumb>,

    @Json(name = "dynamicBannerId")
    val dynamicBannerID: String,

    val banners: Banners,
    val categoryTitle: String,

    @Json(name = "seoBannerId")
    val seoBannerID: String,

    val seoInformation: SEOInformation,
    val pageInformation: PageInformation,
    val triggeredRules: TriggeredRules,
    val endecaCanonical: String,

    @Json(name = "baseFacetId")
    val baseFacetID: String
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<ProductRangeData>(json)
    }
}

data class Banners (
    @Json(name = "topBannerId")
    val topBannerID: String,

    @Json(name = "blockBannerId")
    val blockBannerID: String,

    @Json(name = "seoBannerId")
    val seoBannerID: String
)

data class Crumb (
    val url: String,
    val clickable: String,
    val displayName: String,
    val type: String,
    val item: String
)

data class Facet (
    val dimensionName: String,
    val name: String,
    val type: FacetType,
    val tooltip: String,
    val filterTypes: List<FilterType>,
    val details: List<Detail>
)

data class Detail (
    @Json(name = "facetId")
    val facetID: String,

    val label: String,
    val qty: String,
    val color: String,

    @Json(name = "colorSwatchUrl")
    val colorSwatchURL: String,

    val isSelected: String
)

enum class FilterType(val value: String) {
    AboveSEO("AboveSeo"),
    AlwaysShow("AlwaysShow"),
    Dynamic("Dynamic"),
    SEO("Seo");

    companion object {
        public fun fromValue(value: String): FilterType = when (value) {
            "AboveSeo"   -> AboveSEO
            "AlwaysShow" -> AlwaysShow
            "Dynamic"    -> Dynamic
            "Seo"        -> SEO
            else         -> throw IllegalArgumentException()
        }
    }
}

enum class FacetType(val value: String) {
    Single("Single");

    companion object {
        public fun fromValue(value: String): FacetType = when (value) {
            "Single" -> Single
            else     -> throw IllegalArgumentException()
        }
    }
}

data class PageInformation (
    val title: String,
    val heading: String,
    val description: String,
    val noIndex: Boolean,
    val noFollow: Boolean
)

data class Product (
    @Json(name = "productId")
    val productID: String,

    val type: ProductType,
    val title: String,
    val htmlTitle: String,
    val code: String,
    val averageRating: Double,
    val reviews: Long,
    val price: Price,
    val image: String,
    val alternativeImageUrls: List<String>,
    val displaySpecialOffer: DisplaySpecialOffer,
    val promoMessages: PromoMessages,
    val nonPromoMessage: String,

    @Json(name = "defaultSkuId")
    val defaultSkuID: String,

    val colorSwatches: List<ColorSwatch>,
    val colorSwatchSelected: Long,
    val colorWheelMessage: String,
    val outOfStock: Boolean,
    val emailMeWhenAvailable: Boolean,
    val availabilityMessage: String,
    val compare: Boolean,
    val fabric: String,
    val swatchAvailable: Boolean,
    val categoryQuickViewEnabled: Boolean,
    val swatchCategoryType: String,
    val brand: String,
    val ageRestriction: Long,
    val isInStoreOnly: Boolean,
    val isMadeToMeasure: Boolean,
    val isBundle: Boolean,
    val isProductSet: Boolean,
    val promotionalFeatures: List<Any?>,
    val features: List<Any?>,
    val dynamicAttributes: Map<String, String>,
    val directorate: Directorate,
    val futureRelease: Boolean,
    val multiSku: Boolean,
    val fabricByLength: Boolean,
    val messaging: List<Messaging>,
    val quickAddToBasket: QuickAddToBasket,
    val permanentOos: Boolean
)

data class ColorSwatch (
    val color: String,
    val basicColor: BasicColor,

    @Json(name = "colorSwatchUrl")
    val colorSwatchURL: String,

    @Json(name = "imageUrl")
    val imageURL: String,

    val isAvailable: Boolean,

    @Json(name = "skuId")
    val skuID: String
)

enum class BasicColor(val value: String) {
    Empty(""),
    Grey("Grey"),
    Silver("Silver"),
    White("White");

    companion object {
        public fun fromValue(value: String): BasicColor = when (value) {
            ""       -> Empty
            "Grey"   -> Grey
            "Silver" -> Silver
            "White"  -> White
            else     -> throw IllegalArgumentException()
        }
    }
}

enum class Directorate(val value: String) {
    TechnologyDirectora("Technology Directora");

    companion object {
        public fun fromValue(value: String): Directorate = when (value) {
            "Technology Directora" -> TechnologyDirectora
            else                   -> throw IllegalArgumentException()
        }
    }
}

enum class DisplaySpecialOffer(val value: String) {
    ClaimAnAdditional3YearGuaranteeAtNoExtraCost("Claim an additional 3 year guarantee at no extra cost"),
    Empty("");

    companion object {
        public fun fromValue(value: String): DisplaySpecialOffer = when (value) {
            "Claim an additional 3 year guarantee at no extra cost" -> ClaimAnAdditional3YearGuaranteeAtNoExtraCost
            ""                                                      -> Empty
            else                                                    -> throw IllegalArgumentException()
        }
    }
}

data class Messaging (
    val title: String,
    val type: String
)

data class Price (
    val was: String,
    val then1: String,
    val then2: String,
    val now: String,
    val uom: String,
    val currency: Currency
)

enum class Currency(val value: String) {
    Gbp("GBP");

    companion object {
        public fun fromValue(value: String): Currency = when (value) {
            "GBP" -> Gbp
            else  -> throw IllegalArgumentException()
        }
    }
}

data class PromoMessages (
    val reducedToClear: Boolean,
    val priceMatched: String,
    val offer: String,
    val customPromotionalMessage: String,
    val bundleHeadline: String,
    val customSpecialOffer: CustomSpecialOffer
)

data class CustomSpecialOffer (
    @Json(name = "customSpecialOfferId")
    val customSpecialOfferID: String? = null,

    val imageURL: String? = null,
    val linkText: String? = null,
    val linkURL: String? = null,
    val longDescription: String? = null,
    val promoImageName: String? = null,
    val siteDisplayName: DisplaySpecialOffer? = null
)

data class QuickAddToBasket (
    val showPermanentOos: Boolean,
    val showMoreInfoRedirectPDP: Boolean,
    val simpleAddToBasket: Boolean,
    val simpleMobileEmailMe: Boolean,
    val showEmailMeTriggerQV: Boolean,
    val showChooseSizeTriggerQV: Boolean
)

enum class ProductType(val value: String) {
    Product("product");

    companion object {
        public fun fromValue(value: String): ProductType = when (value) {
            "product" -> Product
            else      -> throw IllegalArgumentException()
        }
    }
}

data class SEOInformation (
    val description: String,
    val noIndex: Boolean
)

data class TriggeredRules (
    val dynamic: String,
    val seo: String
)
