package compiler.psj;
import java_cup.runtime.Symbol;
import java.util.LinkedList;

%%

%public
%class Scanner
%cup
%line
%column
%state STRING

%{
	public LinkedList<compiler.Error> errors=new LinkedList<>();
	private void error(String message) {
		
		Symbol sym=new Symbol(Sym.error, yyline, yycolumn, yytext());
		compiler.Error e=new compiler.Error(message,sym,compiler.Error.Type.LEXIC);
		errors.add(e);
	}
	
	
	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn,yytext());
	}
	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}
	
	/** String **/
	StringBuilder string=new StringBuilder();
%}

NEWLINE	=	\n|\r|\r\n
SPACE	=	[ \t]|{NEWLINE}
DIGIT	=	[0-9]
INT		=	{DIGIT}+
LETTER	=	[a-zA-z]
ID		=	({LETTER}|_)({LETTER}|_|{DIGIT})+


%%



<YYINITIAL>
{
	{SPACE}			{}
	"Iniciar Personaje"		{return symbol(Sym.INICIAR);}
	"Finalizar Personaje"	{return symbol(Sym.FINALIZAR);}
	"Nombre"				{return symbol(Sym.NOMBRE);}
	"Vida"					{return symbol(Sym.VIDA);}
	"Fuerza"				{return symbol(Sym.FUERZA);}
	"Magia"					{return symbol(Sym.MAGIA);}
	"Fuerza_Magica"			{return symbol(Sym.FUERZA_MAGICA);}
	"Costo_Magico"			{return symbol(Sym.COSTO_MAGICO);}
	":"						{return symbol(Sym.COLON);}
	
	"Imagen"		{return symbol(Sym.IMAGEN);}
	
	\"				{string.setLength(0);yybegin(STRING);}
	
	
	{INT}		{return symbol(Sym.INT);}
	{ID}		{return symbol(Sym.ID);}
}

<STRING>{
	\"			{ 
					yybegin(YYINITIAL); 
					return symbol(Sym.STRING,string.toString()); 
				}
  [^\n\r\"\\]+	{ string.append( yytext() ); }
  \\t           { string.append('\t'); }
  \\n           { string.append('\n'); }
  \\r           { string.append('\r'); }
  \\\"          { string.append('\"'); }
  \\            { string.append('\\'); }
}


.|\n			{
				error("Illegal character.");
				}


<<EOF>>			{return symbol(Sym.EOF);}




