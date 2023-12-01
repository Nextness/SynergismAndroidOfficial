/**
 *  break_infinity.kt
 *  This is a re-implementation of index.ts in break_eternity from Patashu, available at
 *  https://github.com/Patashu/break_eternity.js
 **/

package com.game.synergism.core

import java.math.BigDecimal
import kotlin.math.log
import kotlin.math.E
import kotlin.math.sign
import kotlin.math.abs


const val MAX_SIGNIFICANT_DIGITS = 17
const val EXP_LIMIT = 9e15
const val NUMBER_EXP_MAX = 308
const val NUMBER_EXP_MIN = -324
const val MAX_ES_IN_A_ROW = 5
const val IGNORE_COMMAS = true
const val COMMAS_ARE_DECIMAL_POINTS = false

val LAYER_DOWN = log(EXP_LIMIT, 10.0)
val FIRST_NEG_LAYER = 1 / EXP_LIMIT
val DEFAULT_FROM_STRING_CACHE_SIZE = (1 shl 10) - 1

val powerOf10: () -> (Int) -> Double = {
    val powerOf10 = ArrayList<Double>()
    for (i in NUMBER_EXP_MIN + 1..NUMBER_EXP_MAX) {
        powerOf10.add("1e$i".toDouble())
    }
    val indexOf0InPowersOf10 = 323
    { power: Int -> powerOf10[power + indexOf0InPowersOf10] }
}

val criticalHeaders = arrayListOf(2, E, 3, 4, 5, 6, 7, 8, 9, 10)
val criticalTetrationValues = arrayOf(
    // tetration/slog to real height stuff
    // background info and tables of values for critical
    // functions taken here: https://github.com/Patashu/break_eternity.js/issues/22
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.0891180521811202527"),
        BigDecimal("1.1789767925673958433"),
        BigDecimal("1.2701455431742086633"),
        BigDecimal("1.3632090180450091941"),
        BigDecimal("1.4587818160364217007"),
        BigDecimal("1.5575237916251418333"),
        BigDecimal("1.6601571006859253673"),
        BigDecimal("1.7674858188369780435"),
        BigDecimal("1.8804192098842727359"),
        BigDecimal("2"),
    ),
    // Base E (using http://myweb.astate.edu/wpaulsen/tetcalc/tetcalc.html )
    arrayOf(
        BigDecimal("1"),                     // 0.0
        BigDecimal("1.1121114330934078681"), // 0.1
        BigDecimal("1.2310389249316089299"), // 0.2
        BigDecimal("1.3583836963111376089"), // 0.3
        BigDecimal("1.4960519303993531879"), // 0.4
        BigDecimal("1.6463542337511945810"), // 0.5
        BigDecimal("1.8121385357018724464"), // 0.6
        BigDecimal("1.9969713246183068478"), // 0.7
        BigDecimal("2.2053895545527544330"), // 0.8
        BigDecimal("2.4432574483385252544"), // 0.9
        BigDecimal(E),                            // 1.0
    ),
    // Base 3
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.1187738849693603"),
        BigDecimal("1.2464963939368214"),
        BigDecimal("1.38527004705667"),
        BigDecimal("1.5376664685821402"),
        BigDecimal("1.7068895236551784"),
        BigDecimal("1.897001227148399"),
        BigDecimal("2.1132403089001035"),
        BigDecimal("2.362480153784171"),
        BigDecimal("2.6539010333870774"),
        BigDecimal("3"),
    ),
    // Base 4
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.1367350847096405"),
        BigDecimal("1.2889510672956703"),
        BigDecimal("1.4606478703324786"),
        BigDecimal("1.6570295196661111"),
        BigDecimal("1.8850062585672889"),
        BigDecimal("2.1539465047453485"),
        BigDecimal("2.476829779693097"),
        BigDecimal("2.872061932789197"),
        BigDecimal("3.3664204535587183"),
        BigDecimal("4"),
    ),
    // Base 5
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.1494592900767588"),
        BigDecimal("1.319708228183931"),
        BigDecimal("1.5166291280087583"),
        BigDecimal("1.748171114438024"),
        BigDecimal("2.0253263297298045"),
        BigDecimal("2.3636668498288547"),
        BigDecimal("2.7858359149579424"),
        BigDecimal("3.3257226212448145"),
        BigDecimal("4.035730287722532"),
        BigDecimal("5"),
    ),
    // Base 6
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.159225940787673"),
        BigDecimal("1.343712473580932"),
        BigDecimal("1.5611293155111927"),
        BigDecimal("1.8221199554561318"),
        BigDecimal("2.14183924486326"),
        BigDecimal("2.542468319282638"),
        BigDecimal("3.0574682501653316"),
        BigDecimal("3.7390572020926873"),
        BigDecimal("4.6719550537360774"),
        BigDecimal("6"),
    ),
    // Base 7
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.1670905356972596"),
        BigDecimal("1.3632807444991446"),
        BigDecimal("1.5979222279405536"),
        BigDecimal("1.8842640123816674"),
        BigDecimal("2.2416069644878687"),
        BigDecimal("2.69893426559423"),
        BigDecimal("3.3012632110403577"),
        BigDecimal("4.121250340630164"),
        BigDecimal("5.281493033448316"),
        BigDecimal("7"),
    ),
    // Base 8
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.1736630594087796"),
        BigDecimal("1.379783782386201"),
        BigDecimal("1.6292821855668218"),
        BigDecimal("1.9378971836180754"),
        BigDecimal("2.3289975651071977"),
        BigDecimal("2.8384347394720835"),
        BigDecimal("3.5232708454565906"),
        BigDecimal("4.478242031114584"),
        BigDecimal("5.868592169644505"),
        BigDecimal("8"),
    ),
    // Base 9
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.1793017514670474"),
        BigDecimal("1.394054150657457"),
        BigDecimal("1.65664127441059"),
        BigDecimal("1.985170999970283"),
        BigDecimal("2.4069682290577457"),
        BigDecimal("2.9647310119960752"),
        BigDecimal("3.7278665320924946"),
        BigDecimal("4.814462547283592"),
        BigDecimal("6.436522247411611"),
        BigDecimal("9"),
    ),
    // Base 10
    arrayOf(
        BigDecimal("1"),
        BigDecimal("1.1840100246247336579"),
        BigDecimal("1.4061375836156954169"),
        BigDecimal("1.6802272208863963918"),
        BigDecimal("2.026757028388618927"),
        BigDecimal("2.4770056063449647580"),
        BigDecimal("3.0805252717554819987"),
        BigDecimal("3.9191964192627283911"),
        BigDecimal("5.1351528408331864230"),
        BigDecimal("6.9899611795347148455"),
        BigDecimal("10"),
    ),
)

