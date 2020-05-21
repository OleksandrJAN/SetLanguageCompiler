package domain.token;

import domain.Set;

public class Token {
    private TokenType type;
    private String text;
    private Set set;


    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    public Token(Set set) {
        this.type = TokenType.SET;
        this.set = set;
        this.text = set.getView();
    }


    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "Token{" + type + " '" + text + "'}";
    }

}
