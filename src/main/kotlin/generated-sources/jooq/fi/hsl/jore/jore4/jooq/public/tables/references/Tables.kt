/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.`public`.tables.references


import org.jooq.Configuration
import org.jooq.Field
import org.jooq.Record
import org.jooq.Result



/**
 * The table <code>public.pgp_armor_headers</code>.
 */
val PGP_ARMOR_HEADERS: fi.hsl.jore.jore4.jooq.`public`.tables.PgpArmorHeaders = fi.hsl.jore.jore4.jooq.`public`.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS

/**
 * Call <code>public.pgp_armor_headers</code>.
 */
fun PGP_ARMOR_HEADERS(
      configuration: Configuration
    , __1: String?
): Result<Record> = configuration.dsl().selectFrom(fi.hsl.jore.jore4.jooq.`public`.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS.call(
      __1
)).fetch()

/**
 * Get <code>public.pgp_armor_headers</code> as a table.
 */
fun PGP_ARMOR_HEADERS(
      __1: String?
): fi.hsl.jore.jore4.jooq.`public`.tables.PgpArmorHeaders = fi.hsl.jore.jore4.jooq.`public`.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS.call(
    __1
)

/**
 * Get <code>public.pgp_armor_headers</code> as a table.
 */
fun PGP_ARMOR_HEADERS(
      __1: Field<String?>
): fi.hsl.jore.jore4.jooq.`public`.tables.PgpArmorHeaders = fi.hsl.jore.jore4.jooq.`public`.tables.PgpArmorHeaders.PGP_ARMOR_HEADERS.call(
    __1
)
