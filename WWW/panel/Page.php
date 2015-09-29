<?php

/**
 * Class for managing website, showing statistics etc.
 */
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
		if ($_GET['page'] == "statistics") {
			if (is_numeric($_GET['stat'])) {
				$this->showStatisticsDetails($_GET['stat']);
			}
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
					if ($row->odstep_czestotliwosci == 1)
						$periodicity = "Day";
					else if ($row->odstep_czestotliwosci == 7)
						$periodicity = "Week";
					else if ($row->odstep_czestotliwosci == 30)
						$periodicity = "Month";
					
					echo "Czestotliwosc: ".$row->czestotliwosc." ".$periodicity;
					echo "</br>";
					if ($row->kiedy_ostatnio_aktualizowano_nawyk == "1970-01-01")
						$row->kiedy_ostatnio_aktualizowano_nawyk = "-";
					echo "Kiedy ostatnio aktualizowano nawyk: ".$row->kiedy_ostatnio_aktualizowano_nawyk;
					echo "</br>";
					echo "Statystyki:";
					echo "</br>";
					$this->showStatistics($row->id);
				}
			}		
		}
	}
	
	public function showStatisticsDetails($habitID) {
		$statistics = $this->getHabitStatistics($habitID);
		
		while($row = mysql_fetch_object($statistics)){
			if ($habitID == $row->id) {
				echo "&emsp; <a href=\"?page=habits&habit=$row->nawyki_id\">Wstecz</a> ";
				echo "</br>";
				echo "</br>";
				echo "Ilosc nawykow: ".$row->ilosc_nawykow;
				echo "</br>";
				echo "Najlepsza passa: ".$row->najlepsza_passa;
				echo "</br>";
				echo "Srednia dlugosc ciagu: ".$row->srednia_dlugosc_ciagu;
				echo "</br>";
				echo "% Powodzen: ".$row->procent_powodzen;
			}
		}
		
	}
	
	public function showStatistics($id) {
		$statistics = $this->getStatistics($id);
		
		while($row = mysql_fetch_object($statistics)){
			echo "&emsp; <a href=\"?page=statistics&stat=$row->id\">Statystyki id: '$row->id'</a> ";
			echo "</br>";
		}		
		
		 
	}
	
	public function getHabitStatistics($habitID) {
		require_once '../php/FunctionsDB.php';
		
		$this->db = new FunctionsDB();
		$result = $this->db->getHabitStatistics($habitID);
		
		return $result;
	}
	
	public function getStatistics($id) {
		require_once '../php/FunctionsDB.php';
		

		$this->db = new FunctionsDB();
		$result = $this->db->getStatistics($this->username, $id);
		
		return $result;
	}
	
	private function sessionStaph() {
		session_destroy();
	}
	
}