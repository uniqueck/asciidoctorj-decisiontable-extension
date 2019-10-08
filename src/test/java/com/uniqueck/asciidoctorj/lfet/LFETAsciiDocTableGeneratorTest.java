package com.uniqueck.asciidoctorj.lfet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LFETAsciiDocTableGeneratorTest {

    @ParameterizedTest(name = "LFET: {0} - ConditionLabel: {1} - TrueLabel {2}")
    @CsvSource({"smallestDecisionTable, C, Y", "smallestDecisionTable_German, B, J"})
    void generateSimplesDecisionTable(String lfetName, String conditionSymbol, String labelTrue) {
        List<String> generate = new LFETAsciiDocTableGenerator(new File("src/test/resources/" + lfetName + ".lfet")).generate();
        assertNotNull(generate);
        assertFalse(generate.isEmpty());

        assertEquals("." + lfetName, generate.get(0));
        assertEquals("[width=\"100%\",options=header,cols=\"1,3,2,2\",frame=none,grid=all]", generate.get(1));
        assertEquals(LFETAsciiDocTableGenerator.TABLE_START_END_TAG, generate.get(2));
        // header
        assertEquals("2+|", generate.get(3));
        assertEquals("^|R01", generate.get(4));
        assertEquals("^|R02", generate.get(5));

        // conditions
        assertEquals("h|"+conditionSymbol+"01", generate.get(6));
        assertEquals("h|Condition1", generate.get(7));
        assertEquals("^|" + labelTrue, generate.get(8));
        assertEquals("^|N", generate.get(9));

        assertEquals("4+|", generate.get(10));

        // actions
        assertEquals("h|A01", generate.get(11));
        assertEquals("h|Action1", generate.get(12));
        assertEquals("^|X", generate.get(13));
        assertEquals("^|", generate.get(14));

        assertEquals("h|A02", generate.get(15));
        assertEquals("h|Action2", generate.get(16));
        assertEquals("^|", generate.get(17));
        assertEquals("^|X", generate.get(18));

        assertEquals(LFETAsciiDocTableGenerator.TABLE_START_END_TAG, generate.get(generate.size()-1));

        System.out.println(generate.stream().collect(Collectors.joining(System.lineSeparator())));

    }

    @Test
    void generateSimplesDecisionTableWithActionOcc() {
        List<String> generate = new LFETAsciiDocTableGenerator(new File("src/test/resources/smallestDecisionTableWithActionOcc.lfet")).generate();
        assertNotNull(generate);
        assertFalse(generate.isEmpty());

        assertEquals(".smallestDecisionTableWithActionOcc", generate.get(0));
        assertEquals("[width=\"100%\",options=header,cols=\"1,3,2,2\",frame=none,grid=all]", generate.get(1));
        assertEquals(LFETAsciiDocTableGenerator.TABLE_START_END_TAG, generate.get(2));
        // header
        assertEquals("2+|", generate.get(3));
        assertEquals("^|R01", generate.get(4));
        assertEquals("^|R02", generate.get(5));

        // conditions
        assertEquals("h|C01", generate.get(6));
        assertEquals("h|Condition1", generate.get(7));
        assertEquals("^|Y", generate.get(8));
        assertEquals("^|N", generate.get(9));

        assertEquals("4+|", generate.get(10));

        // actions
        assertEquals("h|A01", generate.get(11));
        assertEquals("h|Action1", generate.get(12));
        assertEquals("^|A", generate.get(13));
        assertEquals("^|", generate.get(14));

        assertEquals("h|A02", generate.get(15));
        assertEquals("h|Action2", generate.get(16));
        assertEquals("^|C", generate.get(17));
        assertEquals("^|B", generate.get(18));

        assertEquals(LFETAsciiDocTableGenerator.TABLE_START_END_TAG, generate.get(generate.size()-1));

    }


    @Test
    void generateSimplesDecisionTableWithConditionOcc() {
        List<String> generate = new LFETAsciiDocTableGenerator(new File("src/test/resources/smallestDecisionTableWithConditionOcc.lfet")).generate();
        assertNotNull(generate);
        assertFalse(generate.isEmpty());

        assertEquals(".smallestDecisionTableWithConditionOcc", generate.get(0));
        assertEquals("[width=\"100%\",options=header,cols=\"1,3,2,2,2\",frame=none,grid=all]", generate.get(1));
        assertEquals(LFETAsciiDocTableGenerator.TABLE_START_END_TAG, generate.get(2));
        // header
        assertEquals("2+|", generate.get(3));
        assertEquals("^|R01", generate.get(4));
        assertEquals("^|R02", generate.get(5));
        assertEquals("^|R03", generate.get(6));

        // conditions
        assertEquals("h|C01", generate.get(7));
        assertEquals("h|Condition1", generate.get(8));
        assertEquals("^|A", generate.get(9));
        assertEquals("^|B", generate.get(10));
        assertEquals("^|C", generate.get(11));



        assertEquals("5+|", generate.get(12));

        // actions
        assertEquals("h|A01", generate.get(13));
        assertEquals("h|Action1", generate.get(14));
        assertEquals("^|X", generate.get(15));
        assertEquals("^|", generate.get(16));
        assertEquals("^|X", generate.get(17));

        assertEquals("h|A02", generate.get(18));
        assertEquals("h|Action2", generate.get(19));
        assertEquals("^|", generate.get(20));
        assertEquals("^|X", generate.get(21));
        assertEquals("^|X", generate.get(22));

        assertEquals(LFETAsciiDocTableGenerator.TABLE_START_END_TAG, generate.get(generate.size()-1));



    }
}