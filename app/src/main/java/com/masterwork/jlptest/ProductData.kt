package com.masterwork.jlptest

// To parse the JSON, install Klaxon and do:
//
//   val productData = ProductData.fromJson(jsonString)

import com.beust.klaxon.*

private fun <T> Klaxon.convert(k: kotlin.reflect.KClass<*>, fromJson: (JsonValue) -> T, toJson: (T) -> String, isUnion: Boolean = false) =
    this.converter(object: Converter {
        @Suppress("UNCHECKED_CAST")
        override fun toJson(value: Any)        = toJson(value as T)
        override fun fromJson(jv: JsonValue)   = fromJson(jv) as Any
        override fun canConvert(cls: Class<*>) = cls == k.java || (isUnion && cls.superclass == k.java)
    })

private val klaxon = Klaxon()
    .convert(JsonObject::class,               { it.obj!! },                                        { it.toJsonString() })
    .convert(IncludedService::class,          { IncludedService.fromValue(it.string!!) },          { "\"${it.value}\"" })
    .convert(Currency2::class,                 { Currency2.fromValue(it.string!!) },                 { "\"${it
        .value}\"" })
    .convert(OptionalServiceType::class,      { OptionalServiceType.fromValue(it.string!!) },      { "\"${it.value}\"" })
    .convert(URL::class,                      { URL.fromValue(it.string!!) },                      { "\"${it.value}\"" })
    .convert(Homeappliancetype::class,        { Homeappliancetype.fromValue(it.string!!) },        { "\"${it.value}\"" })
    .convert(CrumbType::class,                { CrumbType.fromValue(it.string!!) },                { "\"${it.value}\"" })
    .convert(Name::class,                     { Name.fromValue(it.string!!) },                     { "\"${it.value}\"" })
    .convert(DeliveryType::class,             { DeliveryType.fromValue(it.string!!) },             { "\"${it.value}\"" })
    .convert(New::class,                      { New.fromValue(it.string!!) },                      { "\"${it.value}\"" })
    .convert(DeliveryFulfilledBy::class,      { DeliveryFulfilledBy.fromValue(it.string!!) },      { "\"${it.value}\"" })
    .convert(Title::class,                    { Title.fromValue(it.string!!) },                    { "\"${it.value}\"" })
    .convert(Image::class,                    { Image.fromValue(it.string!!) },                    { "\"${it.value}\"" })
    .convert(LinkText::class,                 { LinkText.fromValue(it.string!!) },                 { "\"${it.value}\"" })
    .convert(Uom::class,                      { Uom.fromValue(it.string!!) },                      { "\"${it.value}\"" })
    .convert(Autodose::class,                 { Autodose.fromValue(it.string!!) },                 { "\"${it.value}\"" })
    .convert(Cutlerybasket::class,            { Cutlerybasket.fromValue(it.string!!) },            { "\"${it.value}\"" })
    .convert(Dishwashersize::class,           { Dishwashersize.fromValue(it.string!!) },           { "\"${it.value}\"" })
    .convert(Energyratingoverall::class,      { Energyratingoverall.fromValue(it.string!!) },      { "\"${it.value}\"" })
    .convert(Homearea::class,                 { Homearea.fromValue(it.string!!) },                 { "\"${it.value}\"" })
    .convert(Integratedorfreestanding::class, { Integratedorfreestanding.fromValue(it.string!!) }, { "\"${it.value}\"" })
    .convert(Quietmark::class,                { Quietmark.fromValue(it.string!!) },                { "\"${it.value}\"" })
    .convert(SWFURL::class,                   { SWFURL.fromValue(it.string!!) },                   { "\"${it.value}\"" })
    .convert(AvailabilityStatus::class,       { AvailabilityStatus.fromValue(it.string!!) },       { "\"${it.value}\"" })
    .convert(Message::class,                  { Message.fromValue(it.string!!) },                  { "\"${it.value}\"" })
    .convert(Crediteligibilitystatus::class,  { Crediteligibilitystatus.fromValue(it.string!!) },  { "\"${it.value}\"" })
    .convert(TicketType::class,               { TicketType.fromValue(it.string!!) },               { "\"${it.value}\"" })
    .convert(TemplateType::class,             { TemplateType.fromValue(it.string!!) },             { "\"${it.value}\"" })
    .convert(DetailsDatumType::class,         { DetailsDatumType.fromValue(it.string!!) },         { "\"${it.value}\"" })
    .convert(Smarttechnology::class,          { Smarttechnology.fromJson(it) },                    { it.toJson() }, true)
    .convert(SwatchAvailable::class,          { SwatchAvailable.fromJson(it) },                    { it.toJson() }, true)

