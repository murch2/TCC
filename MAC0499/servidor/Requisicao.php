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
 				$result = $exeQuery->randomCardQuery($json['userID'], $json['friendID'], $json['tipoCarta']); 
 				break;

 			case 'RandomOpponent':
 				$result = $exeQuery->randomOpponentQuery($json['userID']); 
 				break;

			case 'MyPicture':
 				$result = $exeQuery->myPictureQuery($json['userID'], $json['url']); 
 				break;

 			case 'FinishNewRound':
 				$result = $exeQuery->finishNewRoundQuery($json); 
 				break;

 			case 'StartOldRound':
 				$result = $exeQuery->startOldRoundQuery($json); 
 				break;

 			case 'FinishOldRound':
 				$result = $exeQuery->finishOldRoundQuery($json); 
 				break;

 			case 'MyGames':
				$result = $exeQuery->myGamesQuery($json);
 			break;
 			
 			default:
 				break;
 		}
 		$result = json_encode($result); 
 		exit($result); 
 	}
 }