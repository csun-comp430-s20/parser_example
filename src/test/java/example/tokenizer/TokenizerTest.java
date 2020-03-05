package example.tokenizer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

public class TokenizerTest {
    public static void assertTokenizes(final String input,
                                       final Token... expectedRaw)
        throws TokenizerException {
        final List<Token> expected = new ArrayList<Token>(expectedRaw.length);
        for (final Token current : expectedRaw) {
            expected.add(current);
        }
        final List<Token> received = (new Tokenizer(input)).tokenize();
        assertEquals(expected, received);
    } // assertTokenizes
    
    @Test
    public void plusTokenizes() throws TokenizerException {
        assertTokenizes("+", new PlusToken());
    }

    @Test
    public void leftParenTokenizes() throws TokenizerException {
        assertTokenizes("(", new LeftParenToken());
    }

    @Test
    public void rightParenTokenizes() throws TokenizerException {
        assertTokenizes(")", new RightParenToken());
    }

    @Test
    public void plusTokenizesWithPreceedingWhitespace() throws TokenizerException {
        assertTokenizes(" +", new PlusToken());
    }

    @Test
    public void plusTokenizesWithFollowingWhitespace() throws TokenizerException {
        assertTokenizes("+ ", new PlusToken());
    }

    @Test
    public void ifTokenizes() throws TokenizerException {
        assertTokenizes("if", new IfToken());
    }

    @Test
    public void elseTokenizes() throws TokenizerException {
        assertTokenizes("else", new ElseToken());
    }

    @Test
    public void singleCharacterVariableTokenizes() throws TokenizerException {
        assertTokenizes("f", new VariableToken("f"));
    }
    
    @Test
    public void multiCharacterVariableTokenizes() throws TokenizerException {
        assertTokenizes("foo", new VariableToken("foo"));
    }

    @Test
    public void variableWithReservedWordPrefixTokenizes() throws TokenizerException {
        assertTokenizes("ifBlah", new VariableToken("ifBlah"));
    }

    @Test
    public void variableWithReservedWordSuffixTokenizes() throws TokenizerException {
        assertTokenizes("blahIf", new VariableToken("blahIf"));
    }

    @Test
    public void variableWithReservedWordInsideTokenizes() throws TokenizerException {
        assertTokenizes("blahifblah", new VariableToken("blahifblah"));
    }

    @Test
    public void singleDigitIntegerTokenizes() throws TokenizerException {
        assertTokenizes("1", new IntegerToken(1));
    }

    @Test
    public void multiDigitIntegerTokenizes() throws TokenizerException {
        assertTokenizes("123", new IntegerToken(123));
    }

    @Test
    public void negativeIntegerTokenizes() throws TokenizerException {
        assertTokenizes("-123", new IntegerToken(-123));
    }

    @Test(expected = TokenizerException.class)
    public void negativeSignAloneDoesNotTokenize() throws TokenizerException {
        assertTokenizes("-");
    }

    @Test(expected = TokenizerException.class)
    public void negativeVariableDoesNotTokenize() throws TokenizerException {
        assertTokenizes("-foo");
    }
    
    @Test
    public void variableWithIntegerPrefixIsTwoTokens() throws TokenizerException {
        assertTokenizes("123foo", new IntegerToken(123), new VariableToken("foo"));
    }

    @Test
    public void variableWithIntegerSuffixIsOneToken() throws TokenizerException {
        assertTokenizes("foo123", new VariableToken("foo123"));
    }

    @Test
    public void variableWithIntegerInsideIsOneToken() throws TokenizerException {
        assertTokenizes("foo123bar", new VariableToken("foo123bar"));
    }

    @Test
    public void variableWithSpaceAndIntegerIsTwoTokens() throws TokenizerException {
        assertTokenizes("foo 123", new VariableToken("foo"), new IntegerToken(123));
    }

    @Test
    public void emptyStringTokenizes() throws TokenizerException {
        assertTokenizes("");
    }

    @Test(expected = TokenizerException.class)
    public void singleUnrecognizedCharacterDoesNotTokenize() throws TokenizerException {
        assertTokenizes("$");
    }
}