data class ProductData (
    val detailsData: List<DetailsDatum>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<ProductData>(json)
    }
}

data class DetailsDatum (
    @Json(name = "productId")
    val productID: String,

    val bundleProducts: List<Any?>,
    val details: Details,
    val deliveries: List<Delivery>,
    val deliverySummary: List<DeliverySummary>,
    val emailMeWhenAvailable: Boolean,
    val skus: List<Skus>,
    val title: String,
    val defaultSku: String,
    val storeOnly: Boolean,
    val type: DetailsDatumType,
    val ageRestriction: Long,
    val seoURL: String,
    val isFBL: Boolean,
    val averageRating: Double,
    val numberOfReviews: Long,
    val price: Price2,
    val code: String,
    val specialOffers: SpecialOffers,
    val displaySpecialOffer: String,
    val setDetails: SetDetails,
    val templateType: TemplateType,
    val priceBands: List<Any?>,
    val legs: List<Any?>,
    val swatchCategoryType: String,
    val deliveryFulfilledBy: DeliveryFulfilledBy,
    val additionalServices: AdditionalServices,
    val media: DetailsDatumMedia,
    val setElements: List<Any?>,
    val headingTypes: List<Any?>,
    val moreFromRange: List<Any?>,
    val promotionalFeatures: List<PromotionalFeature>,
    val setInformation: String,
    val specialOfferBundles: List<Any?>,
    val fixedRelatedProducts: List<Any?>,
    val siblingSets: List<Any?>,
    val defaultCategory: TCategory,
    val parentCategories: List<TCategory>,
    val releaseDateTimestamp: Long,
    val crumbs: List<Crumb2>,
    val seoInformation: SEOInformation2,
    val isInTopNkuCategory: Boolean,
    val brand: Brand,
    val swatchAvailable: SwatchAvailable,
    val madeToMeasureDetails: MadeToMeasureDetails,
    val isAsafShape: Boolean,
    val dynamicAttributes: DetailsDatumDynamicAttributes,
    val excludeFromLiveChat: Boolean,

    @Json(name = "webPimProductType")
    val webPIMProductType: String,

    val nonPromoMessage: String,
    val preorderable: Boolean
)

data class AdditionalServices (
    val includedServices: List<IncludedService>,
    val optionalServices: List<OptionalService>
)

enum class IncludedService(val value: String) {
    The2YearGuaranteeIncluded("2 year guarantee included");

    companion object {
        public fun fromValue(value: String): IncludedService = when (value) {
            "2 year guarantee included" -> The2YearGuaranteeIncluded
            else                        -> throw IllegalArgumentException()
        }
    }
}

data class OptionalService (
    val id: String,

    @Json(name = "associatedProductId")
    val associatedProductID: String,

    val title: String,
    val description: String,
    val price: String,
    val currency: Currency2,
    val orderOnSite: Long,
    val type: OptionalServiceType,
    val url: URL,
    val customProperties: CustomProperties
)

enum class Currency2(val value: String) {
    Gbp("GBP");

    companion object {
        public fun fromValue(value: String): Currency2 = when (value) {
            "GBP" -> Gbp
            else  -> throw IllegalArgumentException()
        }
    }
}

data class CustomProperties (
    val warrantyCustomGeneric: String? = null,
    val warrantyCustomTooltip: String? = null,
    val warrantyCustomDescription: String? = null,
    val warrantyCustomConfirm: String? = null
)

enum class OptionalServiceType(val value: String) {
    AddedWarranty("ADDED_WARRANTY"),
    Dbs("DBS");

    companion object {
        public fun fromValue(value: String): OptionalServiceType = when (value) {
            "ADDED_WARRANTY" -> AddedWarranty
            "DBS"            -> Dbs
            else             -> throw IllegalArgumentException()
        }
    }
}

