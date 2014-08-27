<?php 

require 'infoDB.php' ;

class ExecuteQuery {

	function __construct() {
 	}

	//Tem que mudar de nome pq isso vai ser o switch de tratar as requisições. 
	function SignUpQuery ($json) {
		$query = "INSERT INTO JOGADOR VALUES (". $json['userID'] .", '".$json['userName']."', 0, 0); ";
		$result = $this->setInfo($query); 
		return $this->trataResult($result); 
	}

	function UserInfoQuery($userID) {
		$query = "SELECT nome, moedas, rodadas FROM JOGADOR WHERE id = " . $userID . ";"; 
		$result = $this->getInfo($query); 
		$row = pg_fetch_row($result); 
		//Aqui eu tenho que fazer o array (Talvez aqui e em todos eu precise montar o array com o requestID), aqui tem que ter um if tb.
		// pra ver se eu encontrei algum cara ou não. 
		$jsonResult = array('status' => 'ok', 
							'nome' => $row[0],
							'moedas' => $row[1],
							'rodadas' => $row[2]
							);
		return $jsonResult; 
	}

	function NewGameQuery($userID, $friendID) {
		$query = "INSERT INTO DESAFIOS VALUES (".$userID.", ".$friendID.", 1, 0, 0, 0); ";
		$result = $this->setInfo($query); 
		return $this->criaDesafios($userID, $friendID);
	}

	//Essa daqui tinha que ser privada se tiver isso
	function criaDesafios($userID, $friendID) {
		
		$query = "INSERT INTO DESAFIOS VALUES (".$userID.", ".$friendID.", 1, 0, 0, 0); ";
		$result = $this->setInfo($query); 

		$query = "INSERT INTO DESAFIOS VALUES (".$friendID.", ".$userID.", 1, 0, 0, 0); ";
		$result = $this->setInfo($query); 
		
		//Aqui tem que retornar um boolean por enquanto. 
		return $this->trataResult($result); 	
	}

	//Esse affected rows é pra INSERT 
	function trataResult($result) {
		if  (!$result) {
			$this->log("Aqui 1"); 
			$result = array('status' => 'error');

		}else if (pg_affected_rows($result) == 1) {
			$this->log("Aqui 2"); 
			$result = array('status' => 'ok');	
		}
		else {
			$this->log("Aqui 3"); 
			while ($row = pg_fetch_array($result))  
     				 $result = $result;
		}
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

