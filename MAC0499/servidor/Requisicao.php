 <?php 
require 'ExecuteQuery.php';

 try {
 	$a = new TrataRequisicao($_POST['message']); 
 }
 catch (Exception $e) { 
 	echo "Exception: ", $e->getMessage(), "\n"; 
 }

 class TrataRequisicao {

 	public $json; 

 	function __construct($json) {
 		$json_decoded = json_decode($json, true); 
 		$this->tratandoRequisicao($json_decoded); 
 	}

 	// Se tiver herança o log tem que ir como herança
 	function log ($message) {
 		$fp = fopen('/home/digao/android/workspace/MAC0499/servidor/log.txt', 'a+') or die ("Permission error");
 		fwrite($fp, $message . "\n");
 		fclose($fp); 
 	}

 	function tratandoRequisicao ($json) { 
 		$j = json_decode($json); 
 		$r = array('chavinha' => 'valorzinho');
 		$d = json_encode($r); 
 		exit($d); 
 		// if ($json["request_type"] == "infoDB") {
 		// 	$cc = new ExecuteQuery (); 
 		// 	$result = $cc->makeQuery($json); 
 		// 	exit(json_encode($result)); 
 		// 	// $this->log ($result); 
 		// } else {
 		// 	$this->log("Entra aqui no else");
 		// }
 	}
 }