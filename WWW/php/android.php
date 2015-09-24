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

		if ($user != 1 && $user != 2) {
			// user exists
			$this->response['error'] = FALSE;
			$this->response['username'] = $user['login'];
			$this->response['salt'] = $user['salt'];
			$this->response['created_at'] = $user['created_at'];
			$this->response['updated_at'] = $user['updated_at'];
			
			echo json_encode($this->response);
		}
		else {
			$this->response['error'] = TRUE;
			
			if ($user == 1)
				// user not found
				$this->response['errorMsg'] = "Username not found!";
			if ($user == 2)	
				// wrong password
				$this->response['errorMsg'] = "Wrong password!";
			
			
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
		$username = $_POST['username'];
		
		$czy_sie_udalo = $_POST['czy_sie_udalo'];
		$data_wprowadzenia = $_POST['data_wprowadzenia'];
		$czestotliwosc = intval($_POST['czestotliwosc']);
		$kiedy_ostatnio_aktualizowano_nawyk = $_POST['kiedy_ostatnio_aktualizowano_nawyk'];
		
		$user = $this->db->getUserByUsername($username);
		
		// check if user exists
		if ($user) {
			if ($this->db->storeHabits($user['id'], $czy_sie_udalo, $data_wprowadzenia, $czestotliwosc, $kiedy_ostatnio_aktualizowano_nawyk)) {

				$ilosc_nawykow = intval($_POST['ilosc_nawykow']);
				$najlepsza_passa = floatval($_POST['najlepsza_passa']);
				$srednia_dlugosc_ciagu = floatval($_POST['srednia_dlugosc_ciagu']);
				$procent_powodzen = floatval($_POST['procent_powodzen']);
				$nawyki_id = $this->db->getNawykId();
				
				if ($this->db->storeStatistics($ilosc_nawykow, $najlepsza_passa, $srednia_dlugosc_ciagu, $procent_powodzen, $nawyki_id)) {
					// success
					$this->response['error'] = FALSE;
					$this->response['tag'] = $tag;
					$this->response['errorMsg'] = "Success!";
					
					echo json_encode($this->response);
				}
				else {
					// fail - cannot add statistics
					$this->response['error'] = TRUE;
					$this->response['errorMsg'] = "Cannot add statistics!";	
					
					echo json_encode($this->response);
				}
			}
			else {
				// fail - cannot add habits
				$this->response['error'] = TRUE;
				$this->response['errorMsg'] = "Cannot add habits!";
				
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
