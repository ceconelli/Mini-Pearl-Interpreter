package lexical;

public enum TokenType {
    // special tokens
    INVALID_TOKEN,
    UNEXPECTED_EOF,
    END_OF_FILE,

    // symbols
    ASSIGN,
    DOT_COMMA,
    COMMA,
    OPEN_CUR,
    CLOSE_CUR,
    OPEN_PAR,
    CLOSE_PAR,
    OPEN_BRA,
    CLOSE_BRA,
    BIND,

    // keywords
    PRINT,
    PRINTLN,
    PUSH,
    UNSHIFT,
    IF,
    ELSE,
    WHILE,
    DO,
    FOREACH,
    NOT,
    AND,
    OR,
    INPUT,
    SIZE,
    SORT,
    REVERSE,
    KEYS,
    VALUES,
    EMPTY,
    POP,
    SHIFT,

    // operators
    EQUAL,
    DIFF,
    LOWER_THAN,
    LOWER_EQUAL,
    HIGHER_THAN,
    HIGHER_EQUAL,
    CONCAT,
    ADD,
    SUB,
    MUL,
    DIV,
    MOD,

    // others
    NUMBER,
    STRING,
    SVAR,
    LVAR,
    HVAR
};
