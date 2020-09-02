package proyectoanalizador.backed.analizador;

import java_cup.runtime.*;
import proyectoanalizador.gui.Frame;

%%
%class Lexer
%type java_cup.runtime.Symbol
%cup
%public
%cupdebug
%full
%line
%column
%char

LineTerminator = \r|\n|\r\n
InputCaracter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

/* Comentarios */
Comment = {ComentarioTradicional} | {FinLineaComment} | {DocumentoComentado}
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinLineaComment = "//" {InputCaracter}* {LineTerminator}?
DocumentoComentado = "/**" {CommentContenido} "*"+ "/"
CommentContenido = ( [^*] | \*+ [^/*] )*
ActionCode = "{" ([^};]+ "};" | [^}]+ "};")
Code = "%%" [^%]+ "%%"
ExpresionComillas = "\"" [^\"]+ "\""

LMin = [a-záéíóú]
LMay = [A-ZÁÉÍÓÚ]
D = [0-9]
S = [°@ł€¶ŧ←↓→%øþ·ĸŋđðßæ«»¢“”½¬!$¡~_'.¨¿¡]

%{

    public Frame frame;

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }

    private Symbol symbol(int type){
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

%}
%%

<YYINITIAL> {

    /* Espacios*/
    
    {WhiteSpace}      {/*Ignore*/}
    ("â")*          {/*Ignore*/}
    (" ")*            {/*Ignore*/}
    {Comment}         {/*Ignore*/}

    /* Reglas Semanticas */ 

    {ActionCode}            {return new Symbol(sym.REGLAS_SEMANTICAS, yychar, yyline, new String(yytext()));}                                             
    
    /* Codigo Fuente */ 

    {Code}                  {return new Symbol(sym.CODIGO_FUENTE, yychar, yyline, new String(yytext()));}                                             

    /* Comillas */
    
    {ExpresionComillas}     {return new Symbol(sym.COMILLAS, yychar, yyline, new String(yytext()));}  

    /* Tokens */
    
    /* Palabras Reservadas */
    
    /* Informacion Lenguaje */

    ( nombre )              {return new Symbol(sym.NOMBRE, yychar, yyline, yytext());}
    ( version )             {return new Symbol(sym.VERSION, yychar, yyline, yytext());}
    ( autor )               {return new Symbol(sym.AUTOR, yychar, yyline, yytext());}
    ( extension )           {return new Symbol(sym.EXTENSION, yychar, yyline, yytext());}
    ( lanzamiento )         {return new Symbol(sym.LANZAMIENTO, yychar, yyline, yytext());}
    
    /* Terminales y No Terminales */
    
    ( terminal )            {return new Symbol(sym.TERMINAL, yychar, yyline, yytext());}
    ( no )                  {return new Symbol(sym.NO, yychar, yyline, yytext());}
        
    /* Tipo de Dato */

    ( entero )              {return new Symbol(sym.ENTERO, yychar, yyline, yytext());}
    ( cadena )              {return new Symbol(sym.CADENA, yychar, yyline, yytext());}
    ( real )                {return new Symbol(sym.REAL, yychar, yyline, yytext());}

    /* Otros */

    ( "=" )     {return new Symbol(sym.IGUAL, yychar, yyline, yytext());}
    ( ";" )     {return new Symbol(sym.PUNTO_COMA, yychar, yyline, yytext());}
    ( ":" )     {return new Symbol(sym.DOS_PUNTOS, yychar, yyline, yytext());}

//    ( "\"" )    {return new Symbol(sym.COMILLAS, yychar, yyline, yytext());}
    ( "(" )     {return new Symbol(sym.PARENTESIS_A, yychar, yyline, yytext());}
    ( ")" )     {return new Symbol(sym.PARENTESIS_C, yychar, yyline, yytext());}
    ( "[" )     {return new Symbol(sym.LLAVE_A, yychar, yyline, yytext());}
    ( "]" )     {return new Symbol(sym.LLAVE_C, yychar, yyline, yytext());}
    ( "-" )     {return new Symbol(sym.MENOS, yychar, yyline, yytext());}
    ( "?" )     {return new Symbol(sym.INTERROGACION_C, yychar, yyline, yytext());}
    ( "*" )     {return new Symbol(sym.MULTIPLICACION, yychar, yyline, yytext());}
    ( "+" )     {return new Symbol(sym.MAS, yychar, yyline, yytext());}
    ( "," )     {return new Symbol(sym.COMA, yychar, yyline, yytext());}
    ( "&" )     {return new Symbol(sym.AMPERSAND, yychar, yyline, yytext());}
    ( "|" )     {return new Symbol(sym.BARRA_VERTICAL, yychar, yyline, yytext());}
    ( "\\n" )   {return new Symbol(sym.SALTO_LINEA, yychar, yyline, yytext());}
    ( "\\t" )   {return new Symbol(sym.TABULADOR, yychar, yyline, yytext());}
    ( "\\b" )   {return new Symbol(sym.ESPACIO_B, yychar, yyline, yytext());}

    /* Expresiones */
    
    /* Reglas Semanticas */
            
    /* Numero */

    {D}+                                                {return new Symbol(sym.NUMERO, yychar, yyline, new String(yytext()));}

    /* Version */
    
    ({D}+ (("."){D})*)                                  {return new Symbol(sym.VERSION_NUMEROS, yychar, yyline, new String(yytext()));}

    /* Entradas */

    ({LMin})                                            {return new Symbol(sym.LETRA_MIN, yychar, yyline, new String(yytext()));}
        
    ({LMin}+)                                           {return new Symbol(sym.MINUSCULAS, yychar, yyline, new String(yytext()));}
    
    ({LMay})                                            {return new Symbol(sym.LETRA_MAY, yychar, yyline, new String(yytext()));}
    
    ({LMay}+)                                           {return new Symbol(sym.MAYUSCULA, yychar, yyline, new String(yytext()));}
    
    ({LMay}|{LMin}|"_")({LMay}|{LMin}|"_"|{D})*         {return new Symbol(sym.IDENTIFICADOR, yychar, yyline, new String(yytext()));}
    
    ({S}+)                                              {return new Symbol(sym.SIGNO, yychar, yyline, new String(yytext()));}

    /* Error */

    . {frame.addError(
                      "\nError Lexico: "
              + "\n\tLinea #:                     << " + (yyline + 1) + " >> "
              + "\n\tColumna #:                   << " + (yycolumn + 1) + " >> "
              + "\n\tToken NO Reconocido:         << " + yytext() + " >> ");
    } 
}
