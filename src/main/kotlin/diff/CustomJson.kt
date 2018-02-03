package diff

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier
import com.fasterxml.jackson.databind.ser.PropertyWriter
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import diff.patch.Coordinate
import diff.patch.Patch


/**
 * Created by sebasjm on 28/06/17.
 */
object CustomJson {

    val writer: ObjectWriter
    val niceWriter: ObjectWriter
    val reader: ObjectMapper

    init {

        val mapper = ObjectMapper()

        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS)
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)

        mapper.registerModule(KotlinModule())

        val module = SimpleModule()
        module.setSerializerModifier(object : BeanSerializerModifier() {
            override fun updateBuilder(config: SerializationConfig?, beanDesc: BeanDescription?, builder: BeanSerializerBuilder): BeanSerializerBuilder {
                builder.filterId = "filtered"
                return super.updateBuilder(config, beanDesc, builder)
            }

        })
        module.addSerializer(object: StdSerializer<Coordinate<*,*>>(Coordinate::class.java){
            override fun serialize(value: Coordinate<*, *>?, gen: JsonGenerator?, provider: SerializerProvider?) {
                gen?.writeString(value?.name)
            }
        })
        mapper.registerModule(module)

        val ignorableFieldNames = arrayOf("position")
        val filters = SimpleFilterProvider()
            .addFilter("filtered",SimpleBeanPropertyFilter.serializeAllExcept(*ignorableFieldNames))

        writer = mapper
            .setFilterProvider(filters)
            .writer()

        niceWriter = mapper
            .setFilterProvider(filters)
            .writerWithDefaultPrettyPrinter()

        reader = mapper
    }

}
