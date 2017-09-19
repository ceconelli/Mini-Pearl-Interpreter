package syntatic;

import java.io.IOException;
import lexical.Lexeme;
import lexical.TokenType;
import lexical.LexicalAnalysis;

import interpreter.expr.Expr;
import interpreter.expr.ConstExpr;
import interpreter.value.IntegerValue;
import interpreter.command.Command;
import interpreter.command.CommandsBlock;
import interpreter.command.ActionCommand;
import interpreter.command.PrintCommand;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) throws IOException {
        this.lex = lex;
        this.current = lex.nextToken();
    }

    public Command start() throws IOException {
        Command cmd = procStatements();
        matchToken(TokenType.END_OF_FILE);
        return cmd;
    }

    private void matchToken(TokenType type) throws IOException {
        if (type == current.type) {
            current = lex.nextToken();
        } else {
            showError();
        }
    }

    private void showError() {
        switch (current.type) {
            case INVALID_TOKEN:
                // Imprimir erro lexico (1)
                break;
            case UNEXPECTED_EOF:
                // Imprimir erro lexico (2)
                break;
            case END_OF_FILE:
                // imprimir erro sintático (2)
                break;
            default:
                // imprimir erro sintático (1)
                break;
        }

        System.exit(1);
    }

    // <statements> ::= <cmd> { <cmd> }
    private Command procStatements() throws IOException {
        CommandsBlock cmds = new CommandsBlock();
        cmds.addCommand(procCmd());

        while (current.type == TokenType.SVAR || 
               current.type == TokenType.LVAR || 
               current.type == TokenType.HVAR || 
               current.type == TokenType.PRINT ||
               current.type == TokenType.PRINTLN || 
               current.type == TokenType.PUSH || 
               current.type == TokenType.UNSHIFT || 
               current.type == TokenType.IF || 
               current.type == TokenType.WHILE || 
               current.type == TokenType.DO || 
               current.type == TokenType.FOREACH) {
            cmds.addCommand(procCmd());
        }

        return cmds;
    }
 
    // <cmd> ::= <assign> | <action> | <if> | <while> | <do-while> | <foreach>
    private Command procCmd() throws IOException {
        Command c = null;

        switch (current.type) {
            case SVAR:
            case LVAR:
            case HVAR:
//                procAssign();
                break;
            case PRINT:
            case PRINTLN:
            case PUSH:
            case UNSHIFT:
                c = procAction();
                break;
            case IF:
//                procIf();
                break;
            case WHILE:
//                procWhile();
                break;
            case DO:
//                procDo();
                break;
            case FOREACH:
//                procForeach();
                break;
            default:
                showError();
                break;
        }

        return c;
    }

    // <action> ::= (print <rhs> | println [<rhs>] | push <rhs> ',' <rhs> | unshift <rhs> [ ',' <rhs> ]) [ <post> ] ';'
    private Command procAction() throws IOException {
        int line;
        ActionCommand ac = null;

        if (current.type == TokenType.PRINT) {
            // FIXME: print <rhs>
        } else if (current.type == TokenType.PRINTLN) {
            line = lex.getLine();

            matchToken(TokenType.PRINTLN);

            Expr expr = null;
            if (current.type == TokenType.NUMBER ||
                current.type == TokenType.STRING ||
                current.type == TokenType.OPEN_BRA ||
                current.type == TokenType.OPEN_CUR ||
                current.type == TokenType.SVAR ||
                current.type == TokenType.LVAR ||
                current.type == TokenType.HVAR ||
                current.type == TokenType.INPUT ||
                current.type == TokenType.SIZE ||
                current.type == TokenType.SORT ||
                current.type == TokenType.REVERSE ||
                current.type == TokenType.KEYS ||
                current.type == TokenType.VALUES ||
                current.type == TokenType.EMPTY ||
                current.type == TokenType.POP ||
                current.type == TokenType.SHIFT ||
                current.type == TokenType.OPEN_PAR) {
                expr = procRhs();
            }

            ac = new PrintCommand(expr, true, line);
        } else if (current.type == TokenType.PUSH) {
            // FIXME: push <rhs> ',' <rhs>
        } else if (current.type == TokenType.UNSHIFT) {
            // FIXME: unshift <rhs> [ ',' <rhs> ])
        } else {
            showError();
        }

        // FIXME: [ <post> ]

        matchToken(TokenType.DOT_COMMA);

        return ac;
    }

    // <rhs> ::= <sexpr> [ '[' <rhs> ']' | '{' <rhs> '}' ]
    private Expr procRhs() throws IOException {
        Expr e = procSExpr();

        // FIXME: [ '[' <rhs> ']' | '{' <rhs> '}' ]

        return e;
    }

    // <sexpr> ::= <expr> { '.' <expr> }
    private Expr procSExpr() throws IOException {
        Expr e = procExpr();

        // FIXME: { '.' <expr> }

        return e;
    }

    // <expr> ::= <term> { ('+' | '-') <term> }
    private Expr procExpr() throws IOException {
        Expr e = procTerm();

        // FIXME: { ('+' | '-') <term> }

        return e;
    }

    // <term> ::= <factor> { ('*' | '/' | '%') <factor> }
    private Expr procTerm() throws IOException {
        Expr e = procFactor();

        // FIXME: { ('*' | '/' | '%') <factor> }

        return e;
    }

    // <factor> ::= <number> | <string> | <list> | <hash> | <var> | <func> | '(' <sexpr> ')'
    private Expr procFactor() throws IOException {
        Expr e = null;

        switch (current.type) {
            case NUMBER:
                e = procNumber();
                break;
            // FIXME: <string> | <list> | <hash> | <var> | <func> | '(' <sexpr> ')'
            default:
                showError();
                break;
        }

        return e;
    }

    // <number>
    private Expr procNumber() throws IOException {
        int line = lex.getLine();
        String n = current.token;

        matchToken(TokenType.NUMBER);

        IntegerValue iv = new IntegerValue(Integer.parseInt(n), line);
        Expr e = new ConstExpr(iv, line);
        return e;
    }
    
    private void procWhile() throws IOException{
		 matchToken(TokenType.WHILE);		// while
		 matchToken(TokenType.OPEN_PAR);	// '('
//		 procBoolExp();						// <boolexp>
		 matchToken(TokenType.CLOSE_PAR);	// ')'
		 matchToken(TokenType.OPEN_CUR);	// '{'
		 procStatements();					// <statements>
		 matchToken(TokenType.CLOSE_CUR);	// '}'
		 
	 }
    
    private void procBoolExp() throws IOException{
    }
}
