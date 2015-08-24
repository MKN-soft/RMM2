<?php

class Page {
	
	private $db;
	private $username;
	
	function __construct() {
		session_start();
		if(!isset($_SESSION['username'])){
			header("location:../index.php");
		}
		else {
			$this->username = $_SESSION['username'];
		}
	}
	
	public function get() {
		$string = "";
		 
		if ($_GET['page'] == "") {
			$string .= "Uzytkownik ".$this->username;
		}
		if ($_GET['page'] == "habits") {
			$this->showHabits();
		}
		if ($_GET['page'] == "logout") {
			$this->sessionStaph();
		}
		
		return $string;
	}
	
	public function getHabits() {
		require_once '../php/FunctionsDB.php';
		
		$this->db = new FunctionsDB();
		$result = $this->db->getHabitsByUser($this->username);
		
		return $result;
	}
	
	public function showHabits() {
		$habits = $this->getHabits();
		
		if (is_numeric($_GET['habit'])) {
			while($row = mysql_fetch_object($habits)){
				if ($_GET['habit'] == $row->id) {
					echo "Czy sie udalo: ".$row->czy_sie_udalo;
					echo "</br>";
					echo "Data wprowadzenia: ".$row->data_wprowadzenia;
					echo "</br>";
					echo "Czestotliwosc: ".$row->czestotliwosc;
					echo "</br>";
					echo "Kiedy ostatnio aktualizowano nawyk: ".$row->kiedy_ostatnio_aktualizowano_nawyk;
				}
			}		
		}
	}
	
	private function sessionStaph() {
		session_destroy();
	}
	
}