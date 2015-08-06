<?php

class FunctionsDB {

	private $db;
	
	function __construct() {
		require_once 'ConnectDB.php';
		// connecting to database
		$this->db = new ConnectDB();
		$this->db->connect();
	}
	
	function __destruct() { }
	
	public function storeUser($name, $email, $password) {
		$salt = uniqid('', true);
		$encrypted_password = MD5($password);
		$result = mysql_query("INSERT INTO Uzytkownicy(login, haslo, salt, created_at, updated_at) 
			VALUES ('$email', '$encrypted_password', '$salt', NOW(), NOW())");
		// check for success
		if ($result) {
			// get user details
			$id = mysql_insert_id();
			$result = mysql_query("SELECT * FROM Uzytkownicy WHERE id = $id");
			
			return mysql_fetch_array($result);
		}
		else {
			return false;
		}
	}
	// TODO poprawa query!!
	public function storeStatistics($id, $time, $size, $movements) {
		$result = mysql_query("INSERT INTO Statystyki (user_id, time, size, movements, updated_at) 
			VALUES('$id', '$time', '$size', '$movements', NOW())");
		
		// check for success
		if ($result) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public function getUserBySalt($salt) {
		$result = mysql_query("SELECT * FROM users WHERE salt ='$salt'") or die(mysql_error());
		// check for result
		$no_of_rows = mysql_num_rows($result);
		if ($no_of_rows > 0) {
			$result = mysql_fetch_array($result);
			return $result;
		}
		else {
			return false;
		}
	}
	
	public function getUserByUsernameAndPassword($username, $password) {
		$result = mysql_query("SELECT * FROM Uzytkownicy WHERE login = '$username'") or die(mysql_error());
		
        // check for result 
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            $encrypted_password = $result['haslo'];
            $hash = MD5($password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
	}
	
	public function getStatistics($email) {
		$result = mysql_query("SELECT * FROM Statystyki JOIN users ON users.id = statistics.user_id WHERE users.email = '$email'");
		// check for result
		if (isset($result)) {
			return $result;
		}
		else {
			die(mysql_error());
			return false;
		}
	}
	
	public function isUserExists($email) {
        $result = mysql_query("SELECT login from Uzytkownicy WHERE login = '$email'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed 
            return true;
        } else {
            // user not existed
            return false;
        }
    }
	
}