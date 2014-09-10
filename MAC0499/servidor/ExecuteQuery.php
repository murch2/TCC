<?php 

require 'infoDB.php' ;

class ExecuteQuery {

	function __construct() {
 	}

	//Tem que mudar de nome pq isso vai ser o switch de tratar as requisições. 
	function SignUpQuery ($json) {
		$query = "INSERT INTO JOGADOR VALUES (". $json['userID'] .", '".$json['userName']."', 0, 0); ";
		$result = $this->setInfo($query);

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		for ($i=1; $i < 5; $i++) { 
			$query = "INSERT INTO HISTORICOESTATISTICA VALUES (". $json['userID'] .", $i, 0, 0); ";
			$this->log($query); 
			$result = $this->setInfo($query);
			if ($this->trataResult($result)['status'] == 'error') {
				return $this->error();
			}
		}

		return $this->trataResult($result);
	}

	function UserInfoQuery($userID) {
		$this->log("Metodo UserInfoQuery");
		$query = "SELECT nome, moedas, rodadas FROM JOGADOR WHERE id = " . $userID . ";"; 
		$result = $this->getInfo($query); 
		$row = pg_fetch_row($result); 
		//Aqui eu tenho que fazer o array (Talvez aqui e em todos eu precise montar o array com o requestID), aqui tem que ter um if tb.
		// pra ver se eu encontrei algum cara ou não). (É só retornar erro se der erro igual aos novos que eu estou fazendo)
		$jsonResult = array('status' => 'ok',
							'requestID' => 'UserInfo',  
							'nome' => $row[0],
							'moedas' => $row[1],
							'rodadas' => $row[2]
							);
		return $jsonResult;
	}

	// Mudar esse metodo pra retornar erro, igual aos outros que eu estou fazendo agora. 
	function NewGameQuery($userID, $friendID) {
		$result = $this->criaDesafios($userID, $friendID); 
		$this->log("Depois de criar os desafios" . $result['status']);
		if ($result['status'] == 'ok') {
			$result = $this->criaHistoricoJogo($userID, $friendID);
			if ($result['status'] == 'ok') {
				return $this->getTipoCarta(); 
			}
		}
		return $result; 
	}

	//Essa daqui tinha que ser privada se tiver isso, e precisa ser enxuta
	function criaDesafios($userID, $friendID) {
		// Podia controlar isso com a pontuação mas foda-se
		// A ideia é que quando eu crio eu vou jogar. 
		// (O status = 0 vai representar que ninguem jogou)
		// (O status = 1 vai representar que o primeiro jogador está jogando)
		// (O status = 2 vai representar que o primeiro jogador acabou de jogar)
		// (O status = 3 vai representar que o segundo jogador está jogando)
		// (O status = 4 vai representar que o segundo jogador acabou de jogar)
		// Quando o primeiro jogador for jogar e tiver status 4 eu posso mandar as infos pra ele ver e atualizar as infos do jogo


		// sempre vai ter que ter 
		$query = "INSERT INTO DESAFIOS VALUES (".$userID.", ".$friendID.", 1, -1, -1, 0); ";
		$result = $this->setInfo($query);
	
		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		$query = "INSERT INTO DESAFIOS VALUES (".$friendID.", ".$userID.", 1, -1, -1, 0); ";
		$result = $this->setInfo($query);

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		return $this->ok(); 
	}

	function criaHistoricoJogo ($userID, $friendID) {
		if ($userID < $friendID) 
			$query = "INSERT INTO HISTORICOJOGO VALUES (".$userID.", ".$friendID.", 0, 0); ";
		else 
			$query = "INSERT INTO HISTORICOJOGO VALUES (".$friendID.", ".$userID.", 0, 0); ";

		$result = $this->setInfo($query);		

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		return $this->ok(); 
	}

	function getTipoCarta () {
		$query = "SELECT id, tipo FROM TIPO_CARTA;";	
		$result = $this->getInfo($query);
		return $this->trataResult($result); 
	} 

	function randomCardQuery($userID, $friendID, $tipoCarta) {
		$query = "SELECT id, nome, link_foto FROM cartas WHERE id_tipo_carta = $tipoCarta ORDER BY RANDOM() LIMIT 1;"; 
		$result = $this->getInfo($query);

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error(); 
		}

		$row = pg_fetch_array($result);
		$idCarta = $row['id']; 

