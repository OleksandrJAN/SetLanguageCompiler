import parse.Lexer;
import parse.Parser;
import domain.token.Token;
import statement.Statement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = new String(Files.readAllBytes(Paths.get("program.txt")), StandardCharsets.UTF_8);
        Lexer lexer = new Lexer(input);

        List<Token> tokens = lexer.tokenize();

//        for (Token token : tokens) {
//            System.out.println(token);
//        }

        Parser parser = new Parser(tokens);
        Statement program = parser.parse();
        program.execute();

    }
}
