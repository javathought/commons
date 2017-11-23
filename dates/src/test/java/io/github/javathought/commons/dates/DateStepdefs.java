package io.github.javathought.commons.dates;

import cucumber.api.java8.En;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Steps pour tests sur les dates
 */

public class DateStepdefs implements En {

    private static final DateTimeFormatter dtFormatter = WorkingDay.dtFormatter();
    private LocalDate date;

    public DateStepdefs() {
        When("^la date est '(.*)'$", (String d) -> date = LocalDate.parse(d, dtFormatter));

        Then("^le jour est travaillé$", () -> assertTrue(new WorkingDay(date).isWorking()));

        Then("^le jour n'est pas travaillé", () -> assertFalse(new WorkingDay(date).isWorking()));
    }
}
