<?php 

require 'infoDB.php' ;

class ExecuteQuery {

	function __construct() {
 	}

	//Tem que mudar de nome pq isso vai ser o switch de tratar as requisições. 
	function makeQuery ($json) {
		if ($json["request_name"] == "user_info") { 
			$result = $this->getInfo ("SELECT * FROM JOGADOR"); 
		}
		
		$arr = pg_fetch_all($result);
		return $arr; 
	}
	
	//O erro dessa porra eh na permissão de arquivos. (777 tudo funcionou)
 	function log ($message) {
 		$fp = fopen('/home/digao/android/workspace/MAC0499/servidor/log.txt', 'a+') or die ("Permission error");
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
		echo "Ta chegando no setInfo";
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

