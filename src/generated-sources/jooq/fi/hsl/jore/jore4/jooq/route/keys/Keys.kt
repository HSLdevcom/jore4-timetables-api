/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.route.keys


import fi.hsl.jore.jore4.jooq.route.tables.Direction
import fi.hsl.jore.jore4.jooq.route.tables.TypeOfLine
import fi.hsl.jore.jore4.jooq.route.tables.records.DirectionRecord
import fi.hsl.jore.jore4.jooq.route.tables.records.TypeOfLineRecord

import org.jooq.ForeignKey
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val DIRECTION_PKEY: UniqueKey<DirectionRecord> = Internal.createUniqueKey(Direction.DIRECTION, DSL.name("direction_pkey"), arrayOf(Direction.DIRECTION.DIRECTION_), true)
val TYPE_OF_LINE_PKEY: UniqueKey<TypeOfLineRecord> = Internal.createUniqueKey(TypeOfLine.TYPE_OF_LINE, DSL.name("type_of_line_pkey"), arrayOf(TypeOfLine.TYPE_OF_LINE.TYPE_OF_LINE_), true)

// -------------------------------------------------------------------------
// FOREIGN KEY definitions
// -------------------------------------------------------------------------

val DIRECTION__DIRECTION_THE_OPPOSITE_OF_DIRECTION_FKEY: ForeignKey<DirectionRecord, DirectionRecord> = Internal.createForeignKey(Direction.DIRECTION, DSL.name("direction_the_opposite_of_direction_fkey"), arrayOf(Direction.DIRECTION.THE_OPPOSITE_OF_DIRECTION), fi.hsl.jore.jore4.jooq.route.keys.DIRECTION_PKEY, arrayOf(Direction.DIRECTION.DIRECTION_), true)
