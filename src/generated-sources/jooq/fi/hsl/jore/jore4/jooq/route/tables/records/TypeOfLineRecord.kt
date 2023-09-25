/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.route.tables.records


import fi.hsl.jore.jore4.jooq.route.tables.TypeOfLine

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Row1
import org.jooq.impl.UpdatableRecordImpl


/**
 * Type of line. https://www.transmodel-cen.eu/model/index.htm?goto=2:1:3:424
 */
@Suppress("UNCHECKED_CAST")
open class TypeOfLineRecord private constructor() : UpdatableRecordImpl<TypeOfLineRecord>(TypeOfLine.TYPE_OF_LINE), Record1<String?> {

    open var typeOfLine: String
        set(value): Unit = set(0, value)
        get(): String = get(0) as String

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<String?> = super.key() as Record1<String?>

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row1<String?> = super.fieldsRow() as Row1<String?>
    override fun valuesRow(): Row1<String?> = super.valuesRow() as Row1<String?>
    override fun field1(): Field<String?> = TypeOfLine.TYPE_OF_LINE.TYPE_OF_LINE_
    override fun component1(): String = typeOfLine
    override fun value1(): String = typeOfLine

    override fun value1(value: String?): TypeOfLineRecord {
        set(0, value)
        return this
    }

    override fun values(value1: String?): TypeOfLineRecord {
        this.value1(value1)
        return this
    }

    /**
     * Create a detached, initialised TypeOfLineRecord
     */
    constructor(typeOfLine: String): this() {
        this.typeOfLine = typeOfLine
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised TypeOfLineRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.route.tables.pojos.TypeOfLine?): this() {
        if (value != null) {
            this.typeOfLine = value.typeOfLine
            resetChangedOnNotNull()
        }
    }
}