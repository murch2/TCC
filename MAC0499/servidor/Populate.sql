DELETE FROM TIPO_CARTA; 
DELETE FROM CARTAS; 
DELETE FROM DICAS; 
ALTER SEQUENCE tipo_carta_id_seq RESTART WITH 1;
ALTER SEQUENCE cartas_id_seq RESTART WITH 1;


INSERT INTO JOGADOR VALUES (678655892210891,'Ana Carolina Anjos',3,3, null); 
INSERT INTO JOGADOR VALUES (686035171490949,'Ronaldo Duarte Louro',3,3, null); 

INSERT INTO TIPO_CARTA (tipo) VALUES ('História do Brasil'); 
INSERT INTO TIPO_CARTA (tipo) VALUES ('Personalidades do Brasil'); 
INSERT INTO TIPO_CARTA (tipo) VALUES ('História Mundial'); 
INSERT INTO TIPO_CARTA (tipo) VALUES ('Astros e Estrelas'); 


INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Monteiro Lobato' , 1, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Guimarães Rosa' , 1, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Fernando Henrique Cardoso' , 1, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Floriano Peixoto' , 1, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Barão do Rio Branco' , 1, 'www.google.com');

INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Airton Senna' , 2, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Chico Buarque' , 2, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Leila Diniz' , 2, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Guga' , 2, 'www.google.com');

INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('George Washington' , 3, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Bach' , 3, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Mozart' , 3, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Vivaldi' , 3, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Sigmund Freud' , 3, 'www.google.com');

INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Leonardo de Caprio' , 4, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Bette Davis' , 4, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Michael Jackson' , 4, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('George Lucas' , 4, 'www.google.com');
INSERT INTO CARTAS (nome, id_tipo_carta, link_foto) VALUES ('Madonna' , 4, 'www.google.com');

-- Monteiro Lobato
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,1,'Fundou a Companhia Petróleo do Brasil'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,1,'Escritor e jornalista, é considerado o pai da literatura infantil no Brasil'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,1,'Diplomado pela Faculdade de Direito de São Paulo'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,1,'Seu primeiro livro infantil foi "A menina do narizinho arrebitado"'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,1,'Escreveu o saci. O marquês de Rabicó, Fábulas e Jeca Tatuzinho'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,1,'Morreu no dia 4 de julho de 1948 em São Paulo'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,1,'Nasceu em Taubaté no dia 18 de abril de 1882'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,1,'Criou o Sitío do Picapau Amarelo'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,1,'Em 1918 editou seu primeiro livro de contos Urupês'); 
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,1,'Em março de 1941 foi preso por criticar a política brasileira de petróleo'); 

-- Guimarães Rosa
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,2,'Suas obras foram traduzidas para dezenas de línguas');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,2,'Sua obra mais conhecida é o romance Grande Sertão: Veredas');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,2,'Formou-se em medicina, exercendo a profissão pelo interior mineiro');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,2,'Foi secretário da embaixada em Bogotá, e conselheiro diplomático em Paris');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,2,'Morreu dias após tornar-se membro da Academia Brasileira de Letras');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,2,'Perdeu um concurso de poemas para um poeta desconhecido');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,2,'Seus poemas só foram publicados após sua morte');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,2,'Nasceu no ano de 1908 em Minas Gerais');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,2,'Autodidata, estudou alemão e russo sozinho');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,2,'Faleceu em 1967 no Rio de Janeiro');

-- Barão do Rio Branco
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,3,'Solucionou os problemas de fronteira entre o Brasil e os países vizinhos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,3,'Recebeu de D.Pedro II, em 1888 o título pelo qual é conhecido');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,3,'Notavel estadista, historiador e diplomata');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,3,'Foi Ministro das Relações Exteriores até sua morte, em 1912');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,3,'Seu nome é José Maria da Silva Paranhos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,3,'Foi professor de Geografia e História em Nova Friburgo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,3,'Estabeleceu a primeira embaixada do Brasil');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,3,'Em 1875 foi nomeado Cônsul-geral em Liverpool, Inglaterra');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,3,'Nasceu no Rio de Janeiro em 20 de abril de 1845');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,3,'Costuma-se dizer que foi ele quem traçou o mapa do Brasil');