enum class URL(val value: String) {
    Empty(""),
    Protectplus("/protectplus");

    companion object {
        public fun fromValue(value: String): URL = when (value) {
            ""             -> Empty
            "/protectplus" -> Protectplus
            else           -> throw IllegalArgumentException()
        }
    }
}

data class Brand (
    val name: String,
    val logo: String
)

data class Crumb2 (
    val type: CrumbType,
    val displayName: Homeappliancetype,
    val item: String,
    val clickable: String
)

enum class Homeappliancetype(val value: String) {
    Dishwashers("Dishwashers"),
    Electricals("Electricals");

    companion object {
        public fun fromValue(value: String): Homeappliancetype = when (value) {
            "Dishwashers" -> Dishwashers
            "Electricals" -> Electricals
            else          -> throw IllegalArgumentException()
        }
    }
}

enum class CrumbType(val value: String) {
    Catalogue("CATALOGUE");

    companion object {
        public fun fromValue(value: String): CrumbType = when (value) {
            "CATALOGUE" -> Catalogue
            else        -> throw IllegalArgumentException()
        }
    }
}

data class TCategory (
    val id: String,
    val name: Name
)

enum class Name(val value: String) {
    DishwasherOffers("Dishwasher Offers"),
    Dishwashers("Dishwashers");

    companion object {
        public fun fromValue(value: String): Name = when (value) {
            "Dishwasher Offers" -> DishwasherOffers
            "Dishwashers"       -> Dishwashers
            else                -> throw IllegalArgumentException()
        }
    }
}

data class Delivery (
    val deliveryType: DeliveryType,
    val options: List<Option>
)

enum class DeliveryType(val value: String) {
    Uk("UK");

    companion object {
        public fun fromValue(value: String): DeliveryType = when (value) {
            "UK" -> Uk
            else -> throw IllegalArgumentException()
        }
    }
}

data class Option (
    val id: String,
    val price: String,
    val currency: Currency2,
    val shortDescription: String,
    val standardDescription: String,
    val date: String,
    val dateMessage: String,
    val trialMessage: String,
    val isApprovedSupplier: Boolean,
    val leadTime: Long,
    val cutOffTime: Long,
    val newShortDescription: New,
    val newStandardDescription: String,
    val newPriority: Long
)

enum class New(val value: String) {
    NextDayDelivery("Next Day Delivery"),
    StandardDelivery("Standard Delivery");

    companion object {
        public fun fromValue(value: String): New = when (value) {
            "Next Day Delivery" -> NextDayDelivery
            "Standard Delivery" -> StandardDelivery
            else                -> throw IllegalArgumentException()
        }
    }
}

enum class DeliveryFulfilledBy(val value: String) {
    GreenVanFleet("greenVanFleet");

    companion object {
        public fun fromValue(value: String): DeliveryFulfilledBy = when (value) {
            "greenVanFleet" -> GreenVanFleet
            else            -> throw IllegalArgumentException()
        }
    }
}

data class DeliverySummary (
    val deliveryType: DeliveryType,
    val title: Title,
    val price: String,
    val currency: Currency2,
    val summary: String,
    val trialMessage: String,
    val newTitle: New,
    val newSummary: String,
    val newPriority: Long,

    @Json(name = "newOptionId")
    val newOptionID: String
)

enum class Title(val value: String) {
    UKDelivery("UK delivery");

    companion object {
        public fun fromValue(value: String): Title = when (value) {
            "UK delivery" -> UKDelivery
            else          -> throw IllegalArgumentException()
        }
    }
}

data class Details (
    val returns: String,
    val returnsHeadline: String,
    val termsAndConditions: String,
    val productInformation: String,
    val features: List<Feature>,
    val careGuide: List<Any?>,
    val featuredArticles: List<BuyingGuide>,
    val editorsNotes: String,
    val buyingGuides: List<BuyingGuide>,
    val sizeGuides: List<Any?>,
    val weLikeItBecause: String
)

data class BuyingGuide (
    val title: LinkText,
    val image: Image,

    @Json(name = "linkUrl")
    val linkURL: String,

    val linkText: LinkText,
    val longDescription: String,

    @Json(name = "pdfUrl")
    val pdfURL: String? = null
)