val criticalSlogValues = arrayOf(
    // Base 2
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.9194161097107025"),
        BigDecimal("-0.8335625019330468"),
        BigDecimal("-0.7425599821143978"),
        BigDecimal("-0.6466611521029437"),
        BigDecimal("-0.5462617907227869"),
        BigDecimal("-0.4419033816638769"),
        BigDecimal("-0.3342645487554494"),
        BigDecimal("-0.224140440909962"),
        BigDecimal("-0.11241087890006762"),
        BigDecimal("0"),
    ),
    // Base E
    arrayOf(
        BigDecimal("-1"),                // 0.0
        BigDecimal("-0.90603157029014"), // 0.1
        BigDecimal("-0.80786507256596"), // 0.2
        BigDecimal("-0.70646669396340"), // 0.3
        BigDecimal("-0.60294836853664"), // 0.4
        BigDecimal("-0.49849837513117"), // 0.5
        BigDecimal("-0.39430303318768"), // 0.6
        BigDecimal("-0.29147201034755"), // 0.7
        BigDecimal("-0.19097820800866"), // 0.8
        BigDecimal("-0.09361896280296"), // 0.9
        BigDecimal("0"),                 // 1.0
    ),
    // Base 3
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.9021579584316141"),
        BigDecimal("-0.8005762598234203"),
        BigDecimal("-0.6964780623319391"),
        BigDecimal("-0.5911906810998454"),
        BigDecimal("-0.486050182576545"),
        BigDecimal("-0.3823089430815083"),
        BigDecimal("-0.28106046722897615"),
        BigDecimal("-0.1831906535795894"),
        BigDecimal("-0.08935809204418144"),
        BigDecimal("0"),
    ),
    // Base 4
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.8917227442365535"),
        BigDecimal("-0.781258746326964"),
        BigDecimal("-0.6705130326902455"),
        BigDecimal("-0.5612813129406509"),
        BigDecimal("-0.4551067709033134"),
        BigDecimal("-0.35319256652135966"),
        BigDecimal("-0.2563741554088552"),
        BigDecimal("-0.1651412821106526"),
        BigDecimal("-0.0796919581982668"),
        BigDecimal("0"),
    ),
    // Base 5
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.8843387974366064"),
        BigDecimal("-0.7678744063886243"),
        BigDecimal("-0.6529563724510552"),
        BigDecimal("-0.5415870994657841"),
        BigDecimal("-0.4352842206588936"),
        BigDecimal("-0.33504449124791424"),
        BigDecimal("-0.24138853420685147"),
        BigDecimal("-0.15445285440944467"),
        BigDecimal("-0.07409659641336663"),
        BigDecimal("0"),
    ),
    // Base 6
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.8786709358426346"),
        BigDecimal("-0.7577735191184886"),
        BigDecimal("-0.6399546189952064"),
        BigDecimal("-0.527284921869926"),
        BigDecimal("-0.4211627631006314"),
        BigDecimal("-0.3223479611761232"),
        BigDecimal("-0.23107655627789858"),
        BigDecimal("-0.1472057700818259"),
        BigDecimal("-0.07035171210706326"),
        BigDecimal("0"),
    ),
    // Base 7
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.8740862815291583"),
        BigDecimal("-0.7497032990976209"),
        BigDecimal("-0.6297119746181752"),
        BigDecimal("-0.5161838335958787"),
        BigDecimal("-0.41036238255751956"),
        BigDecimal("-0.31277212146489963"),
        BigDecimal("-0.2233976621705518"),
        BigDecimal("-0.1418697367979619"),
        BigDecimal("-0.06762117662323441"),
        BigDecimal("0"),
    ),
    // Base 8
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.8702632331800649"),
        BigDecimal("-0.7430366914122081"),
        BigDecimal("-0.6213373075161548"),
        BigDecimal("-0.5072025698095242"),
        BigDecimal("-0.40171437727184167"),
        BigDecimal("-0.30517930701410456"),
        BigDecimal("-0.21736343968190863"),
        BigDecimal("-0.137710238299109"),
        BigDecimal("-0.06550774483471955"),
        BigDecimal("0"),
    ),
    // Base 9
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.8670016295947213"),
        BigDecimal("-0.7373984232432306"),
        BigDecimal("-0.6143173985094293"),
        BigDecimal("-0.49973884395492807"),
        BigDecimal("-0.394584953527678"),
        BigDecimal("-0.2989649949848695"),
        BigDecimal("-0.21245647317021688"),
        BigDecimal("-0.13434688362382652"),
        BigDecimal("-0.0638072667348083"),
        BigDecimal("0"),
    ),
    // Base 10
    arrayOf(
        BigDecimal("-1"),
        BigDecimal("-0.8641642839543857"),
        BigDecimal("-0.732534623168535"),
        BigDecimal("-0.6083127477059322"),
        BigDecimal("-0.4934049257184696"),
        BigDecimal("-0.3885773075899922"),
        BigDecimal("-0.29376029055315767"),
        BigDecimal("-0.2083678561173622"),
        BigDecimal("-0.13155653399373268"),
        BigDecimal("-0.062401588652553186"),
        BigDecimal("0"),
    ),
)

sealed interface DecimalSource
sealed interface Decimal

//var D: (DecimalSource) -> Decimal = {
//
//}
//
//var FC: (Double, Double, Double) -> Decimal = {
//
//}
//
//var FC_NN: (Double, Double, Double) -> Decimal = {
//
//}
//
//var ME: (Double, Double) -> Decimal = {
//
//}
//
//var ME_NN: (Double, Double) -> Decimal = {
//
//}

val fMagLog10: (Double) -> Double = { value ->
    sign(value) * log(abs(value), 10.0)
}

val _twopi = BigDecimal("6.2831853071795864769252842")    // 2*pi
val _EXPN1 = BigDecimal("0.36787944117144232159553")      // exp(-1)
val OMEGA = BigDecimal("0.56714329040978387299997")       // W(1, 0)

