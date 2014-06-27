package org.sisgri.register

class Exit extends Register{
	String category
	String name
	float value

    static constraints = {
    	category inList:['2.01 REPASSE P/ SEDE', '2.02 REP. CONGRESSO JOVENS', 
    		'2.03 REP. CONGRES. ADOLESCENTES', '2.04 REP. CONGRES. CRIANÇAS', 
    		'2.05 REP. CONGRESSO CIBE', '2.06 REP. CONGRESSO MISSÕES', 
    		'2.07 REP. OUTRAS FINALIDADES', '2.08 REP. P/ CONGREGAÇÃO', 
    		'2.09 AJUDA SOCIAL', '2.10 CONGRESSO DE JOVENS', '2.11 DEPART. MUSICAL', 
    		'2.12 DEPART. ESCOLA DOMINICAL', '2.13 DEPART. CIBE/CIBEC', '2.14 DEPART. MOCIDADE', 
    		'2.15 DEPART. ADOLESCENTES', '2.16 DEPART. CRIANÇAS', '2.17 DEPART. MISSÕES', 
    		'2.18 AUXILIO OBREIROS', '2.19 PREBENDA PASTORAL', 
    		'2.20 AGUA/LUZ/TELEFONE/AL.PASTORAL', '2.21 ANUIDADES CONVENCIONAIS', 
    		'2.22 VIAGENS E ESTADIAS', '2.23 PROG. RADIO E TELEVISÃO', 
    		'2.24 DESPESAS C/ FESTIVIDADE', '2.25 DESPESAS D/ VESTUÁRIOS', 
    		'2.26 SEGUROS EM GERAL', '2.27 PRODUTOS ALIMENTÍCIOS', 
    		'2.28 FOLHA PGAMENTO EMPREGADO', '2.29 13º SALÁRIO', '2.30 FÉRIAS DE EMPREGADOS', 
    		'2.31 INSS- FOLHA DE PAGAMENTO', '2.32 INSS- OBREIROS', '2.33 FGTS - EMPREGADOS', 
    		'2.34 CONTRIB. SINDICAL EMPREG.', '2.35 PIS-FOLHAS DE PAGAMENTO', 
    		'2.36 RECOL.IMPOSTO RENDA R. FONTE', '2.37 MULTAS JUROS S/IMPOSTOS CONT.', 
    		'2.38 OUTROS IMPOSTOS E TAXAS', '2.39 ENERGIA ELETRÍCA', '2.40 AGUA E ESGOTO', 
    		'2.41 TELEFONE / INTERNETE', '2.42 MAT. ELET. E CONST.', '2.43 MATERIAL DE LIMPESA', 
    		'2.44 MATERIAL DE EXPEDIENTE', '2.45 MATERIAL DE CONSUMO', 
    		'2.46 MATERIAL P/ SANTA-CEIA', '2.47 MANUTEÇÃO VEÍCULO', 
    		'2.48 COMBUST. LUBRIFICANTE', '2.49 ALUGUEL IMÓVEIS TEMPLO', 
    		'2.50 FRETES E TANSPORTES', '2.51 SERV. PRESTADO P. J.', '2.52 SERV. PRESTADO P. F.', 
    		'2.53 DESPESAS POSTAIS', '2.54 TARIFAS BANCÁRIAS', '2.55 REPAROS E MANUTENÇÃO', 
    		'2.56 PRESTAÇÃO DE IMÓVEIS', '2.57 FOLHETOS LITER. MAT. GRAF.', 
    		'2.58 OUTRAS DESPESAS CUSTEIO', '2.59 AQUIS. APARELHAGEM SOM', 
    		'2.60 AQUIS. MOVEIS E UTENS.', '2.61 AQUIS. MAQ. E EQUIPAMENTOS', 
    		'2.62 AQUIS, EQUIP. INFORMATICA', '2.63 AQUS. DE IMÓVEIS', '2.64 AQUIS. DE VEÍCULOS', 
    		'2.65 AQUIS. BENS PEQ. VALOR', '2.66 AQUIS. DE SOFTWARES', '2.67 OFERTAS REPASSADAS', 
    		'2.68 EVENTOS', '2.69 DESPESAS DE CARTÓRIO', '2.70 VALE TRANSPORTE', 
    		'2.71 COMP. PREB. DIRIGENTE', '2.72 PAGAMENTOS EMPREGADOS']
    	name blank: false
		value blank: false
    }
}
