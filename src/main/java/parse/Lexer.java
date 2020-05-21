package parse;

import domain.Element;
import domain.Set;
import domain.token.Token;
import domain.token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Lexer {
    private static final String OPERATOR_CHARS = "+-*/(){},=<>!&|";
    private static final Map<String, TokenType> operationMap = new HashMap<String, TokenType>() {{
        put("+", TokenType.PLUS);
        put("-", TokenType.MINUS);
        put("*", TokenType.STAR);

        put("(", TokenType.LPAREN);
        put(")", TokenType.RPAREN);
        put("{", TokenType.LBRACE);
        put("}", TokenType.RBRACE);
        put(",", TokenType.COMMA);

        put("=", TokenType.EQ);
        put("==", TokenType.EQEQ);
        put("<", TokenType.LT);
        put("<=", TokenType.LTEQ);
        put(">", TokenType.GT);
        put(">=", TokenType.GTEQ);
        put("!", TokenType.EXCL);
        put("!=", TokenType.EXCLEQ);

        put("&", TokenType.AMP);
        put("&&", TokenType.AMPAMP);
        put("|", TokenType.BAR);
        put("||", TokenType.BARBAR);
    }};

    private final List<Token> tokens;
    private final String input;
    private final int length;
    private int pos;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
        this.tokens = new ArrayList<>();
    }


    public List<Token> tokenize() {
        while (pos < length) {
            char current = peek();

            if (current == '$' && input.charAt(pos + 1) == '{') {
                pos++;                              //skip $
                String setStr = getSetStr();
                pos += setStr.length();             //skip Set definition

                Set set = tokenizeSet(setStr);
                addToken(set);
            } else if (Character.isLetter(current)) {
                tokenizeWord();
            } else if (current == '"') {
                tokenizeText();
            } else if (OPERATOR_CHARS.indexOf(current) != -1) {
                tokenizeOperator();
            } else {
                // whitespaces
                pos++;
            }
        }
        return tokens;
    }

    private String getSetStr() {
        int startOfSet = pos;
        int figureNumber = 0;
        StringBuilder builder = new StringBuilder();

        do {
            char current = input.charAt(startOfSet);
            if (current == '{') {
                figureNumber++;
            } else if (current == '}') {
                figureNumber--;
            }

            builder.append(current);
            startOfSet++;
        } while (figureNumber != 0);

        return builder.toString();
    }

    private Set tokenizeSet(String setString) {
        Set set = new Set();
        int iterator = 0;
        int figureNumber = 0;

        do {
            char current = setString.charAt(iterator);

            if (current == '{') {
                figureNumber++;
            } else if (current == '}') {
                figureNumber--;
                iterator++;
                continue;
            }

            if (figureNumber == 1 && current != '{' && current != ',') {
                StringBuilder builder = new StringBuilder();

                while (current != ',' && current != '}') {
                    builder.append(current);
                    iterator++;
                    current = setString.charAt(iterator);
                }

                String elementStr = builder.toString();
                if (elementStr.contains("{")) {
                    throw new RuntimeException("Invalid upper set element -> '" + elementStr + "'!");
                }

                set.addElement(new Element(elementStr));
                continue;   //iterator already increased
            }

            if (figureNumber > 1) {
                StringBuilder builder = new StringBuilder();
                builder.append(current);

                do {
                    iterator++;
                    current = setString.charAt(iterator);
                    builder.append(current);

                    if (current == '{') {
                        figureNumber++;
                    } else if (current == '}') {
                        figureNumber--;
                    }
                } while (figureNumber != 1);

                String subSetString = builder.toString();
                Set subSet = tokenizeSet(subSetString);
                set.addElement(subSet);

                // skip last } of subset and continue cycle
                iterator++;
                continue;
            }

            iterator++;
        } while (figureNumber != 0);

        return set;
    }

    private void tokenizeWord() {
        StringBuilder builder = new StringBuilder();
        while (true) {
            char current = peek();
            if (!Character.isLetterOrDigit(current) && current != '_') {
                break;
            }
            builder.append(current);
            pos++;
        }

        String word = builder.toString();
        switch (word) {
            case "print":
                addToken(TokenType.PRINT);
                break;
            case "if":
                addToken(TokenType.IF);
                break;
            case "else":
                addToken(TokenType.ELSE);
                break;
            case "while":
                addToken(TokenType.WHILE);
                break;
            case "for":
                addToken(TokenType.FOR);
                break;
            default:
                addToken(TokenType.WORD, word);
        }
    }

    private void tokenizeText() {
        StringBuilder builder = new StringBuilder();

        pos++;  //skip "
        while (true) {
            char current = peek();

            if (current == '\\') {
                pos++;
                current = peek();
                switch (current) {
                    case '"':
                        builder.append('"');
                        break;
                    case 'n':
                        builder.append('\n');
                        break;
                    case 't':
                        builder.append('\t');
                        break;
                    case '\\':
                        builder.append('\\');
                        break;
                    default:
                        throw new RuntimeException("Unknown text symbol");
                }

                pos++;  //skip ", \n, \t, \
                continue;
            }
            if (current == '"') {
                break;
            }
            builder.append(current);
            pos++;
        }
        pos++;  //skip closing "

        addToken(TokenType.TEXT, builder.toString());
    }

    private void tokenizeOperator() {
        char current = peek();
        if (current == '/') {
            char afterCurrent = input.charAt(pos + 1);
            if (afterCurrent == '/') {
                pos += 2;   //skip //
                tokenizeComment();
                return;
            } else if (afterCurrent == '*') {
                pos += 2;   //skip /*
                tokenizeMultilineComment();
                return;
            }
        }

        StringBuilder operatorBuilder = new StringBuilder();
        while (true) {
            String operator = operatorBuilder.toString();
            if (!operationMap.containsKey(operator + current) && !operator.isEmpty()) {
                addToken(operationMap.get(operator));
                return;
            }
            operatorBuilder.append(current);
            pos++;
            current = peek();
        }
    }


    private void tokenizeComment() {
        char current = peek();
        while ("\r\n\0".indexOf(current) == -1) {
            pos++;
            current = peek();
        }
    }

    private void tokenizeMultilineComment() {
        char current = peek();
        while (true) {
            if (current == '\0') {
                throw new RuntimeException("Missing */");
            }
            if (current == '*') {
                char afterCurrent = input.charAt(pos + 1);
                if (afterCurrent == '/') {
                    break;
                }
            }
            pos++;
            current = peek();
        }
        pos += 2;   //skip */
    }


    private char peek() {
        if (pos >= length) {
            return '\0';
        }

        return input.charAt(pos);
    }

    private void addToken(TokenType type) {
        tokens.add(new Token(type, ""));
    }

    private void addToken(TokenType type, String text) {
        tokens.add(new Token(type, text));
    }

    private void addToken(Set set) {
        tokens.add(new Token(set));
    }

}
