<?php 

require 'infoDB.php' ;

class ExecuteQuery {

	function __construct() {
 	}

	//Tem que mudar de nome pq isso vai ser o switch de tratar as requisições. 
	function SignUpQuery ($json) {
		$this->log("Chegou no SignUp query"); 
		$query = "INSERT INTO JOGADOR VALUES (". $json['userID'] .", '".$json['userName']."', 0, 0); ";
		$this->log($query); 

		$result = $this->setInfo($query); 
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