enum class Image(val value: String) {
    JohnlewisScene7COMIsImageJohnLewisElectricalMedIcon("//johnlewis.scene7.com/is/image/JohnLewis/electrical_med_icon?");

    companion object {
        public fun fromValue(value: String): Image = when (value) {
            "//johnlewis.scene7.com/is/image/JohnLewis/electrical_med_icon?" -> JohnlewisScene7COMIsImageJohnLewisElectricalMedIcon
            else                                                             -> throw IllegalArgumentException()
        }
    }
}

enum class LinkText(val value: String) {
    BuiltInAppliancesBuyingGuide("Built-in Appliances Buying Guide"),
    DishwashersBuyingGuide("Dishwashers Buying Guide");

    companion object {
        public fun fromValue(value: String): LinkText = when (value) {
            "Built-in Appliances Buying Guide" -> BuiltInAppliancesBuyingGuide
            "Dishwashers Buying Guide"         -> DishwashersBuyingGuide
            else                               -> throw IllegalArgumentException()
        }
    }
}

data class Feature (
    val groupName: String,
    val attributes: List<Attribute>
)

data class Attribute (
    val value: String,
    val values: List<String>,
    val multivalued: Boolean,
    val id: String,
    val name: String,
    val toolTip: String,
    val uom: Uom
)

enum class Uom(val value: String) {
    Empty(""),
    M("m");

    companion object {
        public fun fromValue(value: String): Uom = when (value) {
            ""   -> Empty
            "m"  -> M
            else -> throw IllegalArgumentException()
        }
    }
}

data class DetailsDatumDynamicAttributes (
    val noiselevelrating: String? = null,
    val timeremainingindicator: Autodose,

    @Json(name = "weightedenergyconsumptionper100cyclesforecocycle")
    val weightedenergyconsumptionper100Cyclesforecocycle: String,

    val integratedorfreestanding: Integratedorfreestanding,
    val smarttechnology: Smarttechnology,
    val cutlerybasket: Cutlerybasket,
    val dishwashersize: Dishwashersize,
    val saltlevelindicator: Autodose,
    val floodprotection: Autodose,
    val dryingsystem: String? = null,
    val automaticloadadjustment: Autodose,
    val noiselevel: String,
    val digitaldisplay: Autodose,
    val delicatewash: Autodose? = null,
    val quickwash: Autodose,
    val homearea: Homearea,
    val energyratingoverall: Energyratingoverall,
    val onlineexclusive: String? = null,
    val childlock: Autodose,
    val timerdelay: String? = null,
    val dryingperformance: String? = null,
    val homeappliancetype: Homeappliancetype,
    val cycledurationatratedcapacityfortheecocycle: String,
    val programsequenceindicator: String,
    val adjustableracking: String? = null,
    val homeappliancefeatures: List<String>? = null,
    val quietmark: Quietmark,
    val noofprograms: String,
    val guarantee: String? = null,
    val placesettings: String,
    val rinseaidindicator: Autodose,
    val slimdepth: Autodose,
    val weightedwaterconsumptionfortheecocycle: String,
    val energyconsumptionpercycle: String? = null,
    val international: String? = null,
    val amperage: String? = null,
    val weight: String? = null,
    val invertermotor: Autodose? = null,
    val installationrequired: String? = null,
    val quickwashcycletime: String? = null,
    val countryoforigin: String? = null,
    val widthbuiltinovens: String? = null,
    val annualrunningcost: String? = null,
    val autodose: Autodose? = null,
    val estimatedannualwaterconsumption: String? = null,
    val watersupplyfilltype: String? = null,
    val fittingsincluded: String? = null,
    val waterconsumptionstandardcycle: String? = null,
    val estimatedannualenergyconsumption: String? = null,
    val halfloadoption: Autodose? = null,
    val combinedaperturedimensions: String? = null,
    val adjustable: String? = null,
    val drainagefacilityavailable: Autodose? = null,
    val careinstructions: String? = null
)

enum class Autodose(val value: String) {
    No("NO"),
    Yes("YES");

    companion object {
        public fun fromValue(value: String): Autodose = when (value) {
            "NO"  -> No
            "YES" -> Yes
            else  -> throw IllegalArgumentException()
        }
    }
}

