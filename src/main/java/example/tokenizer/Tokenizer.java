package example.tokenizer;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Tokenizer {
    // ---BEGIN CONSTANTS---
    public static final Map<Character, Token> SINGLE_CHAR_TOKENS =
        new HashMap<Character, Token>() {{
            put(Character.valueOf('+'), new PlusToken());
            put(Character.valueOf('('), new LeftParenToken());
            put(Character.valueOf(')'), new RightParenToken());
        }};
    public static final Map<String, Token> RESERVED_WORDS =
        new HashMap<String, Token>() {{
            put("if", new IfToken());
            put("else", new ElseToken());
        }};
    // ---END CONSTANTS---

    // ---BEGIN INSTANCE VARIABLES---
    private final char[] chars;
    private int pos;
    // ---END INSTANCE VARIABLES---
    
    public Tokenizer(final char[] chars) {
        this.chars = chars;
        pos = 0;
    }

    public Tokenizer(final String input) {
        this(input.toCharArray());
    }
    
    // returns null if it could not tokenize it
    private IntegerToken tokenizeInteger() {
        String digits = "";
        
        if (pos < chars.length &&
            chars[pos] == '-') {
            digits += chars[pos];
            pos++;
        }
        
        while (pos < chars.length &&
               Character.isDigit(chars[pos])) {
            digits += chars[pos];
            pos++;
        }

        final int length = digits.length();
        if (length > 0) {
            if (length == 1 && digits.charAt(0) == '-') {
                pos--;
                // edge case: we read in only a -
                return null;
            } else {
                return new IntegerToken(Integer.parseInt(digits));
            }
        } else {
            return null;
        }
    } // tokenizeInteger

    // returns null if it could not tokenize a variable or reserved word
    private Token tokenizeVariableOrReservedWord() {
        String name = "";

        if (pos < chars.length &&
            Character.isLetter(chars[pos])) {
            name += chars[pos];
            pos++;
        }
        
        while (pos < chars.length &&
               Character.isLetterOrDigit(chars[pos])) {
            name += chars[pos];
            pos++;
        }

        if (name.equals("")) {
            return null;
        } else if (RESERVED_WORDS.containsKey(name)) {
            return RESERVED_WORDS.get(name);
        } else {
            return new VariableToken(name);
        }
    } // tokenizeVariableOrReservedWord

    // returns null if it could not tokenize a single-character token
    private Token tokenizeSingleCharacter() {
        if (pos < chars.length) {
            final Character asChar = Character.valueOf(chars[pos]);
            if (SINGLE_CHAR_TOKENS.containsKey(asChar)) {
                pos++;
                return SINGLE_CHAR_TOKENS.get(asChar);
            } else {
                return null;
            }
        } else {
            return null;
        }
    } // tokenizeSingleCharacter
            
    private void skipWhitespace() {
        while (pos < chars.length &&
               Character.isWhitespace(chars[pos])) {
            pos++;
        }
    } // skipWhitespace

    private Token tokenizeOne() throws TokenizerException {
        Token read = null;

        if ((read = tokenizeInteger()) != null ||
            (read = tokenizeVariableOrReservedWord()) != null ||
            (read = tokenizeSingleCharacter()) != null) {
            return read;
        } else {
            if (pos < chars.length) {
                throw new TokenizerException("Failed to tokenize: " + chars[pos]);
            } else {
                throw new TokenizerException("Out of characters");
            }
        }
    } // tokenizeOne

    public List<Token> tokenize() throws TokenizerException {
        final List<Token> result = new ArrayList<Token>();

        while (pos < chars.length) {
            skipWhitespace();
            if (pos < chars.length) {
                result.add(tokenizeOne());
            }
        }
        
        return result;
    } // tokenize
} // Tokenizer
