<?php

class InfoDB {
	private $host; 
	private $dbname; 
	private $port; 
	private $user; 
	private $passwd; 

	function __construct() {
		$this->host = 'localhost'; 
		$this->dbname = 'BiografiaApp'; 
		$this->port = '5000'; 
		$this->user = 'root'; 
		$this->passwd = 'Iron666!'; 
	}

	function getHost () {
		return $this->host; 	
	}

	function getDBName () {
		return $this->dbname; 	
	}

	function getPort () {
		return $this->port; 	
	}

	function getUser () {
		return $this->user; 	
	}

	function getPasswd () {
		return $this->passwd; 	
	}

	function log ($message) {
 		$fp = fopen('/home/digao/android/workspace/MAC0499/servidor/log.txt', 'a+') or die ("Permission error");
 		fwrite($fp, $message . "\n");
 		fclose($fp); 
 	}
}

?>