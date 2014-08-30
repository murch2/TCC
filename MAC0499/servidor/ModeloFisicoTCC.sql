DROP TABLE JOGADOR CASCADE; 
DROP TABLE TIPO_CARTA CASCADE; 
DROP TABLE CARTAS CASCADE; 
DROP TABLE DICAS CASCADE; 
DROP TABLE HISTORICOESTATISTICA CASCADE; 
DROP TABLE HISTORICOJOGO CASCADE; 
DROP TABLE DESAFIOS CASCADE; 
DROP TABLE HISTORICOESTATISTICA;

CREATE TABLE JOGADOR (
	id					BIGINT PRIMARY KEY,
	nome				VARCHAR(255) NOT NULL, 
	moedas				INT CHECK (moedas >= 0), 
	rodadas				INT CHECK (rodadas >= 0)
);

CREATE TABLE TIPO_CARTA (
	id 						SERIAL PRIMARY KEY, 
	tipo 					VARCHAR(30) UNIQUE
); 

CREATE TABLE CARTAS (
	id 						SERIAL PRIMARY KEY, 
	nome					VARCHAR(255) NOT NULL,
	id_tipo_carta			SERIAL, 
	link_foto				VARCHAR(255) NOT NULL, 

	FOREIGN KEY (id_tipo_carta) REFERENCES TIPO_CARTA (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE DICAS (
	id 						INT CHECK (id > 0 AND id <= 10), 
	id_carta 				INT, 
	texto 					VARCHAR(255) NOT NULL, 

	PRIMARY KEY (id, id_carta),
	FOREIGN KEY (id_carta) REFERENCES CARTAS (id) ON DELETE CASCADE ON UPDATE CASCADE

);  

CREATE TABLE DESAFIOS (
	id_jogador1				BIGINT, 
	id_jogador2				BIGINT, 
	id_carta 				SERIAL, 
	pontuacao1 				INT NOT NULL, 
	pontuacao2	 			INT NOT NULL, 
	status 					SMALLINT, 

	PRIMARY KEY (id_jogador1, id_jogador2),
	FOREIGN KEY (id_jogador1) REFERENCES JOGADOR (id) ON DELETE CASCADE ON UPDATE CASCADE, 
	FOREIGN KEY (id_jogador2) REFERENCES JOGADOR (id) ON DELETE CASCADE ON UPDATE CASCADE, 
	FOREIGN KEY (id_carta) REFERENCES CARTAS (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE HISTORICOJOGO (
	id_jogador1				BIGINT, 
	id_jogador2				BIGINT, 
	vitorias1				INT CHECK (vitorias1 >= 0),
	vitorias2				INT CHECK (vitorias2 >= 0), 

	PRIMARY KEY (id_jogador1, id_jogador2), 
	FOREIGN KEY (id_jogador1) REFERENCES JOGADOR (id) ON DELETE CASCADE ON UPDATE CASCADE, 
	FOREIGN KEY (id_jogador2) REFERENCES JOGADOR (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE HISTORICOESTATISTICA (
	id_jogador				SERIAL, 
	id_tipo_carta			SERIAL, 
	jogadas					INT CHECK (jogadas > 0), 
	acertos					INT CHECK (acertos > 0 AND acertos <= jogadas), 

	PRIMARY KEY (id_jogador, id_tipo_carta),
	FOREIGN KEY (id_jogador) REFERENCES JOGADOR (id) ON DELETE CASCADE ON UPDATE CASCADE, 
	FOREIGN KEY (id_tipo_carta) REFERENCES  TIPO_CARTA (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Vou fazer o empate e desconsidera-lo nas estatisticas. A pontuação vai ser descrescente e caindo conforme as dicas e o tempo passa.