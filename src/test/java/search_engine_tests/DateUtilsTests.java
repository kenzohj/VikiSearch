package search_engine_tests;

import org.junit.jupiter.api.Test;
import search_engine.utils.DateUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilsTests {

    @Test
    public void testDateFormater() {
        assertEquals("09:59 20/03 2023", DateUtils.formatDate(1679302799007L));
    }

    @Test
    public void testDateFileFormater() {
        assertEquals("09-59_20-03_2023", DateUtils.formatFileNameDate(1679302799007L));
    }

}
