package example.parser;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import example.tokenizer.*;

public class ParserTest {
    public static void assertParses(final Exp expected,
                                    final Token... tokens)
        throws ParseException {
        assertEquals(expected, (new Parser(tokens)).parseToplevelExp());
    } // assertParses

    @Test(expected = ParseException.class)
    public void emptyDoesNotParse() throws ParseException {
        assertParses(null);
    }
} // ParserTest
    
        
