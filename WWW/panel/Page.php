<?php

class Page {
	
	function __construct() {
		session_start();
		if(!isset($_SESSION['username'])){
			header("location:../index.php");
		}
	}
	
	public function get() {
		if ($_GET['page'] == "") {
			return "Uzytkownik ".$_SESSION['username'];
		}
		if ($_GET['page'] == "statistics") {
			return "Statistics";
		}
	}
	
}