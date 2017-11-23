package io.github.commons.dates

import java.time.format.DateTimeFormatter
import java.time.{DayOfWeek, LocalDate}

import org.slf4j.LoggerFactory

/**
  * Vérification des jours fériés
  */
case class WorkingDay(date: LocalDate) {
  val logger = LoggerFactory.getLogger(classOf[WorkingDay])

  def isWorking: Boolean = {
    date.getDayOfWeek match {
      case DayOfWeek.SATURDAY => false
      case DayOfWeek.SUNDAY => false
      case _ => ! isClosed
    }
  }

  /**
    *
    * @return liste complète des jours fériés : jours fixes + jours lunaires
    */
  def isClosed: Boolean = {
    val year = date.getYear
    val closedDays = List(
      LocalDate.ofYearDay(year, 1),  // 1er janvier
      LocalDate.parse(s"01/05/$year", WorkingDay.dtFormatter),
      LocalDate.parse(s"08/05/$year", WorkingDay.dtFormatter),
      LocalDate.parse(s"14/07/$year", WorkingDay.dtFormatter),
      LocalDate.parse(s"15/08/$year", WorkingDay.dtFormatter),
      LocalDate.parse(s"01/11/$year", WorkingDay.dtFormatter),
      LocalDate.parse(s"11/11/$year", WorkingDay.dtFormatter),
      LocalDate.parse(s"25/12/$year", WorkingDay.dtFormatter)
    ) ::: lunarDays(year)

    closedDays.contains(date)
  }

  /**
    * Calcule les jours fériés lunaires : di. et lu. de Pâques, je. ascencion, lu. pentecôte
    *
    * Calcul selon l'algorithme donné par Jean Meeus  : https://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques_selon_la_m%C3%A9thode_de_Meeus
    * limité au calendrier grégorien : annés >= 1583
    *
    * implémentation javascript : http://pgj.pagesperso-orange.fr/paques.htm
    *
    * @return la liste des jours lunaires hors dimanches
    */
  def lunarDays(year: Int): List[LocalDate] = {
    val a = year % 19

    val b = Math.floor(year / 100)
    val c = year %100
    val d = Math.floor(b / 4)
    val e = b%4
    val f = Math.floor((b + 8) / 25)
    val g = Math.floor((b - f + 1) / 3)
    val h = (19 * a + b - d - g + 15)%30
    val i = Math.floor(c / 4)
    val k = c%4
    val l = (32 + 2 * e + 2 * i - h - k)%7
    val m = Math.floor((a + 11 * h + 22 *l) / 451)
    val n = { Math.floor(( h + l - 7 * m + 114) / 31) } toInt ; //[3 = March, 4 = April]
    val p = { (h + l - 7 * m + 114)%31 +1 } toInt

    val dayInMars = 22 + d + e
    val dimPaques = LocalDate.parse(s"${"%02d".format(p)}/${"%02d".format(n)}/$year", WorkingDay.dtFormatter)

    // Lundi de Pâques, Jeudi de l'ascencion, lundi de pentecôte
    List(dimPaques.plusDays(1), dimPaques.plusDays(39), dimPaques.plusDays(50) )
  }

}


object WorkingDay {
  val dtFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern ("dd/MM/yyyy")

}

