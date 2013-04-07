package compiler.bad;
import java_cup.runtime.Symbol;
import java.util.LinkedList;
import java.lang.StringBuilder;

%%

%public
%class Scanner
%cup
%line
%column
%state STRING COMMENT

%{
	/** Errores **/
	public LinkedList<compiler.Error> errors=new LinkedList<>();
	private void error(String message) {
		
		Symbol sym=new Symbol(Sym.error, yyline, yycolumn, yytext());
		compiler.Error e=new compiler.Error(message,sym,compiler.Error.Type.LEXIC);
		errors.add(e);
	}
	/** String **/
	StringBuilder string=new StringBuilder();
	
	/** Symbol **/
	private Symbol num_symbol(){
		int type=Sym.NERROR;
		
		try{
			Long num=new Long(yytext());
			if(num>=-16599999&&num<=16599999){
				type=Sym.INT;
			} else if (num>=-165999990000L&&num<=165999990000L){
				type=Sym.LONG;
			}
		}catch (java.lang.NumberFormatException exc){
			error("Número demasiado grande.");
		}
		return new Symbol(type, yyline, yycolumn,yytext());
	}
	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn,yytext());
	}
	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}
%}

NEWLINE	=	\n|\r|\r\n
SPACE	=	[ \t]|{NEWLINE}
DIGIT	=	[0-9]
INT		=	"-"?{DIGIT}+
FLOAT	=	{INT}+"."{INT}+
CHAR	=	"'"{ANY}"'"
ID		=	[:jletter:] [:jletterdigit:]*
BOOLEAN	=	"true"|"false"
ANY		=	[^\n\r\t ]

SIMPLE_COMMENT	=	"--"[^\*\n\r]*{NEWLINE}


%%



<YYINITIAL>
{
	{SPACE}				{}
	{SIMPLE_COMMENT}	{}
	"--*"				{yybegin(COMMENT);}
	
	
	"BD:"			{return symbol(Sym.BD);}
	"addf"			{return symbol(Sym.ADDF);}
	
	"BC:"			{return symbol(Sym.BC);}
	"BE:"			{return symbol(Sym.BE);}
	
	
	","				{return symbol(Sym.COMA);}
	"("				{return symbol(Sym.P1);}
	")"				{return symbol(Sym.P2);}
	
	"|&"			{return symbol(Sym.XOR);}
	"||"			{return symbol(Sym.OR);}
	"&&"			{return symbol(Sym.AND);}
	"!"				{return symbol(Sym.NOT);}
	
	"=="			{return symbol(Sym.EQUAL);}
	"!="			{return symbol(Sym.NEQUAL);}
	"¡!"			{return symbol(Sym.ENULL);}
	">"				{return symbol(Sym.BTHAN);}
	"<"				{return symbol(Sym.LTHAN);}
	">="			{return symbol(Sym.BETHAN);}
	"<="			{return symbol(Sym.LETHAN);}
	
	"-"				{return symbol(Sym.MINUS);}
	"+"				{return symbol(Sym.PLUS);}
	"*"				{return symbol(Sym.MULTI);}
	"/"				{return symbol(Sym.DIV);}
	
	"string"		{return symbol(Sym.KW_STRING);}
	"boolean"		{return symbol(Sym.KW_BOOLEAN);}
	"integer"		{return symbol(Sym.KW_INTEGER);}
	"long"			{return symbol(Sym.KW_LONG);}
	"float"			{return symbol(Sym.KW_FLOAT);}
	"char"			{return symbol(Sym.KW_CHAR);}
	
	
	
	"Tinny"				{return symbol(Sym.TINNY);}
	"Low"				{return symbol(Sym.LOW);}
	"Medium"			{return symbol(Sym.MEDIUM);}
	"Hight"				{return symbol(Sym.HIGHT);}
	"Heavy"				{return symbol(Sym.HEAVY);}
	"crearImagen"		{return symbol(Sym.CREAR_IMAGEN);}
	"crearPotencia"		{return symbol(Sym.CREAR_POTENCIA);}
	"crearArma"			{return symbol(Sym.CREAR_ARMA);}
	"crearEnemigo"		{return symbol(Sym.CREAR_ENEMIGO);}
	"crearEstrategia"	{return symbol(Sym.CREAR_ESTRATEGIA);}
	
	
	"mientras"		{return symbol(Sym.MIENTRAS);}
	"fin_mientras"	{return symbol(Sym.FIN_MIENTRAS);}
	"para"			{return symbol(Sym.PARA);}
	"hacer"			{return symbol(Sym.HACER);}
	"fin_para"		{return symbol(Sym.FIN_PARA);}
	"si"			{return symbol(Sym.SI);}
	"fin_si"		{return symbol(Sym.FIN_SI);}
	"otro_caso"		{return symbol(Sym.OTRO_CASO);}
	"entonces"		{return symbol(Sym.ENTONCES);}
	"Detener"		{return symbol(Sym.DETENER);}
	"Pausar"		{return symbol(Sym.PAUSAR);}
	"aumentar"		{return symbol(Sym.AUMENTAR);}
	"disminuir"		{return symbol(Sym.DISMINUIR);}
	"setf"			{return symbol(Sym.PROC_SETF);}
	
	"getMunicios"		{return symbol(Sym.FUNC_GET_MUNICIONES);}
	"getLibre"			{return symbol(Sym.FUNC_GET_LIBRE);}
	"bordeTablero"		{return symbol(Sym.FUNC_BORDE_TABLERO);}
	"getVal"			{return symbol(Sym.FUNC_GET_VAL);}
	"getf"				{return symbol(Sym.FUNC_GET_F);}
	"getBool"			{return symbol(Sym.FUNC_GET_BOOL);}
	"getStr"			{return symbol(Sym.FUNC_GET_STR);}
	"getInt"			{return symbol(Sym.FUNC_GET_INT);}
	"armaPropia"		{return symbol(Sym.FUNC_ARMA_PROPIA);}
	
	"asignarArma"		{return symbol(Sym.PROC_ASIGNAR_ARMA);}
	"asignarPaso"		{return symbol(Sym.PROC_ASIGNAR_PASO);}
	"asignarSalto"		{return symbol(Sym.PROC_ASIGNAR_SALTO);}
	"asignarHabilidad"	{return symbol(Sym.PROC_ASIGNAR_HABILIDAD);}
	"avanzar"			{return symbol(Sym.PROC_AVANZAR);}
	"girar"				{return symbol(Sym.PROC_GIRAR);}
	"println"			{return symbol(Sym.PROC_PRINTLN);}


	"circular"			{return symbol(Sym.MOV_CIRCULAR);}
	"lineal"			{return symbol(Sym.MOV_LINEAL);}
	
	
	
	\"				{string.setLength(0);yybegin(STRING);}
	
	{INT}			{return num_symbol();}
	{FLOAT}			{return symbol(Sym.FLOAT);}
	{BOOLEAN}		{return symbol(Sym.BOOLEAN);}
	{CHAR}			{return symbol(Sym.CHAR);}
	{ID}			{return symbol(Sym.ID);}
	
	
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

<COMMENT>{
	"*--"		{yybegin(YYINITIAL);}
	.|\n		{}
}


.|\n				{error("Illegal character.");}


<<EOF>>				{return symbol(Sym.EOF);}