		$query = "UPDATE DESAFIOS SET id_carta = $idCarta, status = 1 WHERE id_jogador1 = $userID and id_jogador2 = $friendID;"; 
		$this->log("Update = " . $query);
		$result = $this->setInfo($query);

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error(); 
		}

		$query = "SELECT id, texto FROM dicas WHERE id_carta = $idCarta;"; 
		$this->log($query);
		$resultTips = $this->getInfo($query);
		$arrayTips = $this->trataResult($resultTips);

		if ($arrayTips['status'] == 'error') {
			return $this->error();
		}

		$result = array('status' => 'ok',
		 				'dados'  => array('nomeCarta' => $row['nome'],
		 								  'foto' => $row['link_foto'],
		 								  'idCarta' => $idCarta,
		 								  'dicas' => $arrayTips['dados']));
		
		return $result;
	}

	// Esse nome eh ruim pq isso acotnece quando o cara acaba o primeiro round dele. 
	// Acho que esse mtodo precisa chamar finishNewRound
	function finishNewRoundQuery($json) {
		$score = $json['score']; 
		$userID = $json['userID']; 
		$friendID = $json['friendID']; 
		$correct = $json['correct'];
		$tipoCartaID = $json['tipoCartaID'];
		
		$query = "UPDATE DESAFIOS SET pontuacao1 = $score, status = 2 WHERE id_jogador1 = $userID and id_jogador2 = $friendID;"; 

		$result = $this->setInfo($query);

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error(); 
		}
		
		if ($correct) {
			$query = "UPDATE HISTORICOESTATISTICA SET jogadas = jogadas + 1, acertos = acertos + 1
						WHERE id_jogador = $userID and id_tipo_carta = $tipoCartaID;"; 
		} 
		else {
			$query = "UPDATE HISTORICOESTATISTICA SET jogadas = jogadas + 1
						WHERE id_jogador = $userID and id_tipo_carta = $tipoCartaID;"; 
		}

		$result = $this->setInfo($query);		

		return $this->trataResult($result);
	}

	//Aqui eu só preciso atualizar o status
	function startOldRoundQuery($json) {
		// Preciso alem disso devolver a pontuação do cara que já fez o jogo 
		$score = $json['score']; 
		$userID = $json['userID']; 
		$friendID = $json['friendID']; 
		$query = "UPDATE desafios SET status = 3 WHERE id_jogador1 = $friendID and id_jogador2 = $userID;"; 
		

		$result = $this->setInfo($query);

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error(); 
		}

		return $this->ok(); 
	}

	// Esse vai ser o de quando o cara clicar no game na tela de mainMenu. 
	// Vou criar apenas uma funcao ao inves de duas para o finish OLD e new Round que recebe o status de parametro. 
	function finishOldRoundQuery($json) {
		$score = $json['score']; 
		$userID = $json['userID']; 
		$friendID = $json['friendID']; 
		$correct = $json['correct'];
		$tipoCartaID = $json['tipoCartaID'];

		$query = "UPDATE desafios SET pontuacao2 = $score, status = 4 WHERE id_jogador1 = $friendID and id_jogador2 = $userID;"; 

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error(); 
		}
		
		if ($correct) {
			$query = "UPDATE HISTORICOESTATISTICA SET jogadas = jogadas + 1, acertos = acertos + 1
						WHERE id_jogador = $userID and id_tipo_carta = $tipoCartaID;"; 
		} 
		else {
			$query = "UPDATE HISTORICOESTATISTICA SET jogadas = jogadas + 1
						WHERE id_jogador = $userID and id_tipo_carta = $tipoCartaID;"; 
		}

		$result = $this->setInfo($query);		

		return $this->trataResult($result);
	}

	//Falta fazer não pegar as pessoas que eu tenho jogos. 
	function randomOpponentQuery($userID) {

		$query = "SELECT id, nome, foto
				  FROM JOGADOR 
				  WHERE id <> ".$userID." AND 
				        id NOT IN (SELECT id_jogador2 
				        	  	   FROM desafios 
				        	  	   WHERE id_jogador1 = ".$userID."); "; 

		$result = $this->getInfo($query);
		
		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		$row = pg_fetch_array($result);
		$idFriend = $row['id']; 

		$result = $this->criaDesafios($userID, $idFriend); 	

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		$result = array('status' => 'ok',
		 				'dados'  => $row);
		
		return $result;
	}

	function myPictureQuery($userID, $url) {
		$query = "UPDATE JOGADOR SET foto = '$url' WHERE id = ".$userID.";"; 
		$this->log($query);
		$result = $this->setInfo($query); 
		return $this->trataResult($result); 
	}

	function myGamesQuery($json) {
		$this->log("MyGamesQuery");
		$userID = $json['userID']; 
		$query = "SELECT id_jogador2, pontuacao1, pontuacao2, status, nome, foto FROM DESAFIOS
						  JOIN JOGADOR ON id = id_jogador2 WHERE id_jogador1 = $userID;"; 

		$result = $this->trataResult($this->getInfo($query)); 

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		$index = 0; 
		$dados; 
		foreach ($result['dados'] as $key => $game) {
			$status = $game['status']; 
			$opponentID = $game['id_jogador2']; 
			$this->log("Oponente ID = " . $opponentID);

			// Aqui eu tenho que ver se o jogo do cara é novo. Se o outro cara criou um jogo com ele.
			if ($status == 0) {
				$this->log("Status = 0");
				$query = "SELECT pontuacao1, pontuacao2, status, nome, foto FROM DESAFIOS
						  JOIN JOGADOR ON id = id_jogador1 WHERE id_jogador1 = $opponentID AND id_jogador2 = $userID; "; 

				$this->log($query);
				$resultAux = $this->getInfo($query); 
				
				// Acho que eu posso fazer isso sem while pq eu tenho certeza que eh unico. 
				$row = pg_fetch_array($resultAux); 
				$statusAux = $row['status']; 
				$this->log("StatusAux = " . $statusAux);
				// Se tiver 2 ou 4 eh minha vez. Se tiver 3 é WO 
				if ($statusAux == 2) {
					$dados[$index++] = array('idOpponent' => $opponentID,
										 'pictureOpponent' => $row['foto'],
										 'scoreOpponent' => $row['pontuacao1'],
										 'nameOpponent' => $row['nome'],
										 'gameStatus' => "PLAY");
				}
				elseif ($statusAux == 4) {
					// Talvez não precise lidar com isso. 
					// Aqui eu ainda não mandei o meu jogo pro cara pro cara. 
				} 
				elseif ($statusAux == 3) {
					// Aqui é WO
				}
			}
			elseif ($status == 1) {
				// Aqui dane-se eu tenho qeu apagar as informações desse jogo pq o cara não acabou e quer começar de novo. 
			}
			elseif ($status == 2) {
				// Aqui é o clássico POKE, já joguei e falta o cara jogar. 
				$this->log("Status = 2");
				$dados[$index++] = array('idOpponent' => $opponentID,
										 'pictureOpponent' => $game['foto'],
										 'scoreOpponent' => $game['pontuacao2'],
										 'nameOpponent' => $game['nome'],
										 'gameStatus' => "POKE");
			} 
			elseif ($status == 3) {
				// O cara tá jogando, por enquanto é POKE
			}
			elseif ($status == 4) {
				// O cara já acabou de jogar o que v enviou pra ele, agora preciso buscar informações do que ele mandou pra vc, 
				// pra decidir se é POKE OU PLAY.
			}

		}
		
		$result = array('status' => 'ok',
						'requestID' => 'MyGames',  
						'dados' => $dados);
	

		return $result; 
	}

	// Esse trata result é só pra leitura e precisa receber um parametro de affected rows.
	// acho que tem que fazer um pg_affected_rows(result) com 0 mas ok pro caso do myGamesQuery
	function trataResult($result) {
		if (!$result) {
			$result = $this->error(); 
		}
		else if (pg_affected_rows($result) == 1) {
			$result = $this->ok(); 
		}
		else {
			$aux;
			$i = 0; 
			while ($row = pg_fetch_array($result)) {
     			$aux[$i] = $row; 
     			$i++; 
     		}
     		$result = array('status' => 'ok',
							 'dados' => $aux);
		}
		return $result; 
	}

	function error () {
		$result = array('status' => 'error');
		return $result;
	}

	function ok () {
		$result = array('status' => 'ok');	
		return $result;
	}
	
	//O erro dessa porra eh na permissão de arquivos. (777 tudo funcionou)
 	function log($message) {
 		$fp = fopen('/home/digao/android/workspace/TCC/MAC0499/servidor/log.txt', 'a+') or die ("Permission error");
 		fwrite($fp, $message . "\n");
 		fclose($fp); 
 	}

	function getInfo ($query) {
		$connection = $this->getConnection(); 
		$result = pg_query($connection, $query);		
		$this->closeConnection($connection);
		return $result; 
	}

	//Pensar aqui em um jeito de devolver ok, ou not ok. 
	function setInfo ($query) {
		$connection = $this->getConnection(); 
		$result = pg_query($connection, $query);		
		$this->closeConnection($connection);
		return $result; 	
	}

	function closeConnection ($connection){
		pg_close($connection); 
	}

	//Preciso fechar as conexões
	function getConnection () {
		$info = new InfoDB (); 
		$string = "host = ".$info->getHost() ." port = ". $info->getPort() ." dbname = " .$info->getDBName() ." user = ". $info->getUser() ." password = " 
. $info->getPasswd(); 

		//Isso aqui retorna um int que indica se a conexão falhou ou nao.
		$connection = pg_connect($string) or die ('Error connecting to DataBase'); 
		return $connection; 
	}
}
?>