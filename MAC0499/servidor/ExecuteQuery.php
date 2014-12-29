<?php 

require 'infoDB.php' ;

class ExecuteQuery {

	function __construct() {
 	}

	function SignUpQuery ($json) {
		$query = "INSERT INTO JOGADOR VALUES (". $json['userID'] .", '".$json['userName']."', 0, 0); ";
		$result = $this->setInfo($query);

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		for ($i=1; $i < 5; $i++) { 
			$query = "INSERT INTO HISTORICOESTATISTICA VALUES (". $json['userID'] .", $i, 0, 0); ";
		
			$result = $this->setInfo($query);
		
			if ($this->trataResult($result)['status'] == 'error') {
				return $this->error();
			}
		}

		return $this->ok();
	}

	function UserInfoQuery($userID) {
		$query = "SELECT nome, moedas, rodadas, foto FROM JOGADOR WHERE id = " . $userID . ";"; 
		$result = $this->getInfo($query); 
		$row = pg_fetch_row($result); 
		$jsonResult = array('status' => 'ok',
							'requestID' => 'UserInfo',  
							'nome' => $row[0],
							'moedas' => $row[1],
							'rodadas' => $row[2],
							'foto' => $row[3]
							);
		return $jsonResult;
	}

	function NewGameQuery($userID, $friendID) {
		$result = $this->criaDesafios($userID, $friendID); 
		if ($result['status'] == 'ok') {
			$result = $this->criaHistoricoJogo($userID, $friendID);
			if ($result['status'] == 'ok') {
				return $this->getTipoCarta(); 
			}
		}
		return $result; 
	}

	function criaDesafios($userID, $friendID) {
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
	
		$result = $this->setInfo($query);

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error(); 
		}

		$query = "SELECT id, texto FROM dicas WHERE id_carta = $idCarta;"; 
	
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

	function startOldRoundQuery($json) {
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

	function randomOpponentQuery($userID) {

		$query = "SELECT id, nome, foto
				  FROM JOGADOR 
				  WHERE id <> ".$userID." AND 
				        id NOT IN (SELECT id_jogador2 
				        	  	   FROM desafios 
				        	  	   WHERE id_jogador1 = ".$userID.") ORDER BY RANDOM() LIMIT 1;"; 

		$result = $this->getInfo($query);

	
		
		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		$row = pg_fetch_row($result);

		$result = $this->criaDesafios($userID, $idFriend); 	

		if ($this->trataResult($result)['status'] == 'error') {
			return $this->error();
		}

		$result = array('status' => 'ok',
		 				'dados'  =>  array('id' => $row[0],
		 								   'nome' => $row[1],
		 								   'foto' => $row[2]));
		
		return $result;
	}

	function myPictureQuery($userID, $url) {
		$query = "UPDATE JOGADOR SET foto = '$url' WHERE id = ".$userID.";"; 
	
		$result = $this->setInfo($query); 
		return $this->trataResult($result); 
	}

	function myGamesQuery($json) {
	
		$userID = $json['userID'];
		$query = "SELECT id_jogador2, pontuacao1, pontuacao2, status, nome, foto FROM DESAFIOS
						  JOIN JOGADOR ON id = id_jogador2 WHERE id_jogador1 = $userID;";

		$result = $this->getInfo($query);
 	
		$affected = pg_affected_rows($result); 

		$i = 0;

		if ($affected > 0 ) {
			while ($row = pg_fetch_array($result)) {
	 			$aux[$i] = $row; 
	 			$i++; 
	 		}

	 		$result = $aux; 

	 		if (!$aux) {
	 			return $this->error(); 
	 		}

			$index = 0; 
			$dados;  
			$this->log("2");

			foreach ($result as $key => $game) {
				$status = $game['status']; 
				$opponentID = $game['id_jogador2']; 

				if ($status == 0) {
				
					$query = "SELECT pontuacao1, pontuacao2, status, nome, foto FROM DESAFIOS
							  JOIN JOGADOR ON id = id_jogador1 WHERE id_jogador1 = $opponentID AND id_jogador2 = $userID; "; 

				
					$resultAux = $this->getInfo($query); 
					
					$row = pg_fetch_array($resultAux); 
					$statusAux = $row['status']; 
				
					if ($statusAux == 2) {
						$dados[$index++] = array('idOpponent' => $opponentID,
											 'pictureOpponent' => $row['foto'],
											 'scoreOpponent' => $row['pontuacao1'],
											 'nameOpponent' => $row['nome'],
											 'gameStatus' => "PLAY");
					}
					elseif ($statusAux == 4) {
						
					} 
					elseif ($statusAux == 3) {
						
					}
				}
				elseif ($status == 1) {
					
				}
				elseif ($status == 2) {
					$dados[$index++] = array('idOpponent' => $opponentID,
											 'pictureOpponent' => $game['foto'],
											 'scoreOpponent' => $game['pontuacao2'],
											 'nameOpponent' => $game['nome'],
											 'gameStatus' => "POKE");
				} 
				elseif ($status == 3) {

				}
				elseif ($status == 4) {

				}
			}
		}

		$result = array('status' => 'ok',
						'requestID' => 'MyGames',  
						'dados' => $dados);
	

		return $result; 
	}

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

	function setInfo ($query) {
		$connection = $this->getConnection(); 
		$result = pg_query($connection, $query);		
		$this->closeConnection($connection);
		return $result; 	
	}

	function closeConnection ($connection){
		pg_close($connection); 
	}

	function getConnection () {
		$info = new InfoDB (); 
		$string = "host = ".$info->getHost() ." port = ". $info->getPort() ." dbname = " .$info->getDBName() ." user = ". $info->getUser() ." password = " 
. $info->getPasswd(); 

		$connection = pg_connect($string) or die ('Error connecting to DataBase'); 
		return $connection; 
	}
}
?>