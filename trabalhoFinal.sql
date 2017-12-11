use trabalhoFinal1;
create TABLE titular(
	cpf int(11),
    nome varchar(80),
    cartao int (11),
    saldo double,
    debito double,
    limite double,
    endereco varchar(120),
    cidade varchar(80),
    estado varchar(10),
    mesLast int(2),
    anoLast int(8),
    total double,
    PRIMARY KEY(cpf));
    
create TABLE dependente(
	cpf int(11),
    nome varchar(80),
    cartao int (11),
    saldo double,
    debito double,
    limite double,
    endereco varchar(120),
    cidade varchar(80),
    estado varchar(10),
    cpfTitular int(11),
    mesLast int(2),
    anoLast int(8),
    total double,
    PRIMARY KEY(cpf));

create TABLE estabelecimento(
	cnpj int(14),
    nome varchar(100),
    estado varchar(10),
    endereco varchar(120),
    cidade varchar(80),
    email varchar(80),
    telefone varchar(100),
    parcelas int(2));

create TABLE fatura(
	cartao int(11),
    mes int(2),
    ano int(8),
    valor double,
    valorFinal double);

create TABLE pagamento(
	cartao int(11),
    mes int(2),
    ano int(8),
    valor double);

create TABLE compra(
	cartao int(11),
    estabelecimento int (14),
    mes int(2),
    ano int(8),
    dia int(2),
    valor double,
    parcelas int(2)); 
    


