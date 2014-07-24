 <?php 
 require_once('ExecuteQuery.php');

 $a = new TrataRequisicao(); 

 class TrataRequisicao {

 	public $json; 

 	function __construct() {
 		$json_decoded = json_decode($_POST['message']); 
 		$this->json = $json_decoded; 
 		exit($this->json); 
 	}

 	function teste () {

 	}
 }
