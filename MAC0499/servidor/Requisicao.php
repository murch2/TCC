 <?php 
require 'ExecuteQuery.php';

 try {
 	$a = new TrataRequisicao($_POST['message']); 
 }
 catch (Exception $e) { 
 	echo "Exception: ", $e->getMessage(), "\n"; 
 }

 class TrataRequisicao {

 	function __construct($json) {
 		$json = json_decode($json, true); 
 		$json = $json['message']; 
 		$this->tratandoRequisicao($json); 
 	}

 	// Se tiver herança o log tem que ir como herança
 	function log ($message) {
 		$fp = fopen('/home/digao/android/workspace/TCC/MAC0499/servidor/log.txt', 'a+') or die ("Permission error");
 		fwrite($fp, $message . "\n");
 		fclose($fp); 
 	}

 	function tratandoRequisicao ($json) { 
 		$exeQuery = new ExecuteQuery (); 
 		switch ($json['requestID']) {
 			case 'SignUp':
 				$result = $exeQuery->SignUpQuery($json);
 				break;

 			case 'UserInfo':
 				$result = $exeQuery->UserInfoQuery($json['userID']);
 				break;

 			case 'NewGame':
 				$result = $exeQuery->NewGameQuery($json['userID'], $json['friendID']); 
 				break;

 			case 'RandomCard':
 				$result = $exeQuery->randomCardQuery($json['userID'], $json['tipoCarta']); 
 				break;

 			case 'RandomOpponent':
 				$result = $exeQuery->randomOpponentQuery($json['userID']); 
 				break;

			case 'MyPicture':
 				$result = $exeQuery->myPictureQuery($json['userID'], $json['url']); 
 				break;
 			
 			default:
 				break;
 		} 
 		//na verdade vou dar um ok no result ou error. 
 		$result = json_encode($result); 
 		exit($result); 
 	}
 }