enum class Cutlerybasket(val value: String) {
    Basket("Basket"),
    BasketTray("Basket & Tray"),
    Tray("Tray");

    companion object {
        public fun fromValue(value: String): Cutlerybasket = when (value) {
            "Basket"        -> Basket
            "Basket & Tray" -> BasketTray
            "Tray"          -> Tray
            else            -> throw IllegalArgumentException()
        }
    }
}

enum class Dishwashersize(val value: String) {
    Full("Full"),
    Slimline("Slimline");

    companion object {
        public fun fromValue(value: String): Dishwashersize = when (value) {
            "Full"     -> Full
            "Slimline" -> Slimline
            else       -> throw IllegalArgumentException()
        }
    }
}

enum class Energyratingoverall(val value: String) {
    D("D"),
    E("E"),
    F("F");

    companion object {
        public fun fromValue(value: String): Energyratingoverall = when (value) {
            "D"  -> D
            "E"  -> E
            "F"  -> F
            else -> throw IllegalArgumentException()
        }
    }
}

enum class Homearea(val value: String) {
    Cook("Cook"),
    LaundryCleaning("Laundry & Cleaning");

    companion object {
        public fun fromValue(value: String): Homearea = when (value) {
            "Cook"               -> Cook
            "Laundry & Cleaning" -> LaundryCleaning
            else                 -> throw IllegalArgumentException()
        }
    }
}

enum class Integratedorfreestanding(val value: String) {
    Freestanding("Freestanding"),
    Integrated("Integrated");

    companion object {
        public fun fromValue(value: String): Integratedorfreestanding = when (value) {
            "Freestanding" -> Freestanding
            "Integrated"   -> Integrated
            else           -> throw IllegalArgumentException()
        }
    }
}

enum class Quietmark(val value: String) {
    NotAccredited("Not Accredited");

    companion object {
        public fun fromValue(value: String): Quietmark = when (value) {
            "Not Accredited" -> NotAccredited
            else             -> throw IllegalArgumentException()
        }
    }
}

sealed class Smarttechnology {
    class StringArrayValue(val value: List<String>) : Smarttechnology()
    class StringValue(val value: String)            : Smarttechnology()

    public fun toJson(): String = klaxon.toJsonString(when (this) {
        is StringArrayValue -> this.value
        is StringValue      -> this.value
    })

    companion object {
        public fun fromJson(jv: JsonValue): Smarttechnology = when (jv.inside) {
            is JsonArray<*> -> StringArrayValue(jv.array?.let { klaxon.parseFromJsonArray<String>(it) }!!)
            is String       -> StringValue(jv.string!!)
            else            -> throw IllegalArgumentException()
        }
    }
}

data class MadeToMeasureDetails (
    val type: String,
    val styles: String
)

data class DetailsDatumMedia (
    val images: Images,

    @Json(name = "360images")
    val the360Images: The360Images,

    val videos: Videos
)

data class Images (
    val altText: String,
    val urls: List<String>
)

data class The360Images (
    @Json(name = "swfUrl")
    val swfURL: SWFURL,

    val urls: List<Any?>
)

enum class SWFURL(val value: String) {
    JohnlewisScene7COMIsContentJohnLewis360DegreeView("//johnlewis.scene7.com/is/content/JohnLewis/360-degree-view");

    companion object {
        public fun fromValue(value: String): SWFURL = when (value) {
            "//johnlewis.scene7.com/is/content/JohnLewis/360-degree-view" -> JohnlewisScene7COMIsContentJohnLewis360DegreeView
            else                                                          -> throw IllegalArgumentException()
        }
    }
}

data class Videos (
    val videosList: List<VideosList>? = null,
    val videoHost: String? = null,
    val videoImagePath: String? = null,

    @Json(name = "prod_vid_thmb")
    val prodVidThmb: String? = null,

    val videoHeight: String? = null,
    val videoWidth: String? = null,
    val imgAltText: String? = null
)

data class VideosList (
    val videoType: String,
    val type: String,
    val name: String,
    val videoImageURL: String,
    val url: String
)

data class Price2 (
    val was: String,
    val then1: String,
    val then2: String,
    val now: String,
    val uom: String,
    val currency: Currency2
)

