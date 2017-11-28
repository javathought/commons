package io.github.javathought.commons.dates;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import scala.Some;
import scala.collection.Map;
import scala.collection.Seq;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * Steps pour tests sur les dates
 */

public class DateStepdefs implements En {

    private static final DateTimeFormatter dtFormatter = WorkingDay.dtFormatter();
    private LocalDate date;
    private Seq<WorkingDay> dates;

    public DateStepdefs() {
        When("^la date est '(.*)'$", (String d) -> date = LocalDate.parse(d, dtFormatter));

        Then("^le jour est travaillé$", () -> assertTrue(new WorkingDay(date).isWorking()));

        Then("^le jour n'est pas travaillé", () -> assertFalse(new WorkingDay(date).isWorking()));

        Given("^l'intervalle entre '(.*)' et '(.*)'$", (String d1, String d2) -> {
            dates = WorkingDay.between(LocalDate.parse(d1, dtFormatter), LocalDate.parse(d2, dtFormatter));
        });

        Then("^le nombre de jours ouvrés de l'intervalle est (\\d+)$", (Integer nb) -> {
            assertEquals(nb, new Integer(dates.size()));
        });
        And("^le nombre de jours ouvrés par mois est$", (DataTable datas) -> {
            Map<LocalDate, Object> resMois = WorkingDay.countByMonth(dates);
            assertEquals("Nombre de mois couverts", datas.raw().size() - 1, resMois.size());
            for ( Count expectedMois : datas.asList(Count.class)) {
                assertEquals("Nombre de jours dans le mois", new Some(expectedMois.nb),
                        resMois.get(LocalDate.parse(String.format("01/%d/2017", expectedMois.mois), dtFormatter)));

            }
        });
    }

    private class Count {
        public int mois;
        public int nb;
    }
}
