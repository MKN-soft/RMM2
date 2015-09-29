<?php

/**
 * Methods for insert, update, select from database.
 */

class FunctionsDB {

	private $db;
	private $nawyk_id;
	
	function __construct() {
		require_once 'ConnectDB.php';
		// connecting to database
		$this->db = new ConnectDB();
		$this->db->connect();
	}
	
	function __destruct() {	}
	
	public function storeHabits($id, $czy_sie_udalo, $data_wprowadzenia, $czestotliwosc, $kiedy_ostatnio_aktualizowano_nawyk, $nazwa_nawyku, $odstep_czestotliwosci) {
		$this->db->connect();
		
		// FIND HABIT
		$sql = "CALL getHabitsByTitle('$nazwa_nawyku', '$id')";
		$result = mysql_query($sql);
		
		$habit = mysql_fetch_row($result);	
		$this->db->close();
			
		// check for success
		if ($habit[0]) {
			// UPDATE HABIT
			$this->db->connect();

			$sql = "CALL editHabits($id, '$czy_sie_udalo', '$data_wprowadzenia', '$czestotliwosc', '$kiedy_ostatnio_aktualizowano_nawyk', '$nazwa_nawyku', $habit[0], '$odstep_czestotliwosci')";
			$result = mysql_query($sql);
			
		}
		else {
			// ADD HABIT
			$this->db->connect();
			
			$sql = "CALL storeHabits('$id', '$czy_sie_udalo', '$data_wprowadzenia', '$czestotliwosc', '$kiedy_ostatnio_aktualizowano_nawyk', '$nazwa_nawyku', '$odstep_czestotliwosci')";
			$result = mysql_query($sql);
		}
		
				
		$nawyki_id = mysql_query("select last_insert_id() as id;");
		$echo = mysql_fetch_array($nawyki_id);
		$this->nawyk_id = $echo['id'];
		
		if ($this->nawyk_id == 0)
			$this->nawyk_id = $habit[0];
		
		$this->db->close();
		
		// check for success
		if ($result) {
			return true;
		}
		else {
            return false;
		}
	}
	
	public function storeStatistics($ilosc_nawykow, $najlepsza_passa, $srednia_dlugosc_ciagu, $procent_powodzen, $nawyki_id) {	
		$this->db->connect();
		
		$sql = "CALL storeStatistics('$ilosc_nawykow', '$najlepsza_passa', '$srednia_dlugosc_ciagu', '$procent_powodzen', '$nawyki_id')";
		
		$result = mysql_query($sql);
		
		$this->db->close();
			
		// check for success
		if ($result) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public function getUserByUsername($username) {
		//$result = mysql_query("SELECT * FROM Uzytkownicy WHERE salt ='$salt'") or die(mysql_error());
		
		$sql = "CALL getUserByUsername('$username')";
	
		$result = mysql_query($sql);
		
		$this->db->close();
		
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
		//$result = mysql_query("SELECT * FROM Uzytkownicy WHERE login = '$username'") or die(mysql_error());
		
		$sql = "CALL getUserByUsernameAndPassword('$username', '$password')";
	
		$result = mysql_query($sql);
		
		$this->db->close();
		
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
	
	public function getStatistics($email, $id) {
		//$sql = "SELECT Statystyki.id, najlepsza_passa, srednia_dlugosc_ciagu, procent_powodzen, nawyki_id FROM Statystyki 
		//		JOIN Nawyki ON Nawyki.id = Statystyki.nawyki_id 
		//		JOIN Uzytkownicy ON Uzytkownicy.id = Nawyki.uzytkownik_id
		//		WHERE Uzytkownicy.login = '$email' ";
		
		$sql = "CALL getStatistics('$email', '$id')";
		
		$result = mysql_query($sql);
		
		$this->db->close();
		
		// check for result
		if (isset($result)) {
			return $result;
		}
		else {
			return false;
		}
	}
	
	public function getHabitStatistics($habitID) {
		$sql = "CALL getHabitStatistics('$habitID')";
		
		$result = mysql_query($sql);
		
		$this->db->close();
		
		if (isset($result)) {
			return $result;
		}
		else {
			return false;
		}
	}
	
	public function getStatisticsDetails($id, $email) {
		//$sql = "SELECT Statystyki.id, najlepsza_passa, srednia_dlugosc_ciagu, procent_powodzen, nawyki_id FROM Statystyki 
		//		JOIN Nawyki ON Nawyki.id = Statystyki.nawyki_id 
		//		JOIN Uzytkownicy ON Uzytkownicy.id = Nawyki.uzytkownik_id
		//		WHERE Uzytkownicy.login = '$email' ";
		
		$sql = "CALL getStatistics('$id', '$email')";
		
		$result = mysql_query($sql);
		
		$this->db->close();
		
		// check for result
		if (isset($result)) {
			return $result;
		}
		else {
			return false;
		}
	}
	
	public function isUserExists($email) {
        //$result = mysql_query("SELECT login from Uzytkownicy WHERE login = '$email'");
		$sql = "CALL isUserExists('$email')";
		$result = mysql_query($sql);

        $no_of_rows = mysql_num_rows($result);
		
		$this->db->close();
		
        if ($no_of_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
            return false;
        }
    }
	
	public function storeUser($name, $email, $password) {
		$this->db->connect();
		
		$salt = uniqid('', true);
		$encrypted_password = MD5($password);
		//$result = mysql_query("INSERT INTO Uzytkownicy(login, haslo, salt, created_at, updated_at) 
		//	VALUES ('$email', '$encrypted_password', '$salt', NOW(), NOW())");
		
		$sql = "CALL storeUser('$email', '$encrypted_password', '$salt', NOW(), NOW())";
		$result = mysql_query($sql);

		
		// check for success
		if ($result) {
			$user = $this->getUserByUsername($email);
			$id = $user['id'];
			
			$this->db->connect();
			$result = mysql_query("SELECT * FROM Uzytkownicy WHERE id = $id");
			$returning = mysql_fetch_array($result);
			$this->db->close();
			
			return $returning;
		}
		else {
			return false;
		}
	}
	
	public function getHabitsByUser($username) {
		//$result = mysql_query("SELECT Nawyki.id, czy_sie_udalo, data_wprowadzenia, czestotliwosc, kiedy_ostatnio_aktualizowano_nawyk FROM Nawyki JOIN Uzytkownicy ON Uzytkownicy.id = Nawyki.uzytkownik_id WHERE Uzytkownicy.login = '$username'");
		
		$sql = "CALL getHabitsByUser('$username')";
		
		$result = mysql_query($sql);
		
		$this->db->close();
		
		// check for result
		if (isset($result)) {
			return $result;
		}
		else {
			return false;
		}
	}
	
	public function getNawykId() {
		return $this->nawyk_id;
	}
	
}