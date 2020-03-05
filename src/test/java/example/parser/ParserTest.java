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

    @Test
    public void integerParses() throws ParseException {
        assertParses(new IntegerExp(123), new IntegerToken(123));
    }

    @Test
    public void variableParses() throws ParseException {
        assertParses(new VariableExp("foo"), new VariableToken("foo"));
    }

    @Test
    public void parensParse() throws ParseException {
        assertParses(new VariableExp("foo"),
                     new LeftParenToken(),
                     new VariableToken("foo"),
                     new RightParenToken());
    }

    @Test
    public void ifParses() throws ParseException {
        assertParses(new IfExp(new IntegerExp(1),
                               new IntegerExp(2),
                               new IntegerExp(3)),
                     new IfToken(),
                     new LeftParenToken(),
                     new IntegerToken(1),
                     new RightParenToken(),
                     new IntegerToken(2),
                     new ElseToken(),
                     new IntegerToken(3));
    }

    @Test
    public void plusParses() throws ParseException {
        assertParses(new PlusExp(new IntegerExp(1), new IntegerExp(2)),
                     new IntegerToken(1),
                     new PlusToken(),
                     new IntegerToken(2));
    }

    @Test
    public void plusIsLeftAssociative() throws ParseException {
        assertParses(new PlusExp(new PlusExp(new IntegerExp(1),
                                             new IntegerExp(2)),
                                 new IntegerExp(3)),
                     new IntegerToken(1),
                     new PlusToken(),
                     new IntegerToken(2),
                     new PlusToken(),
                     new IntegerToken(3));
    }

    @Test(expected = ParseException.class)
    public void missingIntegerGivesParseError() throws ParseException {
        assertParses(null,
                     new IntegerToken(1),
                     new PlusToken());
    }
} // ParserTest
    
        