-- Floriano Peixoto
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,4,'Em 1857 tornou-se soldado e em 1861 entrou para a escola militar');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,4,'Durante o seu governo se preocupou com a ordem pública');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,4,'Ficou conhecido como "Marechal de Ferro"');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,4,'Foi criado pelo tio, o Coronel José Vieira de Araújo Peixoto');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,4,'Lutou na Guerra do Paraguai sob o comando do General Osório');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,4,'Faleceu em 29 de junho de 1895');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,4,'Nasceu no dia 30 de abril de 1839 em Alagoas');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,4,'Assumiu a presidência após a renúncia do Marechal Deodoro');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,4,'Foi Ministro da Guerra do governo provisório');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,4,'Durante as Batalhas que participou, foi elogiado por seus comandantes');

-- Fernando Henrique Cardoso
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,5,'Perdeu a eleição para prefeito de São Paulo, em 1985 para Jânio Quadros');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,5,'Durante seu governo, os conflitos dos sem-terras agravam-se cada vez mais');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,5,'Foi exilado, tendo que morar no Chile e Espanha');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,5,'Nasceu no Rio de Janeiro no ano de 1931');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,5,'É muito criticado por suas constantes viagens ao exterior');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,5,'Foi eleito presidente em 1994, e reeleito em 1998');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,5,'Coneguiu vencer a inflação');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,5,'Ocupou o cargo de ministro da fazenda do governo Itamar Franco');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,5,'É sociólogo e professor');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,5,'Implantou no Brasil o plano Real');

-- Guga
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,6,'Em 1997 venceu o torneio de Rolland Garros, na França');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,6,'Em dezembro de 2000 tornou-se o tenista número um do mundo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,6,'Nasceu em Santa Catarina no dia 10 de setembro de 1976');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,6,'Eleito pelo Comitê Olímpico Brasileiro, o melhor atleta Brasileiro de 1999');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,6,'Iniciou sua carreira profissional aos 13 anos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,6,'É considerado o melhor tenista brasileiro');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,6,'Em junho de 2000 conquistoou o bicampeonato de Rolland Garros, na França');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,6,'Quando criança não gostava muito de tênis, preferindo futebol');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,6,'Primeiro brasileiro a chegar no topo do ranking');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,6,'Virou um ídolo nacional ainda jovem');

-- Leila Diniz
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,7,'Trabalhou como professora primária, antes de se tornar atriz');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,7,'Em 1962 entrou para o elenco da TV Globo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,7,'Casou e se separou duas vezes');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,7,'Símbolo da liberdade feminina nos anos 60');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,7,'Morreu prematuramente em um trágico acidente aéreo no dia 14 de junho de 1972');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,7,'Nasceu em 25 de março de 1945 no Rio de Janeiro');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,7,'Sua sinceridade em declarações fez com que fosse excluída da TV Globo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,7,'No cinema participou de 14 filmes, e foi considerada a musa do cinema novo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,7,'Em uma entrevista para O Pasquim em 1969 revelou detalhes de sua vida íntima');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,7,'Em junho de 1982 recebeu o prêmio de melhor atriz no festival de Adelaide');

-- Chico Buarque
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,8,'Venceu o segundo festival de MPB da TV Record em 1966');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,8,'Em 1968 sua peça "Roda Viva" é proibida pela censura');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,8,'Após ser perseguido pelo regime militar, exila-se em Roma');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,8,'Fez várias trilhas sonoras para filmes como "Dona Flor e seus dois maridos"');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,8,'Nasceu no Rio de Janeiro em 19 de junho de 1944');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,8,'Fez a versão do musical infantil italiano "Os Saltimbancos"');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,8,'Um dos grandes autores da MPB');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,8,'Compositor popular e escritor carioca');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,8,'Para fugir da censura usou pseudônimo de Julinho da Adelaide');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,8,'Escreveu a "Ópera do Malandro"');

