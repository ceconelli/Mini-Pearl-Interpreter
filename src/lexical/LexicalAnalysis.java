package lexical;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.PushbackInputStream;

import java.util.*;

public class LexicalAnalysis implements AutoCloseable {

    private int line;
    private SymbolTable st;
    private PushbackInputStream input;

    public LexicalAnalysis(String filename) throws LexicalException {
        try {
            input = new PushbackInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            throw new LexicalException("Unable to open file");
        }

        st = new SymbolTable();
        line = 1;
    }

    public void close() throws IOException {
        input.close();
    }

    public int getLine() {
        return this.line;
    }

    public Lexeme nextToken() throws IOException {
         Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);

         int state = 1;
         while (state != 12 && state != 13){
           int c = input.read();

           switch(state){

             case 1:
				if(c == ' ' || c == '\t' || c == '\r'){
					state = 1;
				}else if(c == '\n'){
					line++;
					state = 1;
				}else if (c == '#'){
					state = 2;
				}else if(Character.isDigit(c)){
					lex.token += (char) c;
					lex.type = TokenType.NUMBER;
					state = 3;
				}else if(c == '!'){
					lex.token += (char) c;
					state = 4;
				}else if(c == '='){
					lex.token += (char) c;
					state = 5;
				}else if(c == '<' || c == '>'){
					lex.token += (char) c;
					state = 6;
				}else if(c == ';' || c == '.' || c == ',' || c == '(' ||
						c == ')' || c == '{' || c == '}' || c == '[' ||
						c == ']' || c == '+' || c == '-' ||	c == '*' ||
						c == '/'){
					lex.token += (char) c;
					state = 12;
				}else if(Character.isLetter(c)){
					lex.token += (char) c;
					state = 7;
				}else if(c == '%'){
					lex.token += (char) c;
					state = 9;
				}else if(c == '$'){
					lex.type = TokenType.SVAR;
					lex.token += (char) c;
					state = 10;
				}else if(c == '@'){
					lex.type = TokenType.LVAR;
					lex.token += (char) c;
					state = 10;
				}else if(c == '\"'){
					lex.type = TokenType.STRING;
					state = 8;
				}else if(c == -1){
					lex.type = TokenType.END_OF_FILE;
					state = 13;
				}else{
					lex.type = TokenType.INVALID_TOKEN;
					state = 13;
				}
				
             break;

             case 2:
            	 
            	 if(c == '\n'){
            		 state = 1;
            	 }else if(c == -1){
 					lex.type = TokenType.END_OF_FILE;
 					state = 13;
 				 }
            	 
             break;

             case 3:
            	 
            	 if(Character.isDigit(c)){
 					lex.token += (char) c;
 					state = 3;
            	 }else if(c == -1){
            		 lex.type = TokenType.UNEXPECTED_EOF;
            		 state = 13;
            	 }else{
            		input.unread(c);
            		state = 13;
            	 }
            	 
             break;

             case 4:

            	 if(c == '='){
            		 lex.token += (char) c;
            		 state = 12;
            	 }else if(c == -1){
            		 lex.type = TokenType.UNEXPECTED_EOF;
            		 state = 13;
            	 }else{
            		 lex.type = TokenType.INVALID_TOKEN;
            		 state = 13;
            	 }
            	 
             break;

             case 5:
            	 
            	 if(c == '>' || c == '='){
            		 lex.token += (char) c;
            		 state = 12;
            	 }else if(c == -1){
            		 lex.type = TokenType.UNEXPECTED_EOF;
            		 state = 13; 
            	 }else{
            		 input.unread(c);
            		 state = 12;
            	 }
            	 
             break;

             case 6:

            	 if(c == '='){
            		 lex.token += (char) c;
            		 state = 12;
            	 }else if(c == -1){
            		 lex.type = TokenType.UNEXPECTED_EOF;
            		 state = 13; 
            	 }else{
            		 input.unread(c);
            		 state = 12;
            	 }
            	 
             break;

             case 7:
            	 
            	 if(Character.isDigit(c) || Character.isLetter(c)){
            		 lex.token += (char) c;
            	 }else if(c == -1){
            		 lex.type = TokenType.UNEXPECTED_EOF;
            		 state = 13; 
            	 }else{
            		 input.unread(c);
            		 state = 12;
            	 }
            	 
             break;

             case 8:
            	 
            	 if(c == '\"'){
            		 input.unread(c);
            		 state = 13;
            	 }else if(c == -1){
            		 lex.type = TokenType.UNEXPECTED_EOF;
            		 state = 13; 
            	 }else{
            		 lex.token += (char) c;
            	 }
            	 
             break;

             case 9:
            	 
            	 if(Character.isDigit(c) || Character.isLetter(c)){
            		 lex.token += (char) c;
            		 state = 11;
            	 }else if(c == -1){
            		 lex.type = TokenType.UNEXPECTED_EOF;
            		 state = 13; 
            	 }else{
            		 input.unread(c);
            		 state = 12;
            	 }
            	 
             break;

             case 10:
            	 
                 if(Character.isDigit(c) || Character.isLetter(c)) {
                   lex.token += (char)c;
                   state = 11;
                 }else if(c == -1){
            		lex.type = TokenType.UNEXPECTED_EOF;
            		state = 13; 
                 }else{
                   lex.type = TokenType.INVALID_TOKEN;
                   state=13;
                 }
                 
             break;
               
	         case 11:
	        	 
	        	 if(Character.isDigit(c)||Character.isLetter(c)){
	        		 lex.token += (char)c;
	                 state = 11;
	             }else if(c == -1){
	            	 lex.type = TokenType.UNEXPECTED_EOF;
	            	 state = 13; 
	             }else{
	            	 input.unread(c);
	            	 state=13;
	             }
	        	 
        	 break;
	
	         case 12:
	         
	        	 lex.type = st.find(lex.token);
	             
	         break;
	         
           }
           
         }

         // TODO: implement me.

         // HINT: read a char.
         // int c = input.read();

         // HINT: unread a char.
         // if (c != -1)
         //     input.unread(c);

         return lex;
    }
}