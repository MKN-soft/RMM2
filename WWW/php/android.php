<?php

class Android {
	
	private $db;
	private $response;
	
	function __construct() {
		
	}
	
	function __destruct() {
		
	}
	
	public function runAPI() {	
		if (isset($_POST['tag']) && $_POST['tag'] != '') {
			// get tag
			$tag = $_POST['tag'];
			
			require_once 'FunctionsDB.php';
			$this->db = new FunctionsDB();
			
			$this->response = array("tag" => $tag, "error" => FALSE);
		
			// check for a tag type
			if ($tag == 'login') {
				$this->login();
			}
			else if ($tag == 'register') {
				$this->register();
			}
			else if ($tag == 'synchro') {
				$this->synchro();
			}
			else {
				// fail
				$this->response['error'] = TRUE;
				$this->response['errorMsg'] = "Unknown 'tag' value";
				
				echo json_encode($this->response);
			}
		}
		else {
			$this->response['error'] = TRUE;
			$this->response['errorMsg'] = "Required parameter 'tag' is missing!";
			
			echo json_encode($this->response);
		} 
	}
	
	private function login() {
		$username = $_POST['username'];
		$password = $_POST['password'];
		
		
		// check for user
		$user = $this->db->getUserByUsernameAndPassword($username, $password);
		if ($user != false) {
			// user exists
			$this->response['error'] = FALSE;
			$this->response['username'] = $user['login'];
			$this->response['salt'] = $user['salt'];
			$this->response['created_at'] = $user['created_at'];
			$this->response['updated_at'] = $user['updated_at'];
			
			echo json_encode($this->response);
		}
		else {
			// user not found
			$this->response['error'] = TRUE;
			$this->response['errorMsg'] = "Incorrect username or password!";
			
			echo json_encode($this->response);
		}
	}
	
	private function register() {
		$name = $_POST['name'];
		$username = $_POST['username'];
		$password = $_POST['password'];
		
		// check if user exists
		if ($this->db->isUserExists($username)) {
			// user exists - error
			$this->response['error'] = TRUE;
			$this->response['errorMsg'] = "User exists!";
			
			echo json_encode($this->response);
		}
		else {
			// can register new user
			$user = $this->db->storeUser($name, $username, $password);
			
			if ($user) {
				// success
				$this->response["error"] = FALSE;
                $this->response["uid"] = $user["id"];
                $this->response["username"] = $user["login"];
				$this->response['salt'] = $user['salt'];
                $this->response["created_at"] = $user["created_at"];
                $this->response["updated_at"] = $user["updated_at"];
				
                echo json_encode($this->response);
			}
			else {
				// fail
				$this->response['error'] = TRUE;
				$this->response['errorMsg'] = "Error occured in Registartion!";
				
				echo json_encode($this->response);
			}
		}
	}

	private function synchro() {
		$tag = $_POST['tag'];
		$salt = $_POST['salt'];
		$time = $_POST['average_time'];
		$size = $_POST['average_board_size'];
		$movements = $_POST['average_movements'];
		
		$user = $this->db->getUserBySalt($salt);
		// check if user exists
		if ($user) {
			if ($this->db->storeStatistics($user['id'], $time, $size, $movements)) {
				// success
				$this->response['error'] = FALSE;
				$this->response['tag'] = $tag;
				$this->response['errorMsg'] = "Success!";
				
				echo json_encode($this->response);
			}
			else {
				// fail
				$this->response['error'] = TRUE;
				$this->response['errorMsg'] = "Cannot add statistics!";
				
				echo json_encode($this->response);
			}
		}
		else {
			// synchro error
			$this->response['error'] = TRUE;
			$this->response['errorMsg'] = "User is not exists!";
			
			echo json_encode($this->response);
		}
	}
	
}

$android = new Android();
$android->runAPI();
unset($android);