-- Airton Senna
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,9,'Durante a carreira correu por quatro equipes Toleman, Lotus, Mclaren e Williams');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,9,'Em 1 de maio de 1994 sofre um acidente no GP de San Marino, falecendo no hospital');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,9,'Um dos grandes mestres do automobilismo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,9,'É considerado um heroi nacional');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,9,'Em 1984 estréia no grande prêmio de Monâco');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,9,'Nasceu em 21 de março de 1960 em São Paulo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,9,'Disputou 161 GPs, conseguindo 41 vitórias');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,9,'Foi campeão mundial de F-1 em 1988, 1990 e 1991');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,9,'Em 1988 conquista o seu primeiro título mundial de F-1');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,9,'O grande prêmio da Autrália em 1993 foi sua última vitória');

-- Silvio Santos
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,10,'É o proprietário do Sistema Brasileiro de Televisão');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,10,'Seu nome é Senor Abravanel');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,10,'Casou-se duas vezes e tem seis filhas');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,10,'Aos 14 anos começa a trabalhar como camelô');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,10,'Na Rádio Nacional apresenta o programa Caravana do Peru que Fala');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,10,'Em 1959 assume o Baú da Felicidade');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,10,'Nasceu em 12 de dezembro de 1930 no Rio de Janeiro');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,10,'Estréia na televisão em 1958 como locutor de comerciais');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,10,'É dono de 33 empresas entre seguradora, banco e rede de televisão');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,10,'Em 1989 tenta concorrer a Presidência da República');

-- Vivaldi
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,11,'Faleceu no ano de 1741');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,11,'Viajou com suas óperas por diversos países');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,11,'Teve seus primeiros estudos musicais através de seu pai');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,11,'Influenciou a muitos compositores, entre eles Bach');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,11,'Sua obra é composta de Óperas, Sonatas, Concertos e Musicas Religiosas');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,11,'Violinista e compositor italiano');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,11,'Sua obra mais conhecida e tocada é As quatro estações');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,11,'Foi convidado pelo Rei Carlos VI a se estabelecer na corte em Viena');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,11,'Iniciou a carreira como professor de música aos 25 anos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,11,'Nasceu em Veneza, Itália em 1678');

-- Mozart1
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,12,'Compositor Alemão nascido em 1756');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,12,'Aos 3 anos de idade, já revelava aptidão para a música');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,12,'Aprendeu piano sem nunca ter recebido uma aula');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,12,'Entre os 4 e 6 anos de idade, compôs 22 musicas');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,12,'Compôs as óperas A Flauta mágica e Bodas de Fígaro');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,12,'Um dos maiores gênios musicais da história');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,12,'Devido a uma saúde frágil morreu ainda jovem, em 1791');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,12,'Foi enterrado em uma vala comum sem nenhuma testemunha');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,12,'Em 1769 em companhia de seu pai realiza um turnê pela Itália');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,12,'Instalou-se em Viena, e casou-se em 1782');

-- Bach
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,13,'Percursor da música moderna, marcou o fim da era barroca');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,13,'Teve dois casamentos e vinte filhos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,13,'Em 1749 ficou cego, recuperando a visão pouco tempo antes de morrer');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,13,'Em 1736 foi nomeado compositor da corte do Rei da Polônia');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,13,'Sua obra caiu no esquecimento durante muito tempo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,13,'Quatro de seus filhos alcançaram êxito na música');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,13,'Em 1720 morre sua espoosa, lhe deixando 7 filhos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,13,'Um dos maiores musicos da história');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,13,'Morreu em Leipzig, Alemanha, em 28 de julho de 1750');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,13,'Nasceu em Eisenbach, Alemanha, em 21 de março de 1685');

-- George Washington
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,14,'Foi presidente 2 vezes, e se recusou a exercer o 3º mandato');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,14,'Em seu testamento pediu para seus escravos serem libertos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,14,'Preparava-se para ser empresário rural');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,14,'Lutou contra os franceses na Guerra dos 7 Anos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,14,'Comandante das forças americanas na Guerra da Indeendência');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,14,'Retirou-se da vida pública em março de 1797');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,14,'Faleceu em Virginia, EUA em 14 de dezembro de 1799');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,14,'Em 1759 se casa com uma viúva rica');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,14,'Nasceu na Virginia, EUA em 22 de fevereiro de 1732');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,14,'Primeiro presidente norte-americano, eleito em 4 de março de 1789');

