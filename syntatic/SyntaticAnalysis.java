package syntatic;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

import lexical.Lexeme;
import lexical.TokenType;
import lexical.LexicalAnalysis;

import interpreter.expr.Expr;
import interpreter.expr.ConstExpr;
import interpreter.expr.Variable;
import interpreter.expr.ScalarVariable;
import interpreter.expr.ListVariable;
import interpreter.expr.HashVariable;
import interpreter.expr.FunctionType;
import interpreter.expr.FunctionExpr;
import interpreter.boolexpr.BoolExpr;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.ListValue;
import interpreter.value.HashValue;
import interpreter.value.Value;
import interpreter.value.PrimitiveValue;
import interpreter.command.Command;
import interpreter.command.CommandsBlock;
import interpreter.command.AssignCommand;
import interpreter.command.ActionCommand;
import interpreter.command.PrintCommand;
import interpreter.command.WhileCommand;
import interpreter.expr.HashIndexExpr;
import interpreter.expr.ListIndexExpr;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;
    private Map<String, Variable> global;

    public SyntaticAnalysis(LexicalAnalysis lex) throws IOException {
        this.lex = lex;
        this.current = lex.nextToken();
        this.global = new HashMap<String, Variable>();
    }

    public Command start() throws IOException {
        Command cmd = procStatements();
        matchToken(TokenType.END_OF_FILE);
        return cmd;
    }
    
    private void matchToken(TokenType type) throws IOException {
        // System.out.println("Match token: " + current.type + " == " + type + "?");
        if (type == current.type) {
            current = lex.nextToken();
        } else {
            showError();
        }
    }

    private void showError() {
        System.out.printf("%02d: ", lex.getLine());

        switch (current.type) {
            case INVALID_TOKEN:
                System.out.printf("Lexema inválido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                System.out.printf("Lexema não esperado [%s]\n", current.token);
                break;
        }

        System.exit(1);
    }
    
    public static void showError(String message, int line) {
        System.out.printf("%02d: ", line);

        System.out.println(message);

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
                c = procAssign();
                break;
            case PRINT:
            case PRINTLN:
            case PUSH:
            case UNSHIFT:
                c = procAction();
                break;
//            case IF:
//                procIf();
//                break;
//            case WHILE:
//                procWhile();
//                break;
//            case DO:
//                procDo();
//                break;
//            case FOREACH:
//                procForeach();
//                break;
            default:
                showError();
                break;
        }

        return c;
    }

    // <assign> ::= <var> '=' <rhs> [ <post> ] ';'
    private Command procAssign() throws IOException {
        int line = lex.getLine();

        Variable v = (Variable) procVar();
        matchToken(TokenType.ASSIGN);
        Expr e = procRhs();

        // PostCondition pc = null;
        if (current.type == TokenType.IF || current.type == TokenType.FOREACH) {
            // pc = procPost();
        }

        matchToken(TokenType.DOT_COMMA);

        AssignCommand ac = new AssignCommand(v, e, line);

//        if (pc == null) {
            return ac;
//        } else {
//            PostConditionalCommand pcc = new PostConditionalCommand(ac, pc, line);
//            return pcc;
//        }
    }

    // <action> ::= (print <rhs> | println [<rhs>] | push <rhs> ',' <rhs> | unshift <rhs> [ ',' <rhs> ]) [ <post> ] ';'
    private Command procAction() throws IOException {
        int line;
        ActionCommand ac = null;

        if (current.type == TokenType.PRINT) {
            line = lex.getLine();
            matchToken(TokenType.PRINT);
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
            // FIXME: print <rhs>
            ac = new PrintCommand(expr, false, line);
            showError();
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
            matchToken(TokenType.PUSH);
            Expr expr0 = procRhs();
            matchToken(TokenType.COMMA);
            Expr expr1 = procRhs();
//            ac = new PushCommand(expr0,expr1,line);
            // FIXME: push <rhs> ',' <rhs>
            showError();
        } else if (current.type == TokenType.UNSHIFT) {
            // FIXME: unshift <rhs> [ ',' <rhs> ])
            matchToken(TokenType.UNSHIFT);
            Expr expr0 = procRhs();
            matchToken(TokenType.COMMA);
            Expr expr1 = procRhs();
//            ac = new UnshiftCommand(expr0,expr1,line);
            showError();
        } else {
            showError();
        }

        // FIXME: [ <post> ]

        matchToken(TokenType.DOT_COMMA);

        return ac;
    }

    // <rhs> ::= <sexpr> [ '[' <rhs> ']' | '{' <rhs> '}' ]
    private Expr procRhs() throws IOException {
        int line = lex.getLine();
        Expr ret = procSExpr();
        Expr e;
        if(current.type == TokenType.OPEN_CUR){
            matchToken(TokenType.OPEN_CUR);
            e = procRhs();
            matchToken(TokenType.CLOSE_CUR);
            ret = new HashIndexExpr(ret,e,line);
        }
        else if(current.type == TokenType.OPEN_BRA){
            matchToken(TokenType.OPEN_BRA);
            e = procRhs();
            matchToken(TokenType.CLOSE_BRA);
            ret = new ListIndexExpr(ret,e,line);//TODO
        }

        // FIXME: [ '[' <rhs> ']' | '{' <rhs> '}' ]

        return ret;
    }

    // <sexpr> ::= <expr> { '.' <expr> }
    private Expr procSExpr() throws IOException {
        Expr ret;
    	
    	Expr e = procExpr();
        do{
            if(current.type == TokenType.CONCAT){
                matchToken(TokenType.CONCAT);
                ret = procExpr();
//                ret = StringconcatExpr(e,ret,lex.getLine());
            }else
                break;
        }while(true);
        

        return e;
    }

    // <expr> ::= <term> { ('+' | '-') <term> }
    private Expr procExpr() throws IOException {
    	Expr ret;
        Expr e = procTerm();
        if(current.type == TokenType.ADD || current.type == TokenType.SUB){
            Expr e1 = null;
            if(current.type == TokenType.ADD){
//                e1 = 
            }
            else{
                
            }   
            
        }

        // FIXME: { ('+' | '-') <term> }

        return e;
    }

    // <term> ::= <factor> { ('*' | '/' | '%') <factor> }
    private Expr procTerm() throws IOException {
        Expr ret;
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
            case STRING:
                e = procString();
                break;
            // FIXME: <list> | <hash>
            case OPEN_BRA:
            	e = procList();
            	break;
            case OPEN_CUR:
            	e = procHash();
            case SVAR:
            case LVAR:
            case HVAR:
                e = procVar();
                break;
            case INPUT:
            case SIZE:
            case SORT:
            case REVERSE:
            case KEYS:
            case VALUES:
            case EMPTY:
            case POP:
            case SHIFT:
                e = procFunc();
                break;
            // FIXME: '(' <sexpr> ')'
            default:
                showError();
                break;
        }

        return e;
    }

    // <var> ::= <scalar-var> | <list-var> '[' <rhs> ']' | <hash-var> '{' <rhs> '}'
    private Expr procVar() throws IOException {
        Expr e = null;
    	Variable v = null;

        switch (current.type) {
            case SVAR:
                e = procScalarVar();
                break;
                
            case LVAR:{
            	v = procListVar();
            	matchToken(TokenType.OPEN_BRA);
            	Expr idx = procRhs();
            	matchToken(TokenType.CLOSE_BRA);
            	e = new ListIndexExpr(v, idx, lex.getLine());
            	
            }break;
            
            case HVAR:{
            	v = procHashVar();
            	matchToken(TokenType.OPEN_CUR);
            	Expr idx = procRhs();
            	matchToken(TokenType.CLOSE_CUR);
            	e = new HashIndexExpr(v, idx, lex.getLine());
            	
            }break;
            
            default:
                showError();
                break;
        }

        return e;
    }

    // <func> ::= (input | size | sort | reverse | keys | values | empty | pop | shift ) <rhs>
    private Expr procFunc() throws IOException {
        int line = lex.getLine();

        FunctionType type = null;
        switch (current.type) {
            case INPUT:
                matchToken(TokenType.INPUT);
                type = FunctionType.Input;
                break;
            default:
                showError();
                break;
        }

        Expr e = procRhs();
        FunctionExpr fc = new FunctionExpr(type, e, line);
        return fc;
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

    // <string>
    private Expr procString() throws IOException {
        int line = lex.getLine();
        String str = current.token;

        matchToken(TokenType.STRING);

        StringValue sv = new StringValue(str, line);
        Expr e = new ConstExpr(sv, line);

        return e;
    }

    // <scalar-var>
    private Variable procScalarVar() throws IOException {
        String name = current.token;

        matchToken(TokenType.SVAR);

        Variable v;
        if (global.containsKey(name)) {
            v = global.get(name);
        } else {
            v = new ScalarVariable(name);
            global.put(name, v);
        }

        return v;
    }
    
 // <list-var>
    private Variable procListVar() throws IOException {
    	String name = current.token;
    	
    	matchToken(TokenType.LVAR);
    	
    	Variable v;
    	if(global.containsKey(name)) {
    		v = global.get(name);
    	} else {
    		v = new ListVariable(name);
    		global.put(name, v);
    	}

    	return v;	
    }
    
 // <hash-var>
    private Variable procHashVar() throws IOException {
    	String name = current.token;
    	
    	matchToken(TokenType.HVAR);
    	
    	Variable v;
    	if(global.containsKey(name)) {
    		v = global.get(name);
    	} else {
    		v = new HashVariable(name);
    		global.put(name, v);
    	}

    	return v;	
    }
    
    //<while> ::= while '(' <boolexpr> ')' '{' <statements> '}'
    private WhileCommand procWhile() throws IOException{
        
    	
    	matchToken(TokenType.WHILE);		// while
        matchToken(TokenType.OPEN_PAR);	// '('
		
        BoolExpr _bool = procBoolExpr();						// <boolexp>
        
        matchToken(TokenType.CLOSE_PAR);	// ')'
        matchToken(TokenType.OPEN_CUR);	// '{'
        
        Command cmd = this.procCmd();					// <statements>
        matchToken(TokenType.CLOSE_CUR);	// '}'
        return new WhileCommand(lex.getLine(),_bool,cmd);
		 
    }
    
    //<boolexpr> ::= [not] <cmpexpr> [ (and | or) <boolexpr> ]
    private BoolExpr procBoolExpr() throws IOException{
    	
    	BoolExpr _bool;
    	boolean isNot = false;
    	
    	if(current.type == TokenType.NOT){ // [
    		matchToken(TokenType.NOT);	// not ]
    		isNot = true;
    	}
    	
    	BoolExpr cmp = procCmpExpr();
    	
    	if(current.type == TokenType.AND){
    		
    		matchToken(TokenType.AND);
    		
    		
    	}else if(current.type == TokenType.OR){
    		matchToken(TokenType.OR);
    	
    	}else{
    		showError();
    	}
    	
    	//TODO
    	return null;
    	
    }
    
    //<cmpexpr> ::= <sexpr> <relop> <sexpr> | empty <rhs>
    private BoolExpr procCmpExpr() {
    	
    	BoolExpr _bool;
    	
    	if(current.type == TokenType.NUMBER || 
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
    	current.type == TokenType.POP ||
    	current.type == TokenType.SHIFT ||
    	current.type == TokenType.OPEN_PAR){
    		
    		
    	}else if(current.type == TokenType.EMPTY){
    		
    	}else{
    		showError();
    	}
    	//TODO
    	return null;
    			
    }
    
    //<list> ::= '[' [ <rhs> { ',' <rhs> } ] ']'
    private Expr procList() throws IOException{
	
	List<PrimitiveValue<?>> e;
	ListValue lv = null;
	
	matchToken(TokenType.OPEN_BRA);
	
	if(current.type == TokenType.NUMBER || 
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
	current.type == TokenType.POP ||
	current.type == TokenType.SHIFT ||
	current.type == TokenType.OPEN_PAR){
		
		e = new Vector<PrimitiveValue<?>>();
		e.add((PrimitiveValue<?>)procRhs().expr());
		
		while(current.type == TokenType.COMMA){
			matchToken(TokenType.COMMA);
			e.add((PrimitiveValue<?>)procRhs().expr());
		}
		
		lv = new ListValue(e, lex.getLine());
	}
	
	matchToken(TokenType.CLOSE_BRA);
	
	return new ConstExpr((Value<?>) lv, lex.getLine());
	
    }
    
    
    //<hash> ::= '{' [ <rhs> '=>' <rhs> { ',' <rhs> '=>' <rhs> } ] '}'
    private Expr procHash() throws IOException{
	
    Map<String, PrimitiveValue<?>> e;
	HashValue hv = null;
	
	StringValue sv;
	
	matchToken(TokenType.OPEN_CUR);
	
	if(current.type == TokenType.NUMBER || 
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
	current.type == TokenType.POP ||
	current.type == TokenType.SHIFT ||
	current.type == TokenType.OPEN_PAR){
		
		e = new HashMap<String, PrimitiveValue<?>>();
		
		sv = (StringValue) procRhs().expr();
		
		matchToken(TokenType.BIND);
		e.put(sv.value(), (PrimitiveValue<?>) procRhs().expr());
		
		while(current.type == TokenType.COMMA){
			matchToken(TokenType.COMMA);
			sv = (StringValue) procRhs().expr();
			
			matchToken(TokenType.BIND);
			e.put(sv.value(), (PrimitiveValue<?>) procRhs().expr());
		}
		
		hv = new HashValue(e, lex.getLine());
	}
	
	matchToken(TokenType.CLOSE_CUR);
	
	return new ConstExpr((Value<?>) hv, lex.getLine());
	
    }
    

    

}