data class PromotionalFeature (
    val title: String,

    @Json(name = "iconUrl")
    val iconURL: String,

    @Json(name = "linkUrl")
    val linkURL: String,

    val description: String,
    val longDescription: String
)

data class SEOInformation2 (
    val title: String,
    val description: String
)

typealias SetDetails = JsonObject

data class Skus (
    val id: String,
    val skuTitle: String,
    val shortSkuTitle: String,
    val color: String,
    val size: String,
    val sizeHeadline: String,

    @Json(name = "swatchUrl")
    val swatchURL: String,

    val availability: Availability,
    val price: Price2,
    val code: String,
    val leadTime: String,

    @Json(name = "d2cDeliveryLeadTime")
    val d2CDeliveryLeadTime: String,

    val media: SkusMedia,
    val brandName: String,
    val unitPriceInfo: SetDetails,
    val priceBand: String,
    val dynamicAttributes: SkusDynamicAttributes,
    val ticketType: TicketType,

    @Json(name = "mainframeProductId")
    val mainframeProductID: String
)

data class Availability (
    val stockLevel: Long,
    val availabilityStatus: AvailabilityStatus,
    val message: Message
)

enum class AvailabilityStatus(val value: String) {
    Instock("INSTOCK"),
    Outofstock("OUTOFSTOCK");

    companion object {
        public fun fromValue(value: String): AvailabilityStatus = when (value) {
            "INSTOCK"    -> Instock
            "OUTOFSTOCK" -> Outofstock
            else         -> throw IllegalArgumentException()
        }
    }
}

enum class Message(val value: String) {
    CurrentlyInStockOnline("Currently in stock online"),
    Only2InStockOnline("Only 2 in stock online"),
    OutOfStock("Out of stock");

    companion object {
        public fun fromValue(value: String): Message = when (value) {
            "Currently in stock online" -> CurrentlyInStockOnline
            "Only 2 in stock online"    -> Only2InStockOnline
            "Out of stock"              -> OutOfStock
            else                        -> throw IllegalArgumentException()
        }
    }
}

data class SkusDynamicAttributes (
    val manufacturerpartnumbermpn: String? = null,
    val crediteligibilitystatus: Crediteligibilitystatus,
    val modelnamenumber: String,
    val range: String? = null,
    val creditofferingids: String? = null
)

enum class Crediteligibilitystatus(val value: String) {
    Core("Core");

    companion object {
        public fun fromValue(value: String): Crediteligibilitystatus = when (value) {
            "Core" -> Core
            else   -> throw IllegalArgumentException()
        }
    }
}

data class SkusMedia (
    val images: Images,

    @Json(name = "360images")
    val the360Images: The360Images
)

enum class TicketType(val value: String) {
    T("T");

    companion object {
        public fun fromValue(value: String): TicketType = when (value) {
            "T"  -> T
            else -> throw IllegalArgumentException()
        }
    }
}

data class SpecialOffers (
    val priceMatched: String,
    val offer: String,
    val customPromotionalMessage: String,
    val bundleHeadline: String,
    val customSpecialOffer: SetDetails
)

sealed class SwatchAvailable {
    class BoolValue(val value: Boolean)  : SwatchAvailable()
    class StringValue(val value: String) : SwatchAvailable()

    public fun toJson(): String = klaxon.toJsonString(when (this) {
        is BoolValue   -> this.value
        is StringValue -> this.value
    })

    companion object {
        public fun fromJson(jv: JsonValue): SwatchAvailable = when (jv.inside) {
            is Boolean -> BoolValue(jv.boolean!!)
            is String  -> StringValue(jv.string!!)
            else       -> throw IllegalArgumentException()
        }
    }
}

enum class TemplateType(val value: String) {
    MediaEpicProdtemplate("media-epic-prodtemplate");

    companion object {
        public fun fromValue(value: String): TemplateType = when (value) {
            "media-epic-prodtemplate" -> MediaEpicProdtemplate
            else                      -> throw IllegalArgumentException()
        }
    }
}

enum class DetailsDatumType(val value: String) {
    Standard("standard");

    companion object {
        public fun fromValue(value: String): DetailsDatumType = when (value) {
            "standard" -> Standard
            else       -> throw IllegalArgumentException()
        }
    }
}