-- George Lucas
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,15,'Nasceu em 14 de maio de 1944, na Califórnia, EUA');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,15,'Desprezado pelos críticos e idolatrado por uma legião interminável de fãs');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,15,'Diretor, produtor e roteirista muito importante para a história do cinema');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,15,'Esboçou a trilogia Guerra nas Estrelas se recuperando de um acidente de carro');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,15,'Estudou na Escola de Cinema da Califórnia, juntamente com Francis Ford Coppola');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,15,'Em 1970, realizou o roteiro e dirigiu seu primeiro filme');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,15,'Recebeu duas indicações ao Oscar e Globo de Ouro');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,15,'Fundou a Industrial Light Magic');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,15,'Invenou com Steven Spielberg o personagem Indiana Jones');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,15,'No auge do sucesso, divorcia-se de forma conturbada de sua esposa');

-- Michael Jackson
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,16,'Dono dos direitos de todas as músicas dos Beatles');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,16,'Atingiu ao longo de sua carreira recordes insuperáveis de vendagem');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,16,'Em 1971 se lançou em carreira solo');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,16,'Iniciou sua carreira aos 5 anos, cantando em um quinteto com seus irmãos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,16,'Nasceu em Indiana, EUA em 1958');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,16,'O auge de sua carreira foi na década de 80 com Thriller');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,16,'É um dos maiores fenômenos da música pop mundial');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,16,'Gerou polêmica com a mudança da cor de sua pele');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,16,'Escreveu em parceria com Lionel Ritchie, a canção We Are The World');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,16,'Recebeu em 1984 oito prêmios Grammy');

-- Leonardo de Caprio
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,17,'Fez seu primeiro comercial de TV aos seis anos');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,17,'Nasceu em 11 de novembro de 1974 nos EUA');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,17,'Assinou um contrato aos 14 anos de idade para trabalhar em comerciais educacionais');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,17,'Apareceu pela primeira vez na TV num dos filmes da "Lassie"');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,17,'Atuou no filme "Criaturas 3"');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,17,'Seu primeiro papel principal foi no filme "Despertar de um Homem", em 1993');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,17,'Em 1993 foi indicado ao Oscar de "Melhor Ator Coadjuvante"');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,17,'Atuou na versão moderna de "Romeu e Julieta"');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,17,'Já atuou como um viciado em drogas e um jovem deficiente');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,17,'Em 1997 estrelou no memorável filme de enorme sucesso "Titanic"');

-- Bette Davis
INSERT INTO DICAS (id, id_carta, texto) VALUES (1,18,'Em 1985 sua filha publicou um livro nada elogioso sobre a mãe');
INSERT INTO DICAS (id, id_carta, texto) VALUES (2,18,'"A malvada" foi seu filme mais famoso');
INSERT INTO DICAS (id, id_carta, texto) VALUES (3,18,'Morreu em 1989, após sofrer um derrame');
INSERT INTO DICAS (id, id_carta, texto) VALUES (4,18,'Nasceu em Lowell, Massachussets, em 1908');
INSERT INTO DICAS (id, id_carta, texto) VALUES (5,18,'Em 1936 recebeu seu primeiro Oscar por "Perigosa"');
INSERT INTO DICAS (id, id_carta, texto) VALUES (6,18,'Sua estréia na Broadway se deu em 1929');
INSERT INTO DICAS (id, id_carta, texto) VALUES (7,18,'Presidiu a Academia Cinematográfica no início da década de 40');
INSERT INTO DICAS (id, id_carta, texto) VALUES (8,18,'A Academia sempre admirou seu trabalho, a indicando 10 vezes ao Oscar');
INSERT INTO DICAS (id, id_carta, texto) VALUES (9,18,'Na II Guerra Mundial se apresentou para soldados em shows beneficentes');
INSERT INTO DICAS (id, id_carta, texto) VALUES (10,18,'No início dos anos 60 publicou um anúncio em jornal se oferecendo para trabalhar');