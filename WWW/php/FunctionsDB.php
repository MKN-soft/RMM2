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
	
	public function storeHabits($id, $czy_sie_udalo, $data_wprowadzenia, $czestotliwosc, $kiedy_ostatnio_aktualizowano_nawyk) {
		$result = mysql_query("INSERT INTO Nawyki (uzytkownik_id, czy_sie_udalo, data_wprowadzenia, czestotliwosc, kiedy_ostatnio_aktualizowano_nawyk) 
			VALUES('$id', '$czy_sie_udalo', '$data_wprowadzenia', '$czestotliwosc', '$kiedy_ostatnio_aktualizowano_nawyk'");
		
		// check for success
		if ($result) {
			return true;
		}
		else {
            return false;
		}
	}
	
	public function storeStatistics($ilosc_nawykow, $najlepsza_passa, $srednia_dlugosc_ciagu, $procent_powodzen, $nawyki_id) {
		
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
			else {
				// wrong password
				return 2;
			}
        } else {
            // user not found
            return 1;
        }
	}
	
	// TODO
	public function getStatistics($email) {
		$result = mysql_query("SELECT * FROM Statystyki JOIN users ON users.id = statistics.user_id WHERE users.email = '$email'");
		// check for result
		if (isset($result)) {
			return $result;
		}
		else {
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
	
	public function getHabitsByUser($username) {
		$result = mysql_query("SELECT Nawyki.id, czy_sie_udalo, data_wprowadzenia, czestotliwosc, kiedy_ostatnio_aktualizowano_nawyk FROM Nawyki JOIN Uzytkownicy ON Uzytkownicy.id = Nawyki.uzytkownik_id WHERE Uzytkownicy.login = '$username'");
		// check for result
		if (isset($result)) {
			return $result;
		}
		else {
			return false;
		}
	}
	
